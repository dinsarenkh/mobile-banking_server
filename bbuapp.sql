-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost:8889
-- Generation Time: May 22, 2022 at 04:02 PM
-- Server version: 5.7.34
-- PHP Version: 8.0.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `bbuapp`
--

-- --------------------------------------------------------

--
-- Table structure for table `bbu_category`
--

CREATE TABLE `bbu_category` (
  `id` bigint(20) NOT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `bbu_product`
--

CREATE TABLE `bbu_product` (
  `id` bigint(20) NOT NULL,
  `category_id` varchar(255) DEFAULT NULL,
  `discount` double NOT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `price` double NOT NULL,
  `qty` double NOT NULL,
  `status` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `bbu_refresh_token`
--

CREATE TABLE `bbu_refresh_token` (
  `id` bigint(20) NOT NULL,
  `expiry_date` datetime NOT NULL,
  `token` varchar(255) NOT NULL,
  `user_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `bbu_refresh_token`
--

INSERT INTO `bbu_refresh_token` (`id`, `expiry_date`, `token`, `user_id`) VALUES
(1, '2022-05-22 22:44:09', 'f47613fb-37e5-4ef2-a0bb-4401425d4c9e', 1),
(2, '2022-05-22 22:46:25', '94b959cd-6720-45fd-ba06-abda421a50cf', 1),
(3, '2022-05-22 22:48:53', '0baa8dbc-a3f1-470d-80ac-8982ca5c57c9', 1),
(4, '2022-05-22 22:55:31', 'b99087ce-c522-4c68-a5ab-e9621f0f6e9c', 1),
(5, '2022-05-23 22:54:27', '23b9baee-2e98-4153-a6f8-7e26631a9a2c', 1),
(6, '2022-05-23 23:00:39', 'c9523d2b-923e-402d-a99f-3c498570cd46', 1);

-- --------------------------------------------------------

--
-- Table structure for table `bbu_roles`
--

CREATE TABLE `bbu_roles` (
  `id` int(11) NOT NULL,
  `name` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `bbu_roles`
--

INSERT INTO `bbu_roles` (`id`, `name`) VALUES
(1, 'ROLE_USER'),
(2, 'ROLE_MODERATOR'),
(3, 'ROLE_ADMIN');

-- --------------------------------------------------------

--
-- Table structure for table `bbu_users`
--

CREATE TABLE `bbu_users` (
  `id` bigint(20) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `bbu_users`
--

INSERT INTO `bbu_users` (`id`, `email`, `password`, `phone`, `status`, `username`) VALUES
(1, 'user@gmail.com', '$2a$10$UmNtkj.a97ff8qCkDkJyu.4hAbV9b3ZAW9Kew.lcFunER/Y79F9iK', '0962505045', 'ACT', 'user');

-- --------------------------------------------------------

--
-- Table structure for table `bbu_user_roles`
--

CREATE TABLE `bbu_user_roles` (
  `user_id` bigint(20) NOT NULL,
  `role_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `bbu_user_roles`
--

INSERT INTO `bbu_user_roles` (`user_id`, `role_id`) VALUES
(1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `hibernate_sequence`
--

CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `hibernate_sequence`
--

INSERT INTO `hibernate_sequence` (`next_val`) VALUES
(7);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `bbu_category`
--
ALTER TABLE `bbu_category`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `bbu_product`
--
ALTER TABLE `bbu_product`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `bbu_refresh_token`
--
ALTER TABLE `bbu_refresh_token`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_od6iqasjnprcvjbvasm084fyw` (`token`),
  ADD KEY `FK8uufrroib1m3jccw11a27bin3` (`user_id`);

--
-- Indexes for table `bbu_roles`
--
ALTER TABLE `bbu_roles`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `bbu_users`
--
ALTER TABLE `bbu_users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK7apqrvpnfppot791evyw9wkjr` (`username`),
  ADD UNIQUE KEY `UK3emg11krtm5rgakh9mlels09r` (`email`);

--
-- Indexes for table `bbu_user_roles`
--
ALTER TABLE `bbu_user_roles`
  ADD PRIMARY KEY (`user_id`,`role_id`),
  ADD KEY `FKjcbkfs5r4uqn1qjsn7x0iwrnc` (`role_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `bbu_category`
--
ALTER TABLE `bbu_category`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `bbu_product`
--
ALTER TABLE `bbu_product`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `bbu_roles`
--
ALTER TABLE `bbu_roles`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `bbu_users`
--
ALTER TABLE `bbu_users`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `bbu_refresh_token`
--
ALTER TABLE `bbu_refresh_token`
  ADD CONSTRAINT `FK8uufrroib1m3jccw11a27bin3` FOREIGN KEY (`user_id`) REFERENCES `bbu_users` (`id`);

--
-- Constraints for table `bbu_user_roles`
--
ALTER TABLE `bbu_user_roles`
  ADD CONSTRAINT `FKjcbkfs5r4uqn1qjsn7x0iwrnc` FOREIGN KEY (`role_id`) REFERENCES `bbu_roles` (`id`),
  ADD CONSTRAINT `FKq1x6uktekhlu39g9443ih6dgr` FOREIGN KEY (`user_id`) REFERENCES `bbu_users` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
