# Enterprise Java Indie Project Checklist

---

## Week 3 (2/2) — Logging and Unit Testing
*Class: Logging (Log4J), Unit Testing (JUnit)*

- [X] Create project repository on GitHub
- [X] Create Maven web application project structure in IntelliJ
- [X] Add link to list of indie projects in student repo
- [X] Complete Problem Statement
- [X] Document all 10 MVP user stories
- [X] Confirm MVP stories meet Ent Java indie project objectives
- [X] Design screens (ensure all MVP user stories are covered)
- [X] List technologies, versions, and how they will be used
- [X] Set up Log4J2 configuration (`log4j2.xml` in `src/main/resources`)
- [X] Create initial package structure (`controller`, `persistence`, `entity`, `util`)
- [X] Set up `pom.xml` with all dependencies (Servlet API, JSP, JSTL, Hibernate, MySQL, Log4J2, JUnit 5, Jackson, Lombok TBD)
- [X] Configure `web.xml` basics
- [X] **Triple-check for Checkpoint 1 next week**
- [X] Complete Week 3 Exercise
- [X] Weekly reflection/time log

---

## Week 4 (2/9) — DAOs and Hibernate
*Class: DAOs, Hibernate | **CHECKPOINT 1 DUE 2/11***

- [X] Finalize database design with ERD (all 4 one-to-many relationships)
- [X] Create the dev version of the MySQL database locally
- [X] Create `User` entity with Hibernate annotations
- [X] Create `Source` entity with Hibernate annotations
- [X] Create `Recommendation` entity with Hibernate annotations
- [X] Create `Media` entity with Hibernate annotations
- [X] Set up `hibernate.cfg.xml` in `src/main/resources`
- [X] Create `SessionFactoryProvider` utility class
- [X] Create test database schema for unit tests
- [X] Verify Log4J2 is working (no `System.out.println` anywhere)
- [X] **Submit Checkpoint 1 by 2/11**
- [X] Complete Week 4 Exercise
- [X] Weekly reflection/time log

---

## Week 5 (2/16) — Hibernate
*Class: Hibernate*

- [X] Research TMDB API — register for API key and test endpoints

- [X] Create `GenericDao<T>` with `getAll()`, `getById()`, `insert()`, `update()`, `delete()`
- [X] Create `RecommendationDao` extending `GenericDao` (add `toggleWatched()`)
- [X] Create `SourceDao` extending `GenericDao`
- [X] Create `MediaDao` extending `GenericDao`
- [X] Create `UserDao` extending `GenericDao` (add `getByCognitoId()`)
- [X] Set up JUnit 5 for testing
- [X] Create test database configuration
- [X] Write unit tests for all DAOs (70%+ coverage)
  - [X] Create test data in `@BeforeEach`
  - [X] Test CRUD operations
  - [X] Test custom queries
  - [X] Test cascade behaviors
- [X] Add comprehensive Log4J2 logging to all DAO methods
- [X] Ensure all database access uses Hibernate (no raw JDBC)
- [X] Complete Week 5 Exercise
- [X] Weekly reflection/time log

---

## Week 6 (2/23) — AWS Deployment & Project Reviews
*Class: Deployment to AWS, Individual Project Reviews*

- [X] Research AWS Elastic Beanstalk deployment options
- [X] Create production `hibernate.cfg.xml` (use env vars for credentials)
- [X] Update all documentation for peer review
- [X] Ensure ERD is clear and complete
- [X] Verify all 11 MVP user stories documented
- [X] Complete Week 6 Exercise
- [X] Weekly reflection/time log

---

## Week 7 (3/2) — Security and Authentication
*Class: Security, Authentication | **CHECKPOINT 2 DUE 3/4***
- [X] **Participate in Peer Design Review session**
- [X] Document feedback received from peer review
- [X] Double-check all Checkpoint 2 requirements
  - [X] Database schema created (MySQL locally)
  - [X] At least one DAO with full CRUD
  - [X] DAO fully unit tested
  - [X] Log4J2 configured and used throughout
- [X] Set up AWS Cognito User Pool (configure pool, password policy, sign-in,
  app client)
- [X] Add AWS SDK for Java dependency to `pom.xml`

[//]: # (- [ ] Create `CognitoUtil` helper class &#40;`authenticateUser`, `registerUser`, `getUser`&#41;)

[//]: # (- [ ] Create `AuthenticationFilter` &#40;implements `Filter`&#41;)

[//]: # (- [ ] Map filter in `web.xml` to protect all pages except login)

[//]: # (- [ ] Create session management utilities)
- [X] Test authentication flow locally with Cognito
- [X] Add logging for all authentication events
- [X] **Submit Checkpoint 2 by 3/4**
- [X] Complete Week 7 Exercise
- [X] Weekly reflection/time log

---

## Week 8 (3/9) — Web Services Intro
*Class: Web Services Intro*

- [X] Research TMDB API v3 documentation thoroughly
- [X] Add Jackson dependencies to `pom.xml`
- [X] Create `TMDBService` class (`searchMovies`, `searchTVShows`, 
  `getMovieDetails`, `getTVShowDetails`)
- [X] Implement proper error handling for API calls (rate limits, network 
  failures, invalid keys)
- [x] Map TMDB response data to `Media` entity
- [X] Write unit tests for `TMDBService`
- [X] Test API integration manually
- [X] Sign up for team project
- [X] Complete Week 8 Exercise
- [X] Weekly reflection/time log

---

## Week 9 (3/23) — RESTful Web Services
*Class: RESTful Web Services | **CHECKPOINT 3 DUE 3/25***

- [X] Create `UserService`
- [X] Create `SourceService` (`createSource`, `getUserSources`, 
  `updateSource`, `deleteSource`)
- [X] Create `RecommendationService` (`createFromTMDB`, `createManual`, 
  `updateRecommendation`, `getByUserId`, `toggleWatched`, `deleteRecommendation`)
- [X] Add Log4J2 logging to all service methods
- [X] Write unit tests for services
- [X] Create simple JSP to display recommendations list (basic, for checkpoint)
- [X] Create basic servlet to fetch and forward data to JSP
- [X] Package application as WAR file
- [X] Deploy to AWS Elastic Beanstalk
  - [X] Create Elastic Beanstalk environment
  - [X] Upload WAR file
  - [X] SSH into EC2 and install MySQL
  - [X] Create production database schema on EC2
  - [X] Configure environment variables
  - [X] Update `hibernate.cfg.xml` for production
  - [X] Test deployment
- [X] Verify authentication works in production
- [X] Add deployed link to indie project list in student repo
- [X] **Submit Checkpoint 3 by 3/25**
- [X] Complete Week 9 Exercise
- [X] Start team project repository setup
- [X] Weekly reflection/time log

---

## Week 10 (3/30) — Work Week
*Class: Work Week*

- [X] Create `SearchTMDBServlet`
- [X] Create `AddRecommendationServlet`
- [X] Implement input validation (required fields, data types, error messages)
- [X] Add error handling to all servlets (try-catch, error page forwarding, 
  Log4J2)
- [X] Configure servlet mappings in `web.xml`
- [X] Test all servlets manually
- [X] Complete Week 10 Branching Activities (2)
- [X] Weekly reflection/time log

---

## Week 11 (4/6) — Work Week
*Class: Work Week*

- [X] Create base JSP template with Bootstrap 5 (`header.jsp`, `footer.jsp`)
- [X] Create `login.jsp`
- [X] Create `recommendations.jsp` (US-005, US-008)
- [X] Create `addRecommendation.jsp` (US-003, US-004) with TMDB search + AJAX
- [X] Test all pages
- [X] Weekly reflection/time log

---

## Week 12 (4/13) — Team Project Presentations
*Class: Team Project Presentations | **Team Projects due 4/15***

- [X] Complete team project and presentation
- [X] Cross-browser testing (Chrome, Firefox, Safari)
- [X] Fix any bugs discovered
- [X] Weekly reflection/time log

---

## Week 13 (4/20) — Asynchronous Messaging

- [X] Create `EditRecommendationServlet`
- [X] Create `DeleteRecommendationServlet`
- [ ] Create `ToggleWatchedServlet`

- [X] Create `editRecommendation.jsp` (US-010)
- [ ] Polish individual project UI with Bootstrap

*Class: Asynchronous Messaging*

- [ ] Implement client-side filtering/sorting (watched/unwatched, date, title)
- [ ] Performance optimization (lazy load images, cache TMDB results)
- [ ] Accessibility improvements (ARIA labels, keyboard nav, alt text)
- [ ] Run full unit test suite
- [ ] Verify test coverage is 70%+
- [ ] Fix any failing tests
- [ ] Security testing (auth/authorization, user data isolation, SQL injection)
- [ ] Optional: Complete Week 13 Exercise (Challenge)
- [ ] Weekly reflection/time log

---

## Week 14 (4/27) — Individual Project Code Reviews
*Class: Individual Project Code Reviews*

- [ ] Clean up code formatting
- [ ] Add JavaDoc comments to all public methods
- [ ] Remove commented-out code and debug logging
- [ ] Update README with latest info
- [ ] **Participate in Peer Code Review session**
- [ ] Document feedback received
- [ ] Prioritize feedback (critical vs nice-to-have)
- [ ] Implement critical feedback (refactor, fix code smells, naming, error handling)
- [ ] Re-test affected areas
- [ ] **Submit Peer Code Review Feedback**
- [ ] Weekly reflection/time log

---

## Week 15 (5/4) — Work Week
*Class: Work Week*

- [ ] Finish implementing peer review feedback
- [ ] Write comprehensive `README.md` (description, tech stack, user stories, setup, deployment, screenshots, deployed link)
- [ ] Create JavaDoc for all public methods
- [ ] Add inline comments where needed
- [ ] Create `USER_GUIDE.md` (register/login, add recommendations, sources, filtering)
- [ ] Final code quality check (no `System.out.println`, consistent formatting, 70%+ test coverage)
- [ ] Run all tests one final time
- [ ] Deploy final version to AWS
- [ ] Test production deployment thoroughly
- [ ] Weekly reflection/time log

---

## Week 16 (5/11) — Individual Project Presentations
*Class: Individual Project Presentations | **PROJECT DUE 5/13, 9 p.m.***

- [ ] Create presentation slides (problem statement, tech, ERD, demo, challenges, learnings)
- [ ] Practice presentation (5–7 minutes)
- [ ] Create video demonstration (screen recording with voiceover, all 10 MVP features)
- [ ] Upload video to YouTube (unlisted) and add link to `README.md`
- [ ] Final testing in production (all 10 user stories, auth, CRUD, mobile)
- [ ] Final documentation review (proofread, verify links, update outdated info)
- [ ] Final commit and push to GitHub
- [ ] Verify all Checkpoint requirements met
- [ ] **Submit project by 5/13, 9 p.m.**
- [ ] Weekly reflection/time log
