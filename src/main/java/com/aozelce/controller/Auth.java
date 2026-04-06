package com.aozelce.controller;

import com.aozelce.auth.*;
import com.aozelce.entity.User;
import com.aozelce.persistence.GenericDao;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.math.BigInteger;
import java.net.*;
import java.net.http.*;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.*;
import java.util.*;
import java.util.stream.Collectors;


@WebServlet(
        urlPatterns = {"/auth"}
)
/**
 * Inspired by: https://stackoverflow.com/questions/52144721/how-to-get-access-token-using-client-credentials-using-java-code
 */

public class Auth extends HttpServlet {
    String CLIENT_ID;
    String CLIENT_SECRET;
    String OAUTH_URL;
    String LOGIN_URL;
    String REDIRECT_URL;
    String REGION;
    String POOL_ID;
    Keys jwks;

    private final Logger logger = LogManager.getLogger(this.getClass());

    @Override
    public void init() throws ServletException {
        super.init();
        loadPropertiesFromApplicationScope();
        loadKey();
    }

    /**
     * Load Cognito properties from application scope (set by ApplicationStartup servlet)
     */
    private void loadPropertiesFromApplicationScope() {
        CLIENT_ID = (String) getServletContext().getAttribute("client.id");
        CLIENT_SECRET = (String) getServletContext().getAttribute("client.secret");
        OAUTH_URL = (String) getServletContext().getAttribute("oauthURL");
        LOGIN_URL = (String) getServletContext().getAttribute("loginURL");
        REDIRECT_URL = (String) getServletContext().getAttribute("redirectURL");
        REGION = (String) getServletContext().getAttribute("region");
        POOL_ID = (String) getServletContext().getAttribute("poolId");

        if (CLIENT_ID == null || REGION == null || POOL_ID == null) {
            logger.error("Cognito properties were not loaded properly during application startup");

        }
    }

    /**
     * Gets the auth code from the request and exchanges it for a token containing user info.
     * @param req servlet request
     * @param resp servlet response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Auth servlet doGet() called - received request");
        String authCode = req.getParameter("code");
        logger.debug("Auth code received: " + (authCode != null ? "present" : "null"));
        String userName = null;

        if (authCode == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing auth code");
        } else {
            HttpRequest authRequest = buildAuthRequest(authCode);
            try {
                TokenResponse tokenResponse = getToken(authRequest);
                userName = validate(tokenResponse, req);
                req.setAttribute("userName", userName);
            } catch (IOException e) {
                logger.error("Error getting or validating the token", e);
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Token validation error");
            } catch (InterruptedException e) {
                logger.error("Error getting token from Cognito oauth url", e);
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Token retrieval interrupted");
            }
        }
        RequestDispatcher dispatcher = req.getRequestDispatcher("index.jsp");
        dispatcher.forward(req, resp);

    }

    /**
     * Sends the request for a token to Cognito and maps the response
     * @param authRequest the request to the oauth2/token url in cognito
     * @return response from the oauth2/token endpoint which should include id token, access token and refresh token
     * @throws IOException
     * @throws InterruptedException
     */
    private TokenResponse getToken(HttpRequest authRequest) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<?> response = null;

        response = client.send(authRequest, HttpResponse.BodyHandlers.ofString());


        logger.debug("Response headers: " + response.headers().toString());
        logger.debug("Response body: " + response.body().toString());

        ObjectMapper mapper = new ObjectMapper();
        TokenResponse tokenResponse = mapper.readValue(response.body().toString(), TokenResponse.class);
        logger.debug("Id token: " + tokenResponse.getIdToken());

        return tokenResponse;

    }

    /**
     * Get values out of the header to verify the token is legit. If it is legit, get the claims from it, such
     * as username. Also stores the authenticated user in the HTTP session.
     * @param tokenResponse the token response from Cognito
     * @param req the HTTP servlet request for session access
     * @return the preferred username of the authenticated user
     * @throws IOException
     */
    private String validate(TokenResponse tokenResponse, HttpServletRequest req) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        CognitoTokenHeader tokenHeader = mapper.readValue(CognitoJWTParser.getHeader(tokenResponse.getIdToken()).toString(), CognitoTokenHeader.class);

        // Header should have kid and alg- https://docs.aws.amazon.com/cognito/latest/developerguide/amazon-cognito-user-pools-using-the-id-token.html
        String keyId = tokenHeader.getKid();
        String alg = tokenHeader.getAlg();

        // todo pick proper key from the two - it just so happens that the first one works for my case
        // Use Key's N and E
        BigInteger modulus = new BigInteger(1, org.apache.commons.codec.binary.Base64.decodeBase64(jwks.getKeys().get(0).getN()));
        BigInteger exponent = new BigInteger(1, org.apache.commons.codec.binary.Base64.decodeBase64(jwks.getKeys().get(0).getE()));

        // Create a public key
        PublicKey publicKey = null;
        try {
            publicKey = KeyFactory.getInstance("RSA").generatePublic(new RSAPublicKeySpec(modulus, exponent));
        } catch (InvalidKeySpecException e) {
            logger.error("Invalid Key Error " + e.getMessage(), e);
        } catch (NoSuchAlgorithmException e) {
            logger.error("Algorithm Error " + e.getMessage(), e);
        }

        // get an algorithm instance
        Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) publicKey, null);

        // Verify ISS field of the token to make sure it's from the Cognito source
        String iss = String.format("https://cognito-idp.%s.amazonaws.com/%s", REGION, POOL_ID);

        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(iss)
                .withClaim("token_use", "id") // make sure you're verifying id token
                .acceptLeeway(36000)   // Allow 10 hours (36000 seconds) of clock skew for timezone difference
                .build();

        // Verify the token
        DecodedJWT jwt = verifier.verify(tokenResponse.getIdToken());
        String userName = jwt.getClaim("cognito:username").asString();
        logger.debug("here's the username: " + userName);

        // Extract the preferred_username claim from the JWT token
        String preferredUsername = jwt.getClaim("preferred_username").asString();
        logger.debug("preferred_username: " + preferredUsername);

        // Extract the email claim from the JWT token
        String email = jwt.getClaim("email").asString();
        logger.debug("email: " + email);

        logger.debug("here are all the available claims: " + jwt.getClaims());

        // ===== DATABASE USER PERSISTENCE LOGIC =====
        // The cognito:username claim contains the unique Cognito User ID (UUID format)
        String cognitoId = userName;

        // Create a GenericDao instance to interact with the User table in the database
        GenericDao<User> userDao = new GenericDao<>(User.class);

        // Check if a user with this Cognito ID already exists in the database
        List<User> existingUsers = userDao.getByPropertyEqual("cognitoId", cognitoId);

        if (existingUsers.isEmpty()) {
            // No existing user found - create a new user record in the database
            User newUser = new User();
            newUser.setCognitoId(cognitoId);    // Set the unique Cognito User ID
            newUser.setEmail(email);             // Set the user's email from Cognito
            newUser.setUsername(preferredUsername); // Set the preferred username from Cognito sign-up
            int newUserId = userDao.insert(newUser); // Insert the new user and get the auto-generated ID
            newUser.setId(newUserId); // Set the generated ID on the user object
            logger.info("Created new user with ID: " + newUserId + " for cognitoId: " + cognitoId);

            // Store the newly created user in the HTTP session for subsequent requests
            HttpSession session = req.getSession();
            session.setAttribute("user", newUser);
            session.setAttribute("preferredUsername", preferredUsername);
        } else {
            // User already exists - check if any attributes need to be updated
            User existingUser = existingUsers.get(0); // Get the first (and should be only) matching user
            boolean needsUpdate = false; // Flag to track if any changes were made

            // Check if email has changed and update if necessary
            if (email != null && !email.equals(existingUser.getEmail())) {
                existingUser.setEmail(email);
                needsUpdate = true;
            }

            // Check if preferred username has changed and update if necessary
            if (preferredUsername != null && !preferredUsername.equals(existingUser.getUsername())) {
                existingUser.setUsername(preferredUsername);
                needsUpdate = true;
            }

            // Only persist changes if something was actually updated
            if (needsUpdate) {
                userDao.saveOrUpdate(existingUser); // Save the updated user to the database
                logger.info("Updated user with cognitoId: " + cognitoId);
            } else {
                logger.debug("User with cognitoId: " + cognitoId + " already exists and is up to date");
            }

            // Store the existing user in the HTTP session for subsequent requests
            HttpSession session = req.getSession();
            session.setAttribute("user", existingUser);
            session.setAttribute("preferredUsername", existingUser.getUsername());
        }

        // Return the preferred username for display (fallback to email if not set)
        return preferredUsername != null ? preferredUsername : email;
    }

    /** Create the auth url and use it to build the request.
     *
     * @param authCode auth code received from Cognito as part of the login process
     * @return the constructed oauth request
     */
    private HttpRequest buildAuthRequest(String authCode) {
        String keys = CLIENT_ID + ":" + CLIENT_SECRET;

        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("grant_type", "authorization_code");
        parameters.put("client-secret", CLIENT_SECRET);
        parameters.put("client_id", CLIENT_ID);
        parameters.put("code", authCode);
        parameters.put("redirect_uri", REDIRECT_URL);

        String form = parameters.keySet().stream()
                .map(key -> key + "=" + URLEncoder.encode(parameters.get(key), StandardCharsets.UTF_8))
                .collect(Collectors.joining("&"));

        String encoding = Base64.getEncoder().encodeToString(keys.getBytes());

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(OAUTH_URL))
                .headers("Content-Type", "application/x-www-form-urlencoded", "Authorization", "Basic " + encoding)
                .POST(HttpRequest.BodyPublishers.ofString(form)).build();
        return request;
    }

    /**
     * Gets the JSON Web Key Set (JWKS) for the user pool from cognito and loads it
     * into objects for easier use.
     *
     * @see Keys
     */
    private void loadKey() {
        ObjectMapper mapper = new ObjectMapper();

        try {
            URL jwksURL = new URL(String.format("https://cognito-idp.%s.amazonaws.com/%s/.well-known/jwks.json", REGION, POOL_ID));
            File jwksFile = new File("jwks.json");
            FileUtils.copyURLToFile(jwksURL, jwksFile);
            jwks = mapper.readValue(jwksFile, Keys.class);
            logger.debug("Keys are loaded. Here's e: " + jwks.getKeys().get(0).getE());
        } catch (IOException ioException) {
            logger.error("Cannot load json..." + ioException.getMessage(), ioException);
        } catch (Exception e) {
            logger.error("Error loading json" + e.getMessage(), e);
        }
    }
}

