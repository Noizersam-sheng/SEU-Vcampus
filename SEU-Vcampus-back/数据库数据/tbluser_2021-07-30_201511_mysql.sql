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
-- Table structure for table `tbluser`
--

DROP TABLE IF EXISTS `tbluser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbluser` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'primary key',
  `nickname` varchar(255) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `authority` enum('教务老师','图书管理员','商店管理员','老师','学生') NOT NULL,
  `age` int NOT NULL,
  `sex` enum('男','女') NOT NULL,
  `time` date DEFAULT NULL,
  `profession` varchar(255) DEFAULT NULL,
  `college` varchar(255) DEFAULT NULL,
  `money` double NOT NULL,
  `url` varchar(255) DEFAULT NULL,
  `netfee` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=213200000 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbluser`
--

/*!40000 ALTER TABLE `tbluser` DISABLE KEYS */;
INSERT INTO `tbluser` VALUES (213190001,NULL,'9:;<=>','教务老师','教务老师',40,'男',NULL,NULL,NULL,0,NULL,0),(213190002,NULL,'9:;<=>','图书管理员','图书管理员',40,'女',NULL,NULL,NULL,0,NULL,0),(213190003,NULL,'9:;<=>','商店管理员','商店管理员',40,'男',NULL,NULL,NULL,0,NULL,0),(213190004,NULL,'9:;<=>','教师','老师',40,'男',NULL,NULL,NULL,0,NULL,0),(213190135,'金佬','9:;<=>','金顾睿','学生',20,'男','2019-08-08','计算机科学与工程','计算机科学与技术',681.1,'null',80),(213190136,'无','9:;<=>','顾深远','学生',22,'男','2015-01-01','seu','seu',929.7,'null',0),(213191111,'无','9:;<=>','佳能','学生',24,'男','2019-08-08','计算机科学与技术','计算机科学与工程',0,'null',0),(213191246,'小赖','9:;<=>','赖泽升','学生',20,'男','1985-07-03','软件','软件学院',572,'无',80),(213192222,'无','9:;<=>','佳杰','老师',20,'男',NULL,'null','计算机科学与技术',0,'null',0),(213192963,'小刘','9:;<=>','刘尊怡','学生',20,'男','2019-08-08','计算机科学与技术','计算机科学与工程',0,'null',0),(213193904,'muni','9:;<=>','牟倪','学生',17,'女','2019-08-25','计算机科学与技术','计算机科学与工程学院',298.18000000000006,'E:\\MyNewDesktop\\技能实训\\learning-workspace\\VCampus\\myFigure\\myPhoto.jpg',170),(213196001,'小陈','9:;<=>','陈天正','老师',40,'男',NULL,'null','数学',0,'null',0),(213196002,'小王','9:;<=>','王静','老师',40,'女',NULL,'null','计算机科学与工程',0,'null',0),(213196666,'无','9:;<=>','A','学生',20,'男','2019-08-01','seu','seu',0,'null',0),(213199998,'wangyun','9:;<=>','汪芸','老师',45,'女',NULL,'null','计算机科学与工程学院、软件学院',0,'null',0),(213199999,'RGL','9:;<=>','任国林','老师',50,'男',NULL,'null','计算机科学与工程学院、软件学院',190,'null',10);
/*!40000 ALTER TABLE `tbluser` ENABLE KEYS */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-07-30 20:15:17
