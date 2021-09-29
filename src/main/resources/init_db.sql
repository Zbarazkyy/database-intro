CREATE DATABASE `library_db` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;
CREATE TABLE `literary_formats` (
                                    `id` bigint NOT NULL AUTO_INCREMENT,
                                    `format` varchar(255) DEFAULT NULL,
                                    `is_deleted` tinyint NOT NULL DEFAULT '0',
                                    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3;
