package com.aozelce.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The type Token response.
 */
public class TokenResponse {

	@JsonProperty("access_token")
	private String accessToken;

	@JsonProperty("refresh_token")
	private String refreshToken;

	@JsonProperty("id_token")
	private String idToken;

	@JsonProperty("token_type")
	private String tokenType;

	@JsonProperty("expires_in")
	private int expiresIn;

    /**
     * Get access token string.
     *
     * @return the string
     */
    public String getAccessToken(){
		return accessToken;
	}

    /**
     * Get refresh token string.
     *
     * @return the string
     */
    public String getRefreshToken(){
		return refreshToken;
	}

    /**
     * Get id token string.
     *
     * @return the string
     */
    public String getIdToken(){
		return idToken;
	}

    /**
     * Get token type string.
     *
     * @return the string
     */
    public String getTokenType(){
		return tokenType;
	}

    /**
     * Get expires in int.
     *
     * @return the int
     */
    public int getExpiresIn(){
		return expiresIn;
	}
}