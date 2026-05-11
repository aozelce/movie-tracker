# Movie & TV Show Recommendation Tracker - Project Documentation

## Project Overview

A web application that solves the problem of scattered movie and TV show recommendations. Users can save recommendations from various sources (friends, podcasts, social media) in one centralized location with notes about why they want to watch each item. The app integrates with The Movie Database (TMDB) API to automatically fetch metadata and posters, uses AWS Cognito for authentication, and is deployed on AWS.

**Technologies**: Java Servlets, JSP, Hibernate, MySQL, Maven, AWS (Cognito, Elastic Beanstalk), TMDB API, Bootstrap, Log4J2, JUnit

**Architecture**: Traditional Java web application
- **Controllers**: Java Servlets
- **Views**: JSP pages with JSTL
- **Model**: POJOs with Hibernate annotations
- **Data Access**: DAOs using Hibernate
- **Build Tool**: Maven
- **Database**: MySQL (local for dev, on EC2/Elastic Beanstalk for production)
- **Testing**: JUnit 5 with test database

**MVP**: 10 user stories (all marked with **[MVP]** below)

**Database**: 4 one-to-many relationships

---

## Problem Statement

People receive movie and TV show recommendations from multiple sources (friends, podcasts, social media, streaming platforms) but lack a centralized system to track them. Recommendations get scattered across Netflix watch lists, Instagram saves, mental notes, and text messages. This fragmentation leads to:

- **Lost context**: Forgetting who recommended something or why it seemed interesting
- **Decision paralysis**: Scrolling endlessly through streaming platforms unable to choose
- **Wasted recommendations**: Good suggestions forgotten or lost in the noise  
- **Time inefficiency**: Spending 20+ minutes deciding what to watch instead of watching

Users need a single source of truth for all their watch recommendations with the context that makes choosing easier.

---

## Project Technologies/Techniques

* **Security/Authentication**
   * AWS Cognito
   * Servlet filters for session management
* **Database**
   * MySQL 8.x (local for development, on EC2/Elastic Beanstalk for production)
   * H2 (optional for testing)
* **ORM Framework**
   * Hibernate 6.x
* **Dependency Management**
   * Maven
* **Web Services consumed using Java**
   * TMDB API (The Movie Database) - for movie/TV metadata and posters
* **CSS**
   * Bootstrap 5
* **Data Validation**
   * Hibernate Validator (Bean Validation API)
   * Bootstrap form validation for front-end
* **Logging**
   * Log4J2
* **Hosting**
   * AWS Elastic Beanstalk (application server)
   * MySQL installed on EC2 instance (or use Elastic Beanstalk's extensions)
* **Tech to explore as part of this work**
   * Project Lombok (reduce boilerplate code)
   * AWS Cognito SDK for Java
   * TMDB API consumption with Jackson for JSON parsing
   * Building clean servlet architecture
* **Unit Testing**
   * JUnit 5 tests to cover all testable logic
   * Test with actual database (no mocking frameworks needed)
   * Target: 70%+ code coverage
* **IDE**
   * IntelliJ IDEA

### Design

* [User Stories](design/userStories.md)
* [Database Design](design/databaseDesign.md)
* [Screens](design/wireframes/)


### [Project Plan](projectPlan.md)


#### [TimeLog](TimeLog.md)

## Acknowledgments

- Data provided by [TMDB](https://www.themoviedb.org/).
