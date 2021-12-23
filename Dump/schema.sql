CREATE DATABASE  IF NOT EXISTS `telco_db` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `telco_db`;
-- MySQL dump 10.13  Distrib 8.0.27, for macos11 (x86_64)
--
-- Host: localhost    Database: telco_db
-- ------------------------------------------------------
-- Server version	8.0.27

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
-- Table structure for table `alert`
--

DROP TABLE IF EXISTS `alert`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `alert` (
  `id` int NOT NULL AUTO_INCREMENT,
  `userId` int NOT NULL,
  `datetime` timestamp NOT NULL,
  `amount` double NOT NULL,
  PRIMARY KEY (`id`),
  KEY `userId_idx` (`userId`),
  CONSTRAINT `alert_userId` FOREIGN KEY (`userId`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alert`
--

LOCK TABLES `alert` WRITE;
/*!40000 ALTER TABLE `alert` DISABLE KEYS */;
/*!40000 ALTER TABLE `alert` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer_order`
--

DROP TABLE IF EXISTS `customer_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer_order` (
  `id` int NOT NULL AUTO_INCREMENT,
  `datetime` timestamp NOT NULL,
  `status` varchar(45) NOT NULL DEFAULT 'PENDING',
  `userid` int DEFAULT NULL,
  `subscriptionId` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `order_userid_idx` (`userid`),
  KEY `order_subscriptionid_idx` (`subscriptionId`),
  CONSTRAINT `order_subscriptionid` FOREIGN KEY (`subscriptionId`) REFERENCES `subscription` (`id`),
  CONSTRAINT `order_userid` FOREIGN KEY (`userid`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer_order`
--

LOCK TABLES `customer_order` WRITE;
/*!40000 ALTER TABLE `customer_order` DISABLE KEYS */;
INSERT INTO `customer_order` VALUES (8,'2021-11-30 11:20:06','ACCEPTED',1,9),(9,'2021-11-30 11:20:18','REJECTED',1,10),(10,'2021-11-30 11:21:48','ACCEPTED',5,11),(11,'2021-11-30 11:22:37','ACCEPTED',7,12),(12,'2021-11-30 11:24:44','ACCEPTED',6,13),(13,'2021-11-30 17:56:22','ACCEPTED',1,14),(14,'2021-11-30 18:04:52','ACCEPTED',7,15);
/*!40000 ALTER TABLE `customer_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `username` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES (1,'admin','admin');
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `login_log`
--

DROP TABLE IF EXISTS `login_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `login_log` (
  `id` int NOT NULL AUTO_INCREMENT,
  `logtime` datetime NOT NULL,
  `userid` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `userid_idx` (`userid`),
  CONSTRAINT `loginlog_userid` FOREIGN KEY (`userid`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `login_log`
--

LOCK TABLES `login_log` WRITE;
/*!40000 ALTER TABLE `login_log` DISABLE KEYS */;
INSERT INTO `login_log` VALUES (1,'2021-11-30 12:08:00',1),(2,'2021-11-30 12:12:19',1),(3,'2021-11-30 12:13:51',1),(4,'2021-11-30 12:19:46',1),(5,'2021-11-30 12:21:04',5),(6,'2021-11-30 12:22:35',7),(7,'2021-11-30 12:23:56',1),(8,'2021-11-30 12:24:17',6),(9,'2021-11-30 18:56:14',1),(10,'2021-11-30 19:04:51',7),(11,'2021-12-23 10:13:50',1),(12,'2021-12-23 10:15:28',1),(13,'2021-12-23 10:18:25',1);
/*!40000 ALTER TABLE `login_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `package`
--

DROP TABLE IF EXISTS `package`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `package` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `package`
--

LOCK TABLES `package` WRITE;
/*!40000 ALTER TABLE `package` DISABLE KEYS */;
INSERT INTO `package` VALUES (29,'Test Package #1'),(30,'Test Package #2'),(31,'Test Package #3');
/*!40000 ALTER TABLE `package` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `packageproduct`
--

DROP TABLE IF EXISTS `packageproduct`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `packageproduct` (
  `packageid` int NOT NULL,
  `productid` int NOT NULL,
  PRIMARY KEY (`packageid`,`productid`),
  KEY `packageproduct_productid_idx` (`productid`),
  CONSTRAINT `packageproduct_packageid` FOREIGN KEY (`packageid`) REFERENCES `package` (`id`),
  CONSTRAINT `packageproduct_productid` FOREIGN KEY (`productid`) REFERENCES `product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `packageproduct`
--

LOCK TABLES `packageproduct` WRITE;
/*!40000 ALTER TABLE `packageproduct` DISABLE KEYS */;
INSERT INTO `packageproduct` VALUES (29,106),(30,106),(31,106),(30,107),(29,108),(31,110),(31,112);
/*!40000 ALTER TABLE `packageproduct` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `packagepurchase`
--

DROP TABLE IF EXISTS `packagepurchase`;
/*!50001 DROP VIEW IF EXISTS `packagepurchase`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `packagepurchase` AS SELECT 
 1 AS `packageid`,
 1 AS `vpid`,
 1 AS `orderid`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `fee` double NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=115 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (106,'iPhone 13',30),(107,'Apple Watch 6',10),(108,'Bettina\'s Water Bottle',25),(109,'Apple Pencil',4),(110,'Vodafone Wi-Fi Station',25),(111,'Samsung Smart TV',20),(112,'Bose SoundLink',5),(113,'AirPods',5),(114,'AirPods Pro',8);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `sales_report`
--

DROP TABLE IF EXISTS `sales_report`;
/*!50001 DROP VIEW IF EXISTS `sales_report`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `sales_report` AS SELECT 
 1 AS `packageid`,
 1 AS `vpid`,
 1 AS `orderid`,
 1 AS `basic_value`,
 1 AS `total_value`,
 1 AS `num_of_products`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `service`
--

DROP TABLE IF EXISTS `service`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `service` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` varchar(45) NOT NULL,
  `minutes` int DEFAULT NULL,
  `sms` int DEFAULT NULL,
  `giga` int DEFAULT NULL,
  `minutesfee` double DEFAULT NULL,
  `smsfee` double DEFAULT NULL,
  `gigafee` double DEFAULT NULL,
  `packageid` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `service_packageid_idx` (`packageid`),
  CONSTRAINT `service_packageid` FOREIGN KEY (`packageid`) REFERENCES `package` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `service`
--

LOCK TABLES `service` WRITE;
/*!40000 ALTER TABLE `service` DISABLE KEYS */;
INSERT INTO `service` VALUES (30,'mobilephone',100,500,NULL,0.1,0.5,NULL,29),(31,'mobileinternet',NULL,NULL,100,NULL,NULL,1,30),(32,'fixedinternet',NULL,NULL,200,NULL,NULL,1,30),(33,'mobileinternet',NULL,NULL,50,NULL,NULL,1,31),(34,'fixedinternet',NULL,NULL,200,NULL,NULL,2,31);
/*!40000 ALTER TABLE `service` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subscription`
--

DROP TABLE IF EXISTS `subscription`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `subscription` (
  `id` int NOT NULL AUTO_INCREMENT,
  `vpid` int NOT NULL,
  `startdate` date NOT NULL,
  PRIMARY KEY (`id`),
  KEY `subscription_vpid_idx` (`vpid`),
  CONSTRAINT `subscription_vpid` FOREIGN KEY (`vpid`) REFERENCES `validityperiod` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subscription`
--

LOCK TABLES `subscription` WRITE;
/*!40000 ALTER TABLE `subscription` DISABLE KEYS */;
INSERT INTO `subscription` VALUES (9,17,'2021-12-01'),(10,19,'2021-12-01'),(11,23,'2021-12-01'),(12,20,'2021-12-01'),(13,17,'2021-12-01'),(14,17,'2021-12-01'),(15,17,'2021-12-01');
/*!40000 ALTER TABLE `subscription` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subscriptionproduct`
--

DROP TABLE IF EXISTS `subscriptionproduct`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `subscriptionproduct` (
  `subscriptionid` int NOT NULL,
  `productid` int NOT NULL,
  PRIMARY KEY (`subscriptionid`,`productid`),
  KEY `subscriptionproduct_productid_idx` (`productid`),
  CONSTRAINT `subscriptionproduct_productid` FOREIGN KEY (`productid`) REFERENCES `product` (`id`),
  CONSTRAINT `subscriptionproduct_subscriptionid` FOREIGN KEY (`subscriptionid`) REFERENCES `subscription` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subscriptionproduct`
--

LOCK TABLES `subscriptionproduct` WRITE;
/*!40000 ALTER TABLE `subscriptionproduct` DISABLE KEYS */;
INSERT INTO `subscriptionproduct` VALUES (9,106),(12,106),(14,106),(15,106),(10,107),(12,107),(9,108),(13,108),(11,110);
/*!40000 ALTER TABLE `subscriptionproduct` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `insolvent` tinyint DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'admin','admin','admin@telco.com',1),(5,'giacomino','giacomino','giacomino@gmail.com',0),(6,'valeria','valeria9','valeria@gmail.com',0),(7,'leomessi','leomessi','leomessi@mail.com',0);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `validityperiod`
--

DROP TABLE IF EXISTS `validityperiod`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `validityperiod` (
  `id` int NOT NULL AUTO_INCREMENT,
  `packageid` int NOT NULL,
  `months` int NOT NULL,
  `fee` double NOT NULL,
  PRIMARY KEY (`id`),
  KEY `validityperiod_packageid_idx` (`packageid`),
  CONSTRAINT `validityperiod_packageid` FOREIGN KEY (`packageid`) REFERENCES `package` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `validityperiod`
--

LOCK TABLES `validityperiod` WRITE;
/*!40000 ALTER TABLE `validityperiod` DISABLE KEYS */;
INSERT INTO `validityperiod` VALUES (16,29,12,10),(17,29,24,8),(18,30,12,8),(19,30,24,7),(20,30,36,5),(21,31,12,20),(22,31,24,18),(23,31,36,15);
/*!40000 ALTER TABLE `validityperiod` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Final view structure for view `packagepurchase`
--

/*!50001 DROP VIEW IF EXISTS `packagepurchase`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `packagepurchase` (`packageid`,`vpid`,`orderid`) AS select `V`.`packageid` AS `packageid`,`V`.`id` AS `id`,`O`.`id` AS `id` from ((`validityperiod` `V` join `subscription` `S`) join `customer_order` `O`) where ((`V`.`id` = `S`.`vpid`) and (`S`.`id` = `O`.`subscriptionId`) and (`O`.`status` = 'ACCEPTED')) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `sales_report`
--

/*!50001 DROP VIEW IF EXISTS `sales_report`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `sales_report` (`packageid`,`vpid`,`orderid`,`basic_value`,`total_value`,`num_of_products`) AS select `V`.`packageid` AS `packageid`,`V`.`id` AS `id`,`O`.`id` AS `id`,(`V`.`fee` * `V`.`months`) AS `V.fee*V.months`,((`V`.`fee` * `V`.`months`) + (`V`.`months` * sum(`P`.`fee`))) AS `(V.fee*V.months)+(V.months*SUM(P.fee))`,count(`P`.`fee`) AS `COUNT(P.fee)` from ((((`validityperiod` `V` join `subscription` `S`) join `customer_order` `O`) join `subscriptionproduct` `SP`) join `product` `P`) where ((`V`.`id` = `S`.`vpid`) and (`S`.`id` = `O`.`subscriptionId`) and (`S`.`id` = `SP`.`subscriptionid`) and (`P`.`id` = `SP`.`productid`) and (`O`.`status` = 'ACCEPTED')) group by `V`.`packageid`,`V`.`id`,`O`.`id`,`V`.`fee`,`V`.`months` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-12-23 10:44:30
