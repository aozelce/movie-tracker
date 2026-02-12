## Project Plan
### Week 3 (2/2) - Logging and Unit Testing
**Class topic: Logging (Log4J), Unit Testing (JUnit)**

* Create project repository on GitHub
* Create Maven web application project structure in IntelliJ
  * Use maven-archetype-webapp or create manually
  * Package as WAR file
* Add link to list of indie projects in student repo
* Complete Problem Statement
* Document all 10 MVP user stories
* Confirm MVP stories meet Ent Java indie project objectives
* Design screens - make sure all MVP user stories are covered
* Research TMDB API - register for API key and test endpoints
* List technologies, versions and how they will be used
* Set up Log4J2 configuration (log4j2.xml in src/main/resources)
* Create initial package structure:
  ```
  src/main/java/com/yourname/watchlist/
  ├── controller/ (servlets)
  ├── persistence/ (DAOs)
  ├── entity/ (POJOs with Hibernate annotations)
  ├── util/ (helpers, API clients)
  └── filter/ (authentication filter) TBD*
  ```
* Set up pom.xml with dependencies:
  * Servlet API 6.0
  * JSP API
  * JSTL
  * Hibernate 6.x
  * MySQL Connector
  * Log4J2
  * JUnit 5
  * Jackson (for JSON parsing)
  * Lombok TBD
* Configure web.xml basics
* **Triple-check for Checkpoint 1 next week**
* Complete Week 3 Exercise
* Weekly reflection/time log

### Week 4 (2/9) - DAOs and Hibernate
**Class topic: DAOs, Hibernate**
**CHECKPOINT 1 DUE 2/11**

This week my focus is getting Checkpoint 1 ready and starting database work.

* Finalize database design with ERD showing all 4 one-to-many relationships
* Create the dev version of the MySQL database locally
* Create all entity classes with Hibernate annotations:
  * User entity (for Cognito user data)
    * @Entity, @Id for cognito_id (String)
    * @OneToMany for sources
    * @OneToMany for recommendations
  * Source entity
    * @Entity, @Id, @GeneratedValue
    * @ManyToOne for user
    * @OneToMany for recommendations
  * Recommendation entity
    * @Entity, @Id, @GeneratedValue
    * @ManyToOne for user
    * @ManyToOne for source
    * @ManyToOne for media
  * Media entity
    * @Entity, @Id, @GeneratedValue
    * @Column(unique=true) for tmdb_id
    * @OneToMany for recommendations
* Add Project Lombok annotations (@Data, @NoArgsConstructor, @AllArgsConstructor)
* Set up hibernate.cfg.xml in src/main/resources
  * Configure MySQL connection
  * Set dialect to MySQL8Dialect
  * Configure hibernate.hbm2ddl.auto (update for dev)
* Create SessionFactoryProvider utility class
* Create test database schema for unit tests
* Verify Log4J2 is working (no System.out.println anywhere)
* **Submit Checkpoint 1 by 2/11**
* Complete Week 4 Exercise
* Weekly reflection/time log

### Week 5 (2/16) - Hibernate
**Class topic: Hibernate**

This week my focus is building out the DAO layer with full CRUD.

* Create GenericDao<T> with common CRUD operations:
  * getAll()
  * getById(id)
  * insert(entity)
  * update(entity)
  * delete(entity)
* Create RecommendationDao extending GenericDao
  * Add custom HQL query: getByUserId(String cognitoId)
  * Add method: toggleWatched(int id) - toggles the is_watched boolean
* Create SourceDao extending GenericDao
  * Add custom HQL query: getByUserId(String cognitoId)
* Create MediaDao extending GenericDao
  * Add custom HQL query: getByTmdbId(int tmdbId)
* Create UserDao extending GenericDao
  * Add custom HQL query: getByCognitoId(String cognitoId)
* Set up JUnit 5 for testing
* Create test database configuration
* Write unit tests for all DAOs (aim for 70%+ coverage)
  * Create test data in @BeforeEach
  * Test CRUD operations
  * Test custom queries
  * Test cascade behaviors
  * Clean up test data in @AfterEach
* Add comprehensive Log4J2 logging to all DAO methods
  * Log method entry/exit
  * Log SQL operations
  * Log exceptions
* Ensure all database access uses Hibernate (no raw JDBC)
* Complete Week 5 Exercise
* Weekly reflection/time log

### Week 6 (2/23) - AWS Deployment & Project Reviews
**Class topic: Deployment to AWS, Individual Project Reviews**

This week my focus is getting ready for AWS deployment and design review.

* Research AWS Elastic Beanstalk deployment options
  * Understand Java platform on Elastic Beanstalk
  * Review deployment process documentation
* Create production hibernate.cfg.xml
  * Use environment variables for database credentials
  * Different configuration for production vs dev
* Prepare for Peer Design Review
  * Update all documentation
  * Ensure ERD is clear and complete
  * Verify all 10 MVP user stories documented
  * Clean up GitHub repository
* **Participate in Peer Design Review session**
* Document feedback received from peer review
* Complete Week 6 Exercise
* Weekly reflection/time log

### Week 7 (3/2) - Security and Authentication
**Class topic: Security, Authentication**
**CHECKPOINT 2 DUE 3/4: Database designed and created, at least one DAO with full CRUD implemented with Hibernate, DAO fully unit tested, Log4J2 implemented**

This week my focus is authentication and meeting Checkpoint 2.

* **Double-check all Checkpoint 2 requirements:**
  * Database schema created (MySQL locally) ✓
  * At least one DAO with full CRUD ✓
  * DAO fully unit tested ✓
  * Log4J2 configured and used throughout ✓
  * No System.out.println anywhere ✓
* Set up AWS Cognito User Pool
  * Configure user pool in AWS Console
  * Set password requirements
  * Enable email/username sign-in
  * Configure app client
  * Get client ID and client secret
* Add AWS SDK for Java dependency to pom.xml
* Create CognitoUtil helper class
  * Methods to interact with Cognito API
  * authenticateUser(username, password)
  * registerUser(email, username, password)
  * getUser(accessToken)
* Create AuthenticationFilter (implements Filter)
  * Check for valid session
  * Verify Cognito token if present
  * Redirect to login if not authenticated
  * Store user info in session
* Map filter in web.xml to protect all pages except login
* Create session management utilities
  * Store cognito_id in session
  * Create/retrieve User entity based on Cognito user
* Test authentication flow locally with Cognito
* Add logging for all authentication events
* **Submit Checkpoint 2 by 3/4**
* Complete Week 7 Exercise
* Weekly reflection/time log

### Week 8 (3/9) - Web Services Intro
**Class topic: Web Services Intro**

This week my focus is integrating the TMDB API (web service consumption).

* Research TMDB API v3 documentation thoroughly
* Add Jackson dependencies to pom.xml for JSON parsing
* Create TMDBService class for API consumption
  * Use HttpURLConnection or Apache HttpClient
  * Implement searchMovies(String query) method
  * Implement searchTVShows(String query) method
  * Implement getMovieDetails(int tmdbId) method
  * Implement getTVShowDetails(int tmdbId) method
* Create DTO classes for TMDB API responses
  * TMDBSearchResultDTO
  * TMDBMovieDetailDTO
  * TMDBTVShowDetailDTO
  * Use Jackson annotations for JSON mapping
* Implement proper error handling for API calls
  * Handle rate limits (40 requests per 10 seconds)
  * Handle network failures
  * Handle invalid API keys
  * Add Log4J2 logging for all API calls and errors
* Map TMDB response data to Media entity
* Create MediaService class
  * Coordinates TMDBService and MediaDao
  * searchAndSave(query) - search TMDB and save to DB
  * getOrCreateMedia(tmdbId) - check DB first, then TMDB
* Write unit tests for TMDBService
  * Mock API responses or use actual API (with care for rate limits)
* Test API integration manually
* Sign up team for team project
* Complete Week 8 Exercise
* Weekly reflection/time log


### Week 9 (3/23) - RESTful Web Services
**Class topic: RESTful Web Services**
**CHECKPOINT 3 DUE 3/25: Deployed to AWS, at least one JSP displaying data from database, authentication implemented, add AWS deployed app link to indie project list**

This week my focus is creating servlets and first deployment to AWS.

* Create service layer classes:
  * UserService - manages User CRUD
  * SourceService - manages Source CRUD
    * createSource(source, userId)
    * getUserSources(userId)
    * updateSource(source)
    * deleteSource(sourceId)
  * RecommendationService - manages Recommendation CRUD
    * createFromTMDB(userId, tmdbId, sourceId, notes)
    * createManual(recommendation)
    * updateRecommendation(recommendation)
    * getByUserId(userId)
    * toggleWatched(recommendationId) - toggle watched status
    * deleteRecommendation(recommendationId)
* Add comprehensive Log4J2 logging to all service methods
* Write unit tests for services
  * Test business logic
  * Test with actual DAOs and test database
* Create simple JSP to display recommendations list (basic, just for checkpoint)
  * Use JSTL and EL to display data
  * Loop through recommendations
  * Show basic info (title, notes, watched status)
* Create basic servlet to fetch and forward data to JSP
* Package application as WAR file
* Deploy to AWS Elastic Beanstalk
  * Create Elastic Beanstalk environment (Java platform)
  * Upload WAR file
  * SSH into EC2 instance and install MySQL
  * Create production database schema on EC2
  * Configure environment variables (DB connection, API keys, Cognito settings)
  * Update hibernate.cfg.xml to use localhost for database
  * Test deployment
* Verify authentication works in production
* Add deployed link to indie project list in student repo
* **Submit Checkpoint 3 by 3/25**
* Complete Week 9 Exercise
* Start team project repository setup
* Weekly reflection/time log

### Week 10 (3/30) - Work Week
**Class topic: Work Week**

This week my focus is building servlets for all CRUD operations.

* Create servlet for TMDB search:
  * SearchTMDBServlet - handles search requests
  * Calls TMDBService
  * Returns JSON response or forwards to JSP
* Create servlet for recommendations:
  * AddRecommendationServlet - POST to create
  * EditRecommendationServlet - POST to update
  * DeleteRecommendationServlet - POST to delete
  * ListRecommendationsServlet - GET to display all
  * ToggleWatchedServlet - POST to toggle watched status (checkbox)
* Create servlet for sources:
  * AddSourceServlet - POST to create
  * EditSourceServlet - POST to update
  * DeleteSourceServlet - POST to delete
  * ListSourcesServlet - GET to display all
* Implement input validation
  * Check required fields
  * Validate data types
  * Return error messages
* Add error handling
  * Try-catch blocks in all servlets
  * Forward to error page on exceptions
  * Log all errors with Log4J2
* Configure servlet mappings in web.xml
* Test all servlets manually
* Complete Week 10 Branching Activities (2)
* Weekly reflection/time log

### Week 11 (4/6) - Work Week
**Class topic: Work Week**

This week my focus is building the JSP pages with Bootstrap.

* Create base JSP template with Bootstrap 5
  * header.jsp with navigation and user info
  * footer.jsp
  * Include JSTL and Bootstrap CDN links
* Create login.jsp
  * Form to submit to AWS Cognito
  * Or integrate Cognito hosted UI
* Create recommendations.jsp (main page - US-005)
  * Use JSTL <c:forEach> to loop through recommendations
  * Display TMDB posters using <img> tags
  * Show metadata (title, year, genres)
  * Display source tag as Bootstrap badge
  * Checkboxes for marking watched (US-008)
  * Edit and delete buttons for each item
  * Style with Bootstrap cards/table
* Create addRecommendation.jsp (US-003, US-004)
  * Form with TMDB search input
  * JavaScript to call SearchTMDBServlet via AJAX
  * Display search results with posters
  * Select button to choose result
  * Manual entry form as fallback
  * Source dropdown
  * Notes textarea
  * Bootstrap validation
* Create editRecommendation.jsp (US-010)
  * Pre-populated form
  * Update source and notes
* Create sources.jsp (US-006)
  * List all sources
  * Add/edit/delete forms
  * Color picker for badge colors
* Add JavaScript for dynamic behavior
  * AJAX calls to servlets
  * Form validation
  * Modal popups for confirm delete
* Test all pages
* Weekly reflection/time log

### Week 12 (4/13) - Team Project Presentations
**Class topic: Team Project Presentations**
**Team Projects due 4/15**

This week is focused on team project, but continue individual project work.

* Complete team project and presentation
* For individual project:
  * Polish UI styling with Bootstrap
  * Add loading spinners for async operations
  * Implement success/error message alerts
  * Test mobile responsiveness
  * Cross-browser testing (Chrome, Firefox, Safari)
  * Fix any bugs discovered
* Weekly reflection/time log

### Week 13 (4/20) - Asynchronous Messaging
**Class topic: Asynchronous Messaging**

This week my focus is polishing and testing.

* Implement client-side filtering/sorting with JavaScript
  * Filter by watched/unwatched
  * Sort by date added, title
* Add pagination for large lists (if time permits)
* Performance optimization
  * Lazy load images
  * Cache TMDB results in database
* Accessibility improvements
  * ARIA labels
  * Keyboard navigation
  * Alt text for images
* Run full unit test suite
* Verify test coverage is 70%+
* Fix any failing tests
* Security testing
  * Test authentication/authorization
  * Verify users can't access others' data
  * Check for SQL injection prevention
* Optional: Complete Week 13 Exercise (Challenge)
* Weekly reflection/time log

### Week 14 (4/27) - Individual Project Code Reviews
**Class topic: Individual Project Code Reviews**

This week my focus is code review prep and incorporating feedback.

* Prepare for Peer Code Review
  * Clean up code formatting
  * Add JavaDoc comments to all public methods
  * Remove commented-out code
  * Remove debug logging statements
  * Update README with latest info
* **Participate in Peer Code Review session**
* Document feedback received
* Prioritize feedback items (critical vs nice-to-have)
* Start implementing critical feedback
  * Refactor based on suggestions
  * Fix code smells
  * Improve naming/documentation
  * Add missing error handling
* Re-test affected areas
* **Submit Peer Code Review Feedback**
* Weekly reflection/time log

### Week 15 (5/4) - Work Week
**Class topic: Work Week**

This week my focus is final implementation and documentation.

* Finish implementing peer review feedback
* Write comprehensive README.md
  * Project description and problem solved
  * Technologies used with versions
  * All 10 MVP user stories
  * Setup instructions (how to run locally)
  * AWS deployment guide
  * Screenshots of application
  * Link to deployed application
* Create JavaDoc for all public methods
  * Class-level documentation
  * Method parameters and return values
  * Explain complex logic
* Add inline comments where needed
* Create USER_GUIDE.md for end users
  * How to register/login
  * How to add recommendations
  * How to use source tags
  * How to filter
* Final code quality check
  * No System.out.println or printStackTrace
  * All logging uses Log4J2
  * Consistent formatting
  * Test coverage above 70%
* Run all tests one final time
* Deploy final version to AWS
* Test production deployment thoroughly
* Weekly reflection/time log

### Week 16 (5/11) - Individual Project Presentations
**Class topic: Individual Project Presentations**
**Individual Projects due 5/13, 9 p.m.**

This week my focus is final presentation and submission.

* Create presentation slides
  * Problem statement
  * Technologies used (emphasize servlet/JSP architecture)
  * Database design (ERD with 4 one-to-many relationships)
  * Demo of key features
  * Challenges faced and solutions
  * What I learned
* Practice presentation (5-7 minutes)
* Create video demonstration
  * Screen recording showing all 10 MVP features
  * Voiceover explaining functionality
  * Upload to YouTube (unlisted)
  * Add video link to README.md
* Final testing in production
  * Test all 10 user stories
  * Verify authentication works
  * Check all CRUD operations
  * Test on mobile device
* Final documentation review
  * Proofread all docs
  * Verify all links work
  * Update any outdated info
* Final commit and push to GitHub
* Verify all Checkpoint requirements met
* **Submit project by 5/13, 9 p.m.**
* Weekly reflection/time log