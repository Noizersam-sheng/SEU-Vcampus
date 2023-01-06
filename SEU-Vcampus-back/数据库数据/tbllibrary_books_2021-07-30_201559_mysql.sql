-- MySQL dump 10.13  Distrib 8.0.25, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: mysql
-- ------------------------------------------------------
-- Server version	8.0.25

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
-- Table structure for table `tbllibrary_books`
--

DROP TABLE IF EXISTS `tbllibrary_books`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbllibrary_books` (
  `book_id` varchar(255) NOT NULL,
  `book_name` varchar(255) NOT NULL,
  `gross` int NOT NULL,
  `num` int NOT NULL,
  `borrowed_num` int NOT NULL,
  PRIMARY KEY (`book_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbllibrary_books`
--

/*!40000 ALTER TABLE `tbllibrary_books` DISABLE KEYS */;
INSERT INTO `tbllibrary_books` VALUES ('0401','自动控制原理',7,6,8),('0701','Matlab-从入门到入土',2,0,3),('0702','数学分析',12,11,3),('0703','现世代数',11,10,2),('0704','群论与几何',12,12,1),('0900','数据结构与算法',8,7,5),('0902','计算机系统结构',7,5,2),('0910','数据结构习题精讲',3,3,2),('0911','高级数据结构',3,3,0),('0912','高级数据结构 C语言实战',5,6,2),('0913','从门电路到CPU',12,11,6),('0915','计算机与社会',12,7,7),('0930','多媒体技术概述',3,2,2),('0999','计算机组成原理',5,3,4),('A11','天空之城',5,0,15),('A12','中世纪',5,1,1),('A13','他改变了中国',8,4,21),('B23','领导力素养',7,7,1),('B24','第五项修炼',6,6,3),('B25','乌合之众',6,6,2),('D35','文化沙漠',6,6,0),('D51','Java入门',5,5,0);
/*!40000 ALTER TABLE `tbllibrary_books` ENABLE KEYS */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-07-30 20:16:00
