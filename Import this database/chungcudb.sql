CREATE DATABASE  IF NOT EXISTS `chungcu` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `chungcu`;
-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: localhost    Database: chungcu
-- ------------------------------------------------------
-- Server version	8.0.42

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
-- Table structure for table `hokhau`
--

DROP TABLE IF EXISTS `hokhau`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hokhau` (
  `sohokhau` int NOT NULL,
  `sonha` varchar(50) DEFAULT NULL,
  `duong` varchar(100) DEFAULT NULL,
  `phuong` varchar(100) DEFAULT NULL,
  `quan` varchar(100) DEFAULT NULL,
  `ngaylamhokhau` date DEFAULT NULL,
  `area` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`sohokhau`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hokhau`
--

LOCK TABLES `hokhau` WRITE;
/*!40000 ALTER TABLE `hokhau` DISABLE KEYS */;
INSERT INTO `hokhau` VALUES (1001,'123','Văn Phú','Phường A','Quận X','2023-01-01',NULL),(1002,'456','Lê Lợi','Phường B','Quận Y','2023-02-15',NULL),(1003,'789','Hai Bà Trưng','Hà Nội','Quận Z','2025-04-29',NULL),(1004,'12','13','14','15','2025-06-03',100.00),(1005,'1','2','4','5','2025-06-03',2123.00);
/*!40000 ALTER TABLE `hokhau` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `khoanthu`
--

DROP TABLE IF EXISTS `khoanthu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `khoanthu` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ngaytao` date DEFAULT NULL,
  `thoihan` date DEFAULT NULL,
  `tenkhoanthu` varchar(100) DEFAULT NULL,
  `batbuoc` tinyint(1) DEFAULT NULL,
  `ghichu` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `khoanthu`
--

LOCK TABLES `khoanthu` WRITE;
/*!40000 ALTER TABLE `khoanthu` DISABLE KEYS */;
INSERT INTO `khoanthu` VALUES (1,'2023-01-01','2023-12-31','Phí dịch vụ chung cư',1,'Phí bắt buộc hàng tháng'),(2,'2023-01-01','2023-12-31','Phí gửi xe máy',1,'Phí gửi xe máy hàng tháng'),(3,'2023-01-01','2023-12-31','Quỹ từ thiện',0,'Đóng góp tự nguyện'),(4,'2025-05-30','2025-05-31','Phí HIV',0,'Nộp nhanh nha'),(6,'2025-05-31','2025-06-07','Sinh viên dốt',0,'haha'),(7,'2025-05-31','2025-06-06','Học sinh Dốt',0,'haha'),(8,'2025-06-05','2025-07-03','Diệt 36',1,''),(9,'2025-06-11','2025-06-25','asf',1,'');
/*!40000 ALTER TABLE `khoanthu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lichsu_hokhau`
--

DROP TABLE IF EXISTS `lichsu_hokhau`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lichsu_hokhau` (
  `id` int NOT NULL AUTO_INCREMENT,
  `hokhau_id` int DEFAULT NULL,
  `nhankhau_id` int DEFAULT NULL,
  `loaithaydoi` enum('them','xoa') DEFAULT NULL,
  `thoigian` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `hokhau_id` (`hokhau_id`),
  KEY `nhankhau_id` (`nhankhau_id`),
  CONSTRAINT `lichsu_hokhau_ibfk_1` FOREIGN KEY (`hokhau_id`) REFERENCES `hokhau` (`sohokhau`),
  CONSTRAINT `lichsu_hokhau_ibfk_2` FOREIGN KEY (`nhankhau_id`) REFERENCES `nhankhau` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lichsu_hokhau`
--

LOCK TABLES `lichsu_hokhau` WRITE;
/*!40000 ALTER TABLE `lichsu_hokhau` DISABLE KEYS */;
/*!40000 ALTER TABLE `lichsu_hokhau` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nhankhau`
--

DROP TABLE IF EXISTS `nhankhau`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nhankhau` (
  `id` int NOT NULL AUTO_INCREMENT,
  `hoten` varchar(100) DEFAULT NULL,
  `ngaysinh` date DEFAULT NULL,
  `gioitinh` enum('Nam','Nu') DEFAULT NULL,
  `dantoc` varchar(50) DEFAULT NULL,
  `cccd` varchar(20) DEFAULT NULL,
  `nghenghiep` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nhankhau`
--

LOCK TABLES `nhankhau` WRITE;
/*!40000 ALTER TABLE `nhankhau` DISABLE KEYS */;
INSERT INTO `nhankhau` VALUES (1,'Nguyễn Văn G','1985-03-15','Nam','Kinh','123456789','Kỹ sư'),(2,'Trần Thị B','1990-07-22','Nu','Kinh','987654321','Giáo viên'),(3,'Lê Văn C','1978-11-30','Nam','Tày','456789123','Công nhân'),(8,'Tuấn','2004-05-02','Nam','Mường','123343434','Sinh Viên');
/*!40000 ALTER TABLE `nhankhau` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nop_tien`
--

DROP TABLE IF EXISTS `nop_tien`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nop_tien` (
  `id` int NOT NULL AUTO_INCREMENT,
  `hokhau_id` int DEFAULT NULL,
  `khoanthu_id` int DEFAULT NULL,
  `ngaynop` date DEFAULT NULL,
  `nguoinop` varchar(100) DEFAULT NULL,
  `sotien` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `hokhau_id` (`hokhau_id`),
  KEY `khoanthu_id` (`khoanthu_id`),
  CONSTRAINT `nop_tien_ibfk_1` FOREIGN KEY (`hokhau_id`) REFERENCES `hokhau` (`sohokhau`),
  CONSTRAINT `nop_tien_ibfk_2` FOREIGN KEY (`khoanthu_id`) REFERENCES `khoanthu` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nop_tien`
--

LOCK TABLES `nop_tien` WRITE;
/*!40000 ALTER TABLE `nop_tien` DISABLE KEYS */;
INSERT INTO `nop_tien` VALUES (1,1001,1,'2023-03-01','Nguyễn Văn A',500000),(2,1001,3,'2023-03-05','Nguyễn Văn A',100000),(3,1002,1,'2023-03-02','Lê Văn C',500000),(4,1001,1,'2025-06-19','Nguyễn Văn G',5000);
/*!40000 ALTER TABLE `nop_tien` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tamtrutamvang`
--

DROP TABLE IF EXISTS `tamtrutamvang`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tamtrutamvang` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nhankhau_id` int DEFAULT NULL,
  `trangthai` enum('tam_tru','tam_vang') DEFAULT NULL,
  `diachitamtrutamvang` varchar(255) DEFAULT NULL,
  `thoigian` date DEFAULT NULL,
  `noidungdenghi` text,
  PRIMARY KEY (`id`),
  KEY `nhankhau_id` (`nhankhau_id`),
  CONSTRAINT `tamtrutamvang_ibfk_1` FOREIGN KEY (`nhankhau_id`) REFERENCES `nhankhau` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tamtrutamvang`
--

LOCK TABLES `tamtrutamvang` WRITE;
/*!40000 ALTER TABLE `tamtrutamvang` DISABLE KEYS */;
INSERT INTO `tamtrutamvang` VALUES (1,2,'tam_tru','123 Đường ABC','2023-04-01','Tạm trú tại địa phương'),(2,3,'tam_vang','456 Đường XYZ','2023-04-10','Đi công tác dài hạn'),(3,8,'tam_tru','1','2022-02-02','asdf'),(4,8,'tam_vang','4356','2022-02-02','asdf');
/*!40000 ALTER TABLE `tamtrutamvang` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `thanhvien_hokhau`
--

DROP TABLE IF EXISTS `thanhvien_hokhau`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `thanhvien_hokhau` (
  `hokhau_id` int NOT NULL,
  `nhankhau_id` int NOT NULL,
  `ngaythem` date DEFAULT NULL,
  `quanhevoichuho` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`hokhau_id`,`nhankhau_id`),
  KEY `nhankhau_id` (`nhankhau_id`),
  CONSTRAINT `thanhvien_hokhau_ibfk_1` FOREIGN KEY (`hokhau_id`) REFERENCES `hokhau` (`sohokhau`),
  CONSTRAINT `thanhvien_hokhau_ibfk_2` FOREIGN KEY (`nhankhau_id`) REFERENCES `nhankhau` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `thanhvien_hokhau`
--

LOCK TABLES `thanhvien_hokhau` WRITE;
/*!40000 ALTER TABLE `thanhvien_hokhau` DISABLE KEYS */;
INSERT INTO `thanhvien_hokhau` VALUES (1001,1,'2023-01-02','Chủ hộ'),(1001,2,'2023-01-02','Thành viên'),(1002,3,'2023-02-16','Chủ hộ'),(1003,8,'2025-05-31','Thành viên');
/*!40000 ALTER TABLE `thanhvien_hokhau` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `username` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  `vaitro` enum('ke_toan','to_truong') NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('admin','123456','ke_toan'),('truongto','password123','to_truong');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-06-13 21:42:55
