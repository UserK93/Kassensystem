-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server Version:               8.0.22 - MySQL Community Server - GPL
-- Server Betriebssystem:        Win64
-- HeidiSQL Version:             11.2.0.6213
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Exportiere Datenbank Struktur für test
CREATE DATABASE IF NOT EXISTS `test` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `test`;

-- Exportiere Struktur von Tabelle test.kunden
CREATE TABLE IF NOT EXISTS `kunden` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `Vorname` varchar(50) DEFAULT NULL,
  `Nachname` varchar(50) DEFAULT NULL,
  `Strasse` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `PLZ` char(50) DEFAULT NULL,
  `Wohnort` char(50) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Exportiere Daten aus Tabelle test.kunden: ~6 rows (ungefähr)
/*!40000 ALTER TABLE `kunden` DISABLE KEYS */;
REPLACE INTO `kunden` (`ID`, `Vorname`, `Nachname`, `Strasse`, `PLZ`, `Wohnort`) VALUES
	(1, 'Franz', 'Kafka', 'Friedrichstraße 64', '50285', 'Köln'),
	(2, 'Jürgen', 'Drews', 'Klosstraße 12', '45286', 'Duisburg'),
	(3, 'Hannes', 'Albrecht', 'Drachenstraße 33', '23586', 'Marl'),
	(4, 'Silke', 'Rulfendorff', 'Lochstraße 44', '14589', 'Düsseldorf'),
	(5, 'Maurizius', 'Johansson', 'Zahnstraße 4', '52728', 'Ratingen'),
	(6, 'Gabi', 'Dedo', 'Gabystraße 33', '50825', 'Köln');
/*!40000 ALTER TABLE `kunden` ENABLE KEYS */;

-- Exportiere Struktur von Tabelle test.lager
CREATE TABLE IF NOT EXISTS `lager` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `Produktname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `Preis` double DEFAULT NULL,
  `Anzahl` int DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `Produktname` (`Produktname`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Exportiere Daten aus Tabelle test.lager: ~7 rows (ungefähr)
/*!40000 ALTER TABLE `lager` DISABLE KEYS */;
REPLACE INTO `lager` (`ID`, `Produktname`, `Preis`, `Anzahl`) VALUES
	(4, 'Monitor', 200, 79),
	(27, 'Möhre', 2.99, 1),
	(28, 'Tisch', 199, 123),
	(29, 'komputa', 539.5, 74),
	(30, 'Pommes', 2.99, 287),
	(32, 'Computer', 299, 120),
	(39, 'Flauschi', 1000000, 1);
/*!40000 ALTER TABLE `lager` ENABLE KEYS */;

-- Exportiere Struktur von Tabelle test.testeinkauf
CREATE TABLE IF NOT EXISTS `testeinkauf` (
  `id` int NOT NULL AUTO_INCREMENT,
  `PersonID` int DEFAULT NULL,
  `Produktname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0',
  `Preis` float NOT NULL DEFAULT '0',
  `Anzahl` int DEFAULT NULL,
  `Datum` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `PersonID` (`PersonID`),
  CONSTRAINT `FK_testeinkauf_personen` FOREIGN KEY (`PersonID`) REFERENCES `kunden` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=74 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Exportiere Daten aus Tabelle test.testeinkauf: ~58 rows (ungefähr)
/*!40000 ALTER TABLE `testeinkauf` DISABLE KEYS */;
REPLACE INTO `testeinkauf` (`id`, `PersonID`, `Produktname`, `Preis`, `Anzahl`, `Datum`) VALUES
	(5, 1, 'Hose', 400, 2, '2021-02-16'),
	(6, 1, 'Hose', 4400, 22, '2021-02-16'),
	(7, 1, 'Hose', 400, 2, '2021-02-16'),
	(8, 3, 'Hose', 2400, 12, '2021-02-16'),
	(9, 4, 'Hose', 4400, 22, '2021-02-16'),
	(10, 3, 'Kopfhörer', 1999, 10, '2021-02-16'),
	(11, 4, 'Hose', 400, 2, '2021-02-16'),
	(12, 4, 'Kopfhörer', 399.8, 2, '2021-02-16'),
	(16, 3, 'Kopfhörer', 399.8, 2, '2021-02-16'),
	(17, 3, 'Kopfhörer', 399.8, 2, '2021-02-16'),
	(19, 3, 'Kopfhörer', 599.7, 3, '2021-02-16'),
	(21, 6, 'Kopfhörer', 9995, 50, '2021-02-16'),
	(22, 6, 'Hose', 400, 2, '2021-02-16'),
	(23, 6, 'Apfel', 3.98, 2, '2021-03-02'),
	(24, 6, 'Hose', 240, 2, '2021-03-02'),
	(25, 1, 'Kopfhörer', 399.8, 2, '2021-03-10'),
	(26, 1, 'Zitrone', 4, 2, '2021-03-10'),
	(27, 1, 'Kopfhörer', 399.8, 2, '2021-03-10'),
	(28, 1, 'Kopfhörer', 399.8, 2, '2021-03-10'),
	(29, 1, 'Zitrone', 20, 10, '2021-03-10'),
	(30, 1, 'Hose', 600, 5, '2021-03-10'),
	(32, 1, 'Lenor', 5.98, 2, '2021-03-10'),
	(33, 1, 'Lenor', 5.98, 2, '2021-03-10'),
	(34, 6, 'Tomate', 3.98, 2, '2021-03-10'),
	(35, 6, 'Rosen', 99, 100, '2021-03-10'),
	(36, 1, 'Rosen', 1.98, 2, '2021-03-10'),
	(37, 1, 'Rosen', 2.97, 3, '2021-03-10'),
	(38, 6, 'Kopfhörer', 799.6, 4, '2021-03-10'),
	(39, 6, 'Kopfhörer', 799.6, 4, '2021-03-10'),
	(40, 1, 'Rosen', 2.97, 3, '2021-03-10'),
	(41, 1, 'Rosen', 2.97, 3, '2021-03-10'),
	(42, 1, 'Rosen', 2.97, 3, '2021-03-10'),
	(43, 1, 'Tomate', 5.97, 3, '2021-03-10'),
	(44, 1, 'Rosen', 39.6, 40, '2021-03-10'),
	(45, 1, 'Rosen', 39.6, 40, '2021-03-10'),
	(46, 3, 'Tisch', 796, 4, '2021-04-06'),
	(47, 3, 'Tisch', 1990, 10, '2021-04-06'),
	(48, 3, 'Pommes', 35.88, 12, '2021-04-08'),
	(53, 3, 'Maus', 10, 5, '2021-09-01'),
	(54, 1, 'Banane', 3.96, 4, '2021-09-01'),
	(55, 1, 'Maus', 6, 3, '2021-09-01'),
	(56, 1, 'Tisch', 398, 2, '2021-09-01'),
	(57, 1, 'Banane', 0.99, 1, '2021-09-01'),
	(58, 1, 'Maus', 2, 1, '2021-09-01'),
	(59, 1, 'Pommes', 2.99, 1, '2021-09-01'),
	(60, 1, 'Banane', 2.97, 3, '2021-09-01'),
	(61, 1, 'Maus', 2, 1, '2021-09-03'),
	(62, 1, 'Banane', 2.97, 3, '2021-09-03'),
	(63, 1, 'Tisch', 240, 2, '2021-09-03'),
	(64, 1, 'Banane', 4.95, 5, '2021-09-03'),
	(65, 1, 'Monitor', 400, 2, '2021-09-03'),
	(66, 1, 'Tisch', 796, 4, '2021-09-03'),
	(68, 1, 'PC', 299, 1, '2021-09-03'),
	(69, 1, 'Tisch', 597, 3, '2021-09-03'),
	(70, 1, 'Monitor', 400, 2, '2021-09-06'),
	(71, 1, 'Monitor', 1000, 5, '2021-09-17'),
	(72, 1, 'Tisch', 398, 2, '2021-09-17'),
	(73, 1, 'Monitor', 200, 1, '2021-09-17');
/*!40000 ALTER TABLE `testeinkauf` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
