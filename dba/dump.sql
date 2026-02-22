-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: movie_tracker_experiment
-- ------------------------------------------------------
-- Server version	8.0.33

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `media`
--

DROP TABLE IF EXISTS `media`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `media` (
  `id` int NOT NULL AUTO_INCREMENT,
  `tmdb_id` int NOT NULL,
  `title` varchar(255) NOT NULL,
  `media_type` varchar(10) NOT NULL,
  `year` int DEFAULT NULL,
  `poster_path` varchar(500) DEFAULT NULL,
  `overview` text,
  `genres` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tmdb_id` (`tmdb_id`),
  KEY `idx_tmdb_id` (`tmdb_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `media`
--

LOCK TABLES `media` WRITE;
/*!40000 ALTER TABLE `media` DISABLE KEYS */;
INSERT INTO `media` VALUES (1,114410,'The Bear','tv',2022,'/rOTReW39E2lXd7ewEYvPGxqkRsb.jpg','A young chef returns to Chicago to run his family\'s sandwich shop.','Drama,Comedy'),(2,1396,'Breaking Bad','tv',2008,'/ggFHVNu6YYI5L9pCfOacjizRGt.jpg','A chemistry teacher diagnosed with cancer starts manufacturing meth.','Drama,Crime,Thriller'),(3,94997,'House of the Dragon','tv',2022,'/7QMsOTMUswlwxJP0rTTZfmz2tX2.jpg','The Targaryen dynasty is at the apex of its power.','Sci-Fi & Fantasy,Drama,Action & Adventure'),(4,1668,'Friends','tv',1994,'/f496cm9enuEsZkSPzCwnTESEK5s.jpg','Six young people navigate life and love in Manhattan.','Comedy'),(5,100088,'The Last of Us','tv',2023,'/uKvVjHNqB5VmOrdxqAt2F7J78ED.jpg','Joel and Ellie journey through post-apocalyptic America.','Drama,Sci-Fi & Fantasy'),(6,872585,'Oppenheimer','movie',2023,'/8Gxv8gSFCU0XGDykEGv7zR1n2ua.jpg','The story of J. Robert Oppenheimer.','Drama,History'),(7,346698,'Barbie','movie',2023,'/iuFNMS8U5cb6xfzi51Dbkovj7vM.jpg','Barbie and Ken leave Barbie Land.','Comedy,Adventure,Fantasy'),(8,603692,'John Wick: Chapter 4','movie',2023,'/vZloFAK7NmvMGKE7VkF5UHaz0I.jpg','John Wick faces a new enemy.','Action,Thriller,Crime'),(9,299536,'Avengers: Infinity War','movie',2018,'/7WsyChQLEftFiDOVTGkv3hFpyyt.jpg','The Avengers face Thanos.','Adventure,Action,Science Fiction'),(10,157336,'Interstellar','movie',2014,'/gEU2QniE6E77NI6lCU6MxlNBvIx.jpg','Explorers travel through a wormhole.','Adventure,Drama,Science Fiction'),(11,155,'The Dark Knight','movie',2008,'/qJ2tW6WMUDux911r6m7haRef0WH.jpg','Batman faces the Joker.','Drama,Action,Crime,Thriller'),(12,27205,'Inception','movie',2010,'/9gk7adHYeDvHkCSEqAvQNLV5Uge.jpg','A thief steals secrets through dream-sharing.','Action,Science Fiction,Adventure');
/*!40000 ALTER TABLE `media` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recommendation`
--

DROP TABLE IF EXISTS `recommendation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `recommendation` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `source_id` int DEFAULT NULL,
  `media_id` int NOT NULL,
  `notes` text,
  `is_watched` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `source_id` (`source_id`),
  KEY `media_id` (`media_id`),
  KEY `idx_user_id` (`user_id`),
  CONSTRAINT `recommendation_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE,
  CONSTRAINT `recommendation_ibfk_2` FOREIGN KEY (`source_id`) REFERENCES `source` (`id`) ON DELETE SET NULL,
  CONSTRAINT `recommendation_ibfk_3` FOREIGN KEY (`media_id`) REFERENCES `media` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recommendation`
--

LOCK TABLES `recommendation` WRITE;
/*!40000 ALTER TABLE `recommendation` DISABLE KEYS */;
INSERT INTO `recommendation` VALUES (1,2,1,1,'Sarah said best show of 2023!',0),(2,2,3,6,'NYT podcast recommended',0),(3,2,2,2,'Tom says best show ever',1),(4,2,4,7,'Saw on Instagram',0),(5,2,5,8,'Reddit action thread',0),(8,4,6,3,'Mom loves this show',0),(9,4,7,6,'Twitter masterpiece',0),(10,4,8,4,'Netflix suggestion',1),(11,4,7,11,'Film Twitter hype',0),(12,3,9,5,'Roommate binge',0),(13,3,10,10,'Letterboxd top rated',0),(14,3,10,9,'4.5 stars average',1);
/*!40000 ALTER TABLE `recommendation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `source`
--

DROP TABLE IF EXISTS `source`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `source` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_user_source` (`user_id`,`name`),
  CONSTRAINT `source_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `source`
--

LOCK TABLES `source` WRITE;
/*!40000 ALTER TABLE `source` DISABLE KEYS */;
INSERT INTO `source` VALUES (4,2,'Instagram'),(3,2,'NYT Podcast'),(5,2,'Reddit'),(1,2,'Sarah'),(2,2,'Tom'),(10,3,'Letterboxd'),(9,3,'Roommate'),(7,4,'Film Twitter'),(6,4,'Mom'),(8,4,'Netflix');
/*!40000 ALTER TABLE `source` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `cognito_id` varchar(255) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `username` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'','alperen@email.com','alperen'),(2,'','john.doe@email.com','john_doe'),(3,'','mike.chen@email.com','mike_chen'),(4,'','sarah.smith@email.com','sarah_smith'),(5,NULL,'mike@email.com','mike'),(7,NULL,'john@email.com','john'),(8,NULL,'ken@email.com','ken');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'movie_tracker_experiment'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-02-22 23:42:02
