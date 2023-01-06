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
-- Table structure for table `tblcourse`
--

DROP TABLE IF EXISTS `tblcourse`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tblcourse` (
  `course_id` varchar(255) NOT NULL,
  `course_name` varchar(255) NOT NULL,
  `teacher_name` varchar(255) NOT NULL,
  `time` int NOT NULL,
  `start_time` int NOT NULL,
  `end_time` int NOT NULL,
  `status` enum('必修','选修') NOT NULL,
  `now_num` int NOT NULL,
  `max_num` int NOT NULL,
  `classroom` varchar(255) DEFAULT NULL,
  `num` int DEFAULT NULL,
  PRIMARY KEY (`course_id`,`teacher_name`,`time`,`start_time`,`end_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblcourse`
--

/*!40000 ALTER TABLE `tblcourse` DISABLE KEYS */;
INSERT INTO `tblcourse` VALUES ('090002','计算机系统结构','213199999',4,6,7,'必修',5,10,'j3-102',2),('090002','计算机系统结构','213199999',5,6,7,'必修',5,10,'j3-102',2),('090010','计算机组成原理专题实践（研讨）','213199999',2,8,10,'选修',5,10,'j2-102',2),('090010','计算机组成原理专题实践（研讨）','213199999',2,11,13,'选修',5,10,'j2-102',2),('090020','数据结构','213199998',4,6,7,'必修',0,10,'j2-104',2),('090020','数据结构','213199998',5,3,4,'必修',0,10,'j2-104',2),('090034','数据结构与算法专题实践（全英、研讨）','213199998',2,11,13,'选修',0,10,'j1-105',2),('090034','数据结构与算法专题实践（全英、研讨）','213199998',4,11,13,'选修',0,10,'j1-105',2),('090098','计算机网络（全英，研讨）','213199998',1,6,7,'必修',4,30,'j2-102',2),('090098','计算机网络（全英，研讨）','213199998',2,3,5,'必修',4,30,'j2-102',2),('090099','高级数据结构','213199998',3,3,5,'选修',0,30,'j2-103',2),('090099','高级数据结构','213199998',4,6,7,'选修',0,30,'j2-103',2),('0900X0','CPU设计（全英、研讨）','213199999',4,3,5,'选修',2,10,'j2-102',2),('0900X0','CPU设计（全英、研讨）','213199999',5,3,5,'选修',2,10,'j2-102',2),('ZR2001','大学物理','213196001',1,1,2,'必修',2,45,'j1-101',2),('ZR2001','大学物理','213196001',5,3,4,'必修',2,45,'j1-101',2),('ZR2002','大物实验（理工）','213196001',3,6,9,'必修',0,30,'j3-104',2),('ZR2002','大物实验（理工）','213196001',4,6,8,'必修',0,30,'j3-104',2);
/*!40000 ALTER TABLE `tblcourse` ENABLE KEYS */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-07-30 20:16:12
