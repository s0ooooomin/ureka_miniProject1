CREATE DATABASE dvdvdeep;
USE dvdvdeep;

CREATE TABLE `customers` (
  `cust_id` int NOT NULL,
  `cust_name` varchar(45) DEFAULT NULL,
  `phone` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`cust_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `movies` (
  `movie_id` int NOT NULL,
  `movie_title` varchar(45) DEFAULT NULL,
  `director` varchar(45) DEFAULT NULL,
  `price` int DEFAULT NULL,
  PRIMARY KEY (`movie_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `orders` (
  `order_id` int unsigned NOT NULL AUTO_INCREMENT,
  `cust_id` int NOT NULL,
  `movie_id` int NOT NULL,
  `saleprice` int DEFAULT NULL,
  `borrowdate` date NOT NULL,
  `returndate` date DEFAULT NULL,
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
