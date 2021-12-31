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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alert`
--

LOCK TABLES `alert` WRITE;
/*!40000 ALTER TABLE `alert` DISABLE KEYS */;
INSERT INTO `alert` VALUES (4,1,'2021-12-24 17:04:30',192);
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
  `order_status` varchar(45) NOT NULL DEFAULT 'PENDING',
  `userid` int DEFAULT NULL,
  `subscriptionId` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `order_userid_idx` (`userid`),
  KEY `order_subscriptionid_idx` (`subscriptionId`),
  CONSTRAINT `order_subscriptionid` FOREIGN KEY (`subscriptionId`) REFERENCES `subscription` (`id`),
  CONSTRAINT `order_userid` FOREIGN KEY (`userid`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=73 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer_order`
--

LOCK TABLES `customer_order` WRITE;
/*!40000 ALTER TABLE `customer_order` DISABLE KEYS */;
INSERT INTO `customer_order` VALUES (65,'2021-12-29 11:40:34','ACCEPTED',1,66),(66,'2021-12-29 11:40:50','ACCEPTED',1,67),(67,'2021-12-29 11:41:05','ACCEPTED',1,68),(68,'2021-12-29 11:41:26','ACCEPTED',5,69),(69,'2021-12-29 11:42:37','ACCEPTED',5,70),(70,'2021-12-29 11:47:49','ACCEPTED',1,71),(71,'2021-12-30 14:15:12','REJECTED',1,72),(72,'2021-12-30 14:24:46','REJECTED',5,73);
/*!40000 ALTER TABLE `customer_order` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `setInsolventUser` AFTER UPDATE ON `customer_order` FOR EACH ROW BEGIN
	IF new.order_status = "REJECTED" THEN
		UPDATE user SET insolvent = 1 WHERE user.id = new.userid;
        END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `addSale` AFTER UPDATE ON `customer_order` FOR EACH ROW begin 
	if (new.order_status = "ACCEPTED") then
		if((select vpid from salesreport where orderid = new.id) not in (select vpid from packagesales)) then
			insert into packagesales values (
				(select VP.packageid from salesreport as SP, validityperiod as VP where SP.orderid = new.id and SP.vpid = VP.id),
				(select vpid from salesreport where orderid = new.id),
                1,
                (select base_amount from salesreport where orderid = new.id),
                (select total_amount from salesreport where orderid = new.id),
                (select num_of_products from salesreport where orderid = new.id));
		else 
			update packagesales set 
				num_sales = num_sales + 1,
                base_amount = base_amount + (select base_amount from salesreport where orderid = new.id),
                total_amount = total_amount + (select base_amount from salesreport where orderid = new.id),
                num_products = num_products + (select num_of_products from salesreport where orderid = new.id)
			where vpid = (select vpid from salesreport where orderid = new.id);
		end if;
	end if;
end */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `raiseAlert` AFTER UPDATE ON `customer_order` FOR EACH ROW BEGIN 
	IF new.order_status = "REJECTED" THEN
		IF(new.userid NOT IN (select userid FROM alert)) THEN 
			IF(
				(SELECT COUNT(*) FROM customer_order WHERE userid = new.userid AND order_status = "REJECTED") > 2
			) THEN
				INSERT INTO alert (userId, datetime, amount) VALUES (new.userid, NOW(), 
					(SELECT total_amount FROM salesreport WHERE orderid = new.id));
			END IF;
		ELSE 
			UPDATE alert SET amount = amount + (SELECT total_amount FROM salesreport WHERE orderid = new.id)
            WHERE userid = new.userid;
        END IF;
	END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

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
) ENGINE=InnoDB AUTO_INCREMENT=78 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `login_log`
--

LOCK TABLES `login_log` WRITE;
/*!40000 ALTER TABLE `login_log` DISABLE KEYS */;
INSERT INTO `login_log` VALUES (69,'2021-12-29 12:40:24',1),(70,'2021-12-29 12:41:18',5),(71,'2021-12-29 12:42:01',1),(72,'2021-12-29 12:42:20',5),(73,'2021-12-29 12:47:38',1),(74,'2021-12-29 19:55:29',1),(75,'2021-12-30 09:17:12',1),(76,'2021-12-30 15:15:01',1),(77,'2021-12-30 15:24:38',5);
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
-- Table structure for table `packagesales`
--

DROP TABLE IF EXISTS `packagesales`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `packagesales` (
  `packageid` int DEFAULT NULL,
  `vpid` int NOT NULL,
  `num_sales` int DEFAULT NULL,
  `base_amount` int DEFAULT NULL,
  `total_amount` int DEFAULT NULL,
  `num_products` int DEFAULT NULL,
  PRIMARY KEY (`vpid`),
  KEY `packagesales_packageid_idx` (`packageid`),
  CONSTRAINT `packagesales_packageid` FOREIGN KEY (`packageid`) REFERENCES `package` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `packagesales`
--

LOCK TABLES `packagesales` WRITE;
/*!40000 ALTER TABLE `packagesales` DISABLE KEYS */;
INSERT INTO `packagesales` VALUES (29,17,1,192,912,1),(30,19,1,168,1128,2),(30,20,3,540,1620,2),(31,23,1,540,1620,2);
/*!40000 ALTER TABLE `packagesales` ENABLE KEYS */;
UNLOCK TABLES;

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
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `addProduct` AFTER INSERT ON `product` FOR EACH ROW begin
	if id = new.id then
		insert into productsales values (new.id, 0, 0);
    end if;
end */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Temporary view structure for view `productreport`
--

DROP TABLE IF EXISTS `productreport`;
/*!50001 DROP VIEW IF EXISTS `productreport`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `productreport` AS SELECT 
 1 AS `productId`,
 1 AS `name`,
 1 AS `sales`,
 1 AS `salesValue`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `productsales`
--

DROP TABLE IF EXISTS `productsales`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `productsales` (
  `productid` int NOT NULL,
  `num_sales` int DEFAULT NULL,
  `sales_value` int DEFAULT NULL,
  PRIMARY KEY (`productid`),
  CONSTRAINT `productsales_productid` FOREIGN KEY (`productid`) REFERENCES `product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `productsales`
--

LOCK TABLES `productsales` WRITE;
/*!40000 ALTER TABLE `productsales` DISABLE KEYS */;
INSERT INTO `productsales` VALUES (106,0,0),(107,0,0),(108,0,0),(109,0,0),(110,0,0),(111,0,0),(112,0,0),(113,0,0),(114,0,0);
/*!40000 ALTER TABLE `productsales` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `salesreport`
--

DROP TABLE IF EXISTS `salesreport`;
/*!50001 DROP VIEW IF EXISTS `salesreport`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `salesreport` AS SELECT 
 1 AS `userid`,
 1 AS `orderid`,
 1 AS `subid`,
 1 AS `vpid`,
 1 AS `base_amount`,
 1 AS `total_amount`,
 1 AS `num_of_products`,
 1 AS `order_status`*/;
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
) ENGINE=InnoDB AUTO_INCREMENT=74 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subscription`
--

LOCK TABLES `subscription` WRITE;
/*!40000 ALTER TABLE `subscription` DISABLE KEYS */;
INSERT INTO `subscription` VALUES (66,17,'2022-01-01'),(67,19,'2022-01-01'),(68,23,'2022-01-01'),(69,20,'2022-01-01'),(70,20,'2022-01-01'),(71,20,'2022-02-01'),(72,17,'2022-01-01'),(73,17,'2022-01-01');
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
INSERT INTO `subscriptionproduct` VALUES (66,106),(67,106),(69,106),(67,107),(70,107),(68,110),(68,112);
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
INSERT INTO `user` VALUES (1,'admin','admin','admin@telco.com',1),(5,'giacomino','giacomino','giacomino@gmail.com',1),(6,'valeria','valeria9','valeria@gmail.com',0),(7,'leomessi','leomessi','leomessi@mail.com',0);
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
-- Final view structure for view `productreport`
--

/*!50001 DROP VIEW IF EXISTS `productreport`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `productreport` AS select `P`.`id` AS `productId`,`P`.`name` AS `name`,count(`P`.`id`) AS `sales`,sum((`P`.`fee` * `VP`.`months`)) AS `salesValue` from (((`product` `P` join `subscriptionproduct` `SP`) join `salesreport` `S`) join `validityperiod` `VP`) where ((`P`.`id` = `SP`.`productid`) and (`s`.`subid` = `SP`.`subscriptionid`) and (`VP`.`id` = `s`.`vpid`) and (`s`.`order_status` = 'ACCEPTED')) group by `P`.`id`,`P`.`name`,`P`.`fee` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `salesreport`
--

/*!50001 DROP VIEW IF EXISTS `salesreport`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `salesreport` AS select `U`.`id` AS `userid`,`O`.`id` AS `orderid`,`S`.`id` AS `subid`,`V`.`id` AS `vpid`,(`V`.`fee` * `V`.`months`) AS `base_amount`,((`V`.`fee` * `V`.`months`) + (`V`.`months` * coalesce(sum(`P`.`fee`),0))) AS `total_amount`,coalesce(count(`P`.`id`),0) AS `num_of_products`,`O`.`order_status` AS `order_status` from (((((`customer_order` `O` join `subscription` `S` on((`O`.`subscriptionId` = `S`.`id`))) join `validityperiod` `V` on((`S`.`vpid` = `V`.`id`))) join `user` `U` on((`U`.`id` = `O`.`userid`))) left join `subscriptionproduct` `SP` on((`S`.`id` = `SP`.`subscriptionid`))) left join `product` `P` on((`SP`.`productid` = `P`.`id`))) group by `U`.`id`,`O`.`id`,`S`.`id`,`V`.`id`,`V`.`months` */;
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

-- Dump completed on 2021-12-31  9:17:41
