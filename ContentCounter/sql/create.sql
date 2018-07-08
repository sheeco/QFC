/*
SQLyog v10.2
MySQL - 5.1.72-community : Database - mybatis
*********************************************************************
*/

CREATE DATABASE ContentCounter;

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
/*Table structure for table `items` */

CREATE TABLE `query` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `url` varchar(2083) NOT NULL COMMENT 'URL',
  `title` varchar(100) COMMENT '标题',
  `count_character` int(11) COMMENT '总字数',
  `count_chinese` int(11) COMMENT '中文字数',
  `count_english` int(11) COMMENT '英文字数',
  `count_punctuation` int(11) COMMENT '标点符号数',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;