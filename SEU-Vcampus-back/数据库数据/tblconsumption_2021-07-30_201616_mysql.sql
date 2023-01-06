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
-- Table structure for table `tblconsumption`
--

DROP TABLE IF EXISTS `tblconsumption`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tblconsumption` (
  `id` int NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `commodity_id` varchar(255) NOT NULL,
  `commodity_name` varchar(255) NOT NULL,
  `type` varchar(255) NOT NULL,
  `num` int NOT NULL,
  `cost` double NOT NULL,
  `time` date NOT NULL,
  `market` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblconsumption`
--

/*!40000 ALTER TABLE `tblconsumption` DISABLE KEYS */;
INSERT INTO `tblconsumption` VALUES (213191246,'','A12','康师傅','食品饮料',1,20,'2021-07-14',1),(213191246,'','A66','王老吉','食品饮料',12,960,'2021-07-21',1),(213191246,'','A13','热带风味冰红茶','食品饮料',12,60,'2021-07-21',1),(213191246,'','B12','计算机组成原理','学习资料',1,36,'2021-07-24',2),(213191246,'','B10','概率论（东南大学出版社）','学习资料',1,35,'2021-07-23',2),(213191246,'','A16','雪碧','食品饮料',1,20,'2021-07-19',1),(213191246,'','A13','热带风味冰红茶','食品饮料',1,5,'2021-07-21',1),(213191246,'','A15','可口可乐','食品饮料',50,500,'2021-07-28',1),(213191246,'','A17','哈根达斯','雪糕',1,30,'2021-07-23',1),(213191246,'','A16','雪碧','食品饮料',30,600,'2021-07-23',1),(213191246,'','A19','辣条','食品饮料',10,50,'2021-07-26',1),(213191246,'','B10','概率论（东南大学出版社）','学习资料',1,35,'2021-07-26',2),(213191246,'','A17','哈根达斯','雪糕',1,30,'2021-07-26',1),(213191246,'','A17','哈根达斯','雪糕',1,30,'2021-07-26',1),(213191246,'','A15','可口可乐','食品饮料',1,10,'2021-07-28',1),(213190135,'','SJ001','同济大学高等数学课本经典教材上册','学习资料',2,70.3,'2021-07-29',1),(213190135,'','SJ002','同济大学高等数学课本下册','学习资料',1,39.9,'2021-07-28',1),(213191246,'','SJ003','Java从入门到精通 经典Java入门教材','学习资料',1,28,'2021-07-29',1),(213190136,'','SJ001','同济大学高等数学课本经典教材上册','学习资料',2,70.3,'2021-07-30',1),(213190135,'','SJ001','同济大学高等数学课本经典教材上册','学习资料',6,210.89999999999998,'2021-07-30',1),(213190135,'','SJ003','Java从入门到精通 经典Java入门教材','学习资料',3,84,'2021-07-30',1),(213190135,'','SJ003','Java从入门到精通 经典Java入门教材','学习资料',1,28,'2021-07-30',1),(213193904,'','SJ002','同济大学高等数学课本下册','学习资料',1,36.96,'2021-07-30',1),(213193904,'','SJ003','Java从入门到精通 经典Java入门教材','学习资料',1,28,'2021-07-30',1),(213193904,'','SJ002','同济大学高等数学课本下册','学习资料',1,36.96,'2021-07-28',1);
/*!40000 ALTER TABLE `tblconsumption` ENABLE KEYS */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-07-30 20:16:17
