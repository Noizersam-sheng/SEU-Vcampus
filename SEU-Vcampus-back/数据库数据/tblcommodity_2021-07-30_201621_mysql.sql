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
-- Table structure for table `tblcommodity`
--

DROP TABLE IF EXISTS `tblcommodity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tblcommodity` (
  `commodity_id` varchar(255) NOT NULL,
  `type` varchar(255) NOT NULL,
  `commodity_name` varchar(255) NOT NULL,
  `price` double NOT NULL,
  `value` double NOT NULL,
  `discount` double NOT NULL,
  `time` date NOT NULL,
  `number` int NOT NULL,
  `hand` enum('一手','二手') NOT NULL,
  `poster` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`commodity_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblcommodity`
--

/*!40000 ALTER TABLE `tblcommodity` DISABLE KEYS */;
INSERT INTO `tblcommodity` VALUES ('2131912467351','学习资料','微机原理 9系教材 二手 附笔记',10,0,0.05,'2021-07-30',1,'二手','213191246','E:\\MyNewDesktop\\技能实训\\learning-workspace\\VCampus\\CommodityImage\\微机原理（二手）.png'),('2131912467547','学习资料','中国近现代史 二手 全新 低价甩卖',10,0,0.1,'2021-07-30',1,'二手','213191246','E:\\MyNewDesktop\\技能实训\\learning-workspace\\VCampus\\CommodityImage\\现代史（二手）.png'),('2131939042439','学习资料','概率论二手教材 附笔记',30,0,0.1,'2021-07-29',1,'二手','213193904','E:\\MyNewDesktop\\技能实训\\learning-workspace\\VCampus\\CommodityImage\\概率论（二手）.png'),('2131939046104','学习资料','Matlab编程 经典入门教材 二手 全新',50,0,0.15,'2021-07-29',1,'二手','213193904','E:\\MyNewDesktop\\技能实训\\learning-workspace\\VCampus\\CommodityImage\\matlab（二手）.png'),('A0011X','衣物&饰品','双肩背包 大容量',50,0,0.8,'2021-07-30',50,'一手','','E:\\MyNewDesktop\\技能实训\\learning-workspace\\VCampus\\CommodityImage\\书包.png'),('SJ002','学习资料','同济大学高等数学课本下册',42,0,0.88,'2021-07-28',190,'一手','','E:\\MyNewDesktop\\技能实训\\learning-workspace\\VCampus\\CommodityImage\\高等数学下册.png'),('XX001','学习用品','超大容量便携U盘',30,0,0.5,'2021-07-28',20,'一手','','E:\\MyNewDesktop\\技能实训\\learning-workspace\\VCampus\\CommodityImage\\U盘.png'),('XX002','学习用品','快充便携蓝牙耳机',20,0,1,'2021-07-28',6,'一手','','E:\\MyNewDesktop\\技能实训\\learning-workspace\\VCampus\\CommodityImage\\蓝牙耳机.png'),('XX0020','学习用品','震撼4D音效有线耳机',60,0,0.7,'2021-07-30',40,'一手','','E:\\MyNewDesktop\\技能实训\\learning-workspace\\VCampus\\CommodityImage\\有线耳机.png'),('YW001','衣物&饰品','黑色短裙优雅修身',100,0,0.9,'2021-07-28',9,'一手','','E:\\MyNewDesktop\\技能实训\\learning-workspace\\VCampus\\CommodityImage\\短裙.png'),('YW0010','衣物&饰品','立方体项链七夕浪漫礼物',100,0,0.5,'2021-07-30',20,'一手','','E:\\MyNewDesktop\\技能实训\\learning-workspace\\VCampus\\CommodityImage\\项链.png'),('YW002','衣物&饰品','橙色大码帆布鞋',50,0,1,'2021-07-28',15,'一手','','E:\\MyNewDesktop\\技能实训\\learning-workspace\\VCampus\\CommodityImage\\帆布鞋.png'),('YW003','衣物&饰品','T恤女装清新',30,0,0.7,'2021-07-28',30,'一手','','E:\\MyNewDesktop\\技能实训\\learning-workspace\\VCampus\\CommodityImage\\T恤.png'),('YW004','衣物&饰品','运动鞋男鞋户外',200,0,0.3,'2021-07-28',40,'一手','','E:\\MyNewDesktop\\技能实训\\learning-workspace\\VCampus\\CommodityImage\\运动鞋.png');
/*!40000 ALTER TABLE `tblcommodity` ENABLE KEYS */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-07-30 20:16:23
