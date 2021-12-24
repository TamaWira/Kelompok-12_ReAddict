/*
SQLyog Ultimate
MySQL - 10.4.11-MariaDB : Database - db_progmob_12
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`db_progmob_12` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `db_progmob_12`;

/*Table structure for table `tb_buku` */

DROP TABLE IF EXISTS `tb_buku`;

CREATE TABLE `tb_buku` (
  `id_buku` int(11) NOT NULL AUTO_INCREMENT,
  `id_kategori` int(11) DEFAULT NULL,
  `nama_buku` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id_buku`),
  KEY `id_kategori` (`id_kategori`),
  CONSTRAINT `tb_buku_ibfk_1` FOREIGN KEY (`id_kategori`) REFERENCES `tb_kategori` (`id_kategori`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

/*Data for the table `tb_buku` */

LOCK TABLES `tb_buku` WRITE;

insert  into `tb_buku`(`id_buku`,`id_kategori`,`nama_buku`) values 
(1,1,'Laskar Pelangi'),
(2,2,'Lie in April');

UNLOCK TABLES;

/*Table structure for table `tb_kategori` */

DROP TABLE IF EXISTS `tb_kategori`;

CREATE TABLE `tb_kategori` (
  `id_kategori` int(11) NOT NULL AUTO_INCREMENT,
  `kategori` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id_kategori`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;

/*Data for the table `tb_kategori` */

LOCK TABLES `tb_kategori` WRITE;

insert  into `tb_kategori`(`id_kategori`,`kategori`) values 
(1,'Novel'),
(2,'Roman'),
(3,'Komik'),
(5,'Biografi');

UNLOCK TABLES;

/*Table structure for table `tb_member` */

DROP TABLE IF EXISTS `tb_member`;

CREATE TABLE `tb_member` (
  `id_member` int(11) NOT NULL AUTO_INCREMENT,
  `nama_lengkap` varchar(50) DEFAULT NULL,
  `username` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `gender` enum('P','L') DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `kesukaan_membaca` int(11) DEFAULT NULL,
  `buku_favorit` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id_member`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

/*Data for the table `tb_member` */

LOCK TABLES `tb_member` WRITE;

insert  into `tb_member`(`id_member`,`nama_lengkap`,`username`,`email`,`gender`,`password`,`kesukaan_membaca`,`buku_favorit`) values 
(1,'I Putu Bayu Adhya Wiratama','yuutama','wira.tama20012001@gmail.com','L','ironman',100,'laskar pelangi');

UNLOCK TABLES;

/*Table structure for table `tb_peminjaman` */

DROP TABLE IF EXISTS `tb_peminjaman`;

CREATE TABLE `tb_peminjaman` (
  `id_peminjaman` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL,
  `judul_buku` varchar(50) DEFAULT NULL,
  `tanggal_peminjaman` date DEFAULT NULL,
  `tanggal_kembali` date DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id_peminjaman`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;

/*Data for the table `tb_peminjaman` */

LOCK TABLES `tb_peminjaman` WRITE;

insert  into `tb_peminjaman`(`id_peminjaman`,`username`,`judul_buku`,`tanggal_peminjaman`,`tanggal_kembali`,`status`) values 
(1,'yuutama','Laskar Pelangi','2021-12-22','2025-01-10','Kembali'),
(3,'yuutama','Bill Gates','2021-12-22','2022-01-10','Pinjam'),
(4,'yuutama','Elon Musk','2021-12-22','2022-01-10','Pinjam');

UNLOCK TABLES;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
