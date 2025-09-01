-- MySQL dump 10.13  Distrib 8.0.41, for Win64 (x86_64)
--
-- Host: localhost    Database: project
-- ------------------------------------------------------
-- Server version	8.0.41

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `author`
--

DROP TABLE IF EXISTS `author`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `author` (
  `author_id` varchar(255) NOT NULL,
  `author_desc` varchar(255) DEFAULT NULL,
  `author_image` varchar(255) DEFAULT NULL,
  `author_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`author_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `author`
--

LOCK TABLES `author` WRITE;
/*!40000 ALTER TABLE `author` DISABLE KEYS */;
INSERT INTO `author` VALUES ('11001','Paul Beatty is an American author and winner of the Man Booker Prize for his novel \'The Sellout\'.','https://upload.wikimedia.org/wikipedia/commons/thumb/f/f4/Paul_Beatty_2016.jpg/440px-Paul_Beatty_2016.jpg','Paul Beatty'),('11002','Douglas Adams was an English author best known for his absurd and humorous science fiction series \'The Hitchhiker’s Guide to the Galaxy\'.','https://upload.wikimedia.org/wikipedia/commons/8/8d/Douglas_adams_portrait.jpg','Douglas Adams'),('11003','Joseph Heller was an American author known for \'Catch-22\', a satirical anti-war novel that coined the term itself.','https://upload.wikimedia.org/wikipedia/en/5/5e/Joseph_Heller_1986.jpg','Joseph Heller'),('11005','Neil Gaiman and Terry Pratchett collaborated to write the comedic apocalyptic novel \'Good Omens\'.','https://upload.wikimedia.org/wikipedia/commons/5/54/Terry_Pratchett_and_Neil_Gaiman_%28Good_Omens%29.jpg','Neil Gaiman & Terry Pratchett'),('11006','David Sedaris is a humorist and essayist known for his witty reflections on everyday life and his essays like those in \'Me Talk Pretty One Day\'.','https://upload.wikimedia.org/wikipedia/commons/7/77/David_Sedaris.jpg','David Sedaris');
/*!40000 ALTER TABLE `author` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-09-01 12:37:46
