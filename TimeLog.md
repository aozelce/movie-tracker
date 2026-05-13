# Time Log

| Date    | Task                                                                                                                                                               | Hours | Notes |
|---------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------|-------|
| 1/28/26 | Set up project repository                                                                                                                                          | 1     |       |
| 2/12/26 | Added : Problem statement, user stories, project plan, screen design                                                                                               | 4     |       |
| 2/15/26 | Implement User DAO and User entity with Hibernate. Added some of the tests. Set up test DB.                                                                        | 5     |       |
| 2/17/26 | Implemented Media DAO. Added more tests.                                                                                                                           | 5     |       |
| 2/18/26 | Implemented Recommendation DAO and tests. Reviewed database. Reviewed javadoc.                                                                                     | 3     |       |
| 2/18/26 | Worked on item in checkpoint 1 feedback. Reviewed project plan.                                                                                                    | 1     |       |
| 2/19/26 | Watched weekly course videos                                                                                                                                       | 1     |       |
| 2/20/26 | Refactored Source and Recommendation entities to replace IDs with relationships. Updated DAOs, tests, and User entity to support associations.                     | 4     |       |
| 2/20/26 | Watched weekly course videos for comapring with Equals method                                                                                                      | 1     |       |
| 2/21/26 | Added equals and hashCode methods to Media, Source, and Recommendation entities. Improved RecommendationDao tests with enhanced assertions and updated test logic. | 3     |       |
| 2/21/26 | Updated User entity with helper methods and equality checks. Added tests for cascading recommendation insertions.                                                  | 2     |       |
| 2/21/26 | Implemented RecommendationServlet for displaying user recommendations. Added new JSP and integrated Jersey dependencies into the pom. Deployed the app.            | 4     |       |
| 2/21/26 | Refactored DAO test setup to avoid redundant instantiation. Added test for cascading recommendation deletions on user removal.                                     | 3     |       |
| 2/22/26 | Updated tests and schema to reflect new user and recommendation constraints. Updated database references and test data.                                            | 3     |       |
| 2/22/26 | Watched weekly course videos for Generic Dao                                                                                                                       | 1     |       |
| 2/23/26 | Implemented GenericDao. Refactored RecommendationDao tests to use GenericDao.                                                                                      | 3     |       |
| 2/23/26 | Refactored all associated tests to use GenericDao. Removed MediaDao, RecommendationDao, SourceDao, and UserDao.                                                    | 2     |       |
| 2/27/26 | Watched week 6 videos.                                                                                                                                             | 1     |       |
| 2/27/26 | Deployed project to AWS Elastic Beanstalk instance                                                                                                                 | 2     |       |
| 2/28/26 | Watched week 7 videos.                                                                                                                                             | 1     |       |
| 3/1/26  | Implemented Cognito. Configured Certificate, deployed app to AWS fully                                                                                             | 4     |       |
| 3/10/26 | Code reviewed a peer's project                                                                                                                                     | 1     |       |
| 3/18/26 | Added Javadoc comments, implemented API DAO, generated Movie and ResultsItem POJOs from TMDB API, created test for JSON response validation.                       | 3     |       |
| 3/21/26 | Added TMDB properties loading, refactored TmdbDao, added search query parameter, updated navigation and recommendations page.                                      | 4     |       |
| 3/29/26 | Added rolling file appender to log4j2, updated index.jsp, improved user greeting and navigation links, updated recommendations page.                               | 2     |       |
| 4/06/26 | Added REST API endpoints for movie resource, added JSON support, updated movie endpoints, included dependencies, added API response test screenshots.              | 5     |       |
| 4/09/26 | Refactored TmdbDao to use injected properties, updated AddRecommendation, added unit tests for movie search, updated navigation links.                             | 3     |       |
| 4/16/26 | Refactored JSP files to use a shared head.jsp.                                                                                                                     | 2     |       |
| 4/19/26 | Updated year input in searchResults.jsp to extract year from releaseDate.                                                                                          | 1     |       |
| 4/20/26 | Moved test database config, updated .gitignore, general project maintenance.                                                                                       | 1     |       |
| 4/20/26 | Reviewed team presentations and replied with comments and question. Responded to inquiries regarding our team project.                                             | 2     |       |
| 4/20/26 | Added delete recommendation functionality with authentication and ownership validation in controller. Updated recommendations page to include delete option.       | 2     |       |
| 4/21/26 | Added edit recommendation functionlity with authentication and ownership validation. Updated recommendations page to include edit option.                          | 4     |       |
| 4/28/26 | Live peer review 2                                                                                                                                                 | 3     |       |
| 4/29/26 | Implemented Jackson json ignore properties to prevent API response changes breaking app                                                                            | 1     |       |
| 4/30/26 | Fixed genre functionality, added a separate API call to retrieve genre names from genre IDs                                                                        | 0.25  |       |
| 5/1/26  | Modularized session user operations, refactored AddRecommendation servlet methods to elminate duplicate code blocks.                                               | 2     |       |
| 5/1/26  | Refactored AddRecommendation servlet to use AuthRedirector for authentication checks and extracted redirection logic into a reusable utility class.                | 1     |       |
| 5/1/26  | Made miscellenous changes based on peer review suggestions                                                                                                         | 1     |       |
| 5/9/26  | Reseaarched and Added Lombok dependency to project configuration                                                                                                   | 1     |       |
| 5/9/26  | Replaced manual methods and constructors in Media entity with Lombok annotations                                                                                   | 1     |       |
| 5/9/26  | Updated Javadoc formatting across test classes and utility methods                                                                                                 | 1     |       |
| 5/9/26  | Added unit test for TmdbGenreService genre name retrieval logic                                                                                                    | 2     |       |
| 5/11/26 | Added tests for fetching entities by property equality with integer and object values                                                                              | 1     |       |
| 5/11/26 | Added sticky footer layout with new CSS                                                                                                                            | 1     |       |
| 5/11/26 | Updated JSPs to use main tags                                                                                                                                      | 1     |       |
| 5/11/26 | Refined footer styling                                                                                                                                             | 1     |       |
| 5/11/26 | Included acknowledgments in the Readme                                                                                                                             | 1     |       |
| 5/11/26 | Added RecommendationService for managing recommendations with user session handling                                                                                | 2     |       |
| 5/11/26 | Refined TmdbGenreService with updated Javadoc and validations                                                                                                      | 1     |       |
| 5/11/26 | Added MediaService with methods for creating Media entities from TMDB results                                                                                      | 1     |       |
| 5/11/26 | Added Javadoc and logging to PropertiesLoader interface and methods                                                                                                | 1     |       |
| 5/11/26 | Enhanced recommendation and error handling                                                                                                                         | 1     |       |
| 5/11/26 | Added AuthUtils utility for authentication handling and enhanced logging                                                                                           | 1     |       |
| 5/11/26 | Refactored imports and updated Javadoc in servlets                                                                                                                 | 1     |       |
| 5/11/26 | Refactored AddRecommendation servlet: streamlined workflows                                                                                                        | 1     |       |
| 5/11/26 | Added error page handling for 403 and 404 HTTP status codes                                                                                                        | 1     |       |
| 5/12/26 | Added confirmation handling for recommendations with form updates                                                                                                  | 0.5   |       |
| 5/12/26 | Adjusted servlet logic                                                                                                                                             | 0.5   |       |
| 5/12/26 | Added additional logging                                                                                                                                           | 0.5   |       |
| 5/12/26 | Added detailed Javadoc comments for EditRecommendation methods                                                                                      | 0.5   |       |
| 5/12/26 | Renamed TmdbDao to TmdbMediaService with updated references, tests, and initialization logic                                                        | 1     |       |
| 5/12/26 | Added language query parameter to TMDB search requests in TmdbDao                                                                                   | 0.5   |       |
| 5/12/26 | Enhanced comments to clarify genre handling and session cache logic                                                                                 | 0.5   |       |
| 5/12/26 | Added confirmation handling for recommendations with form updates                                                                                   | 0.5   |       |
| 5/12/26 | Adjusted servlet logic                                                                                                                              | 0.5   |       |
| 5/12/26 | Added additional logging                                                                                                                            | 0.5   |       |
| 5/13/26 | Renamed test class to match updated TmdbMediaService package                                                                                         | 0.5   |       |
| 5/13/26 | Refactored ToggleWatched to use AuthUtils for user retrieval; updated TmdbMediaService Javadoc and comments                                          | 0.5   |       |
| 5/13/26 | Refactored EditRecommendation to use AuthUtils for authenticated user retrieval                                                                      | 0.5   |       |
| 5/13/26 | Updated Javadoc in EditRecommendation                                                                                                               | 0.5   |       |
| 5/13/26 | Refactored EditRecommendation to simplify session handling, add helper method for retrieving recommendations, and integrate AuthRedirector for unauthenticated redirects. | 1     |       |
