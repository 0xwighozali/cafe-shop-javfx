-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 28, 2023 at 12:11 PM
-- Server version: 10.4.16-MariaDB
-- PHP Version: 7.3.24

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `kafein`
--

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE `product` (
  `product_id` varchar(100) NOT NULL,
  `product_name` varchar(200) NOT NULL,
  `type` varchar(30) NOT NULL,
  `stock` int(11) NOT NULL,
  `price` double NOT NULL,
  `image` varchar(500) NOT NULL,
  `date` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `product`
--

INSERT INTO `product` (`product_id`, `product_name`, `type`, `stock`, `price`, `image`, `date`) VALUES
('1000', '1000', 'Baso', 1000, 1000, 'C:\\\\Users\\\\0xwighozali\\\\Pictures\\\\Screenshots\\\\Screenshot (66).png', '2023-12-28'),
('22', 'Sayut', 'Baso', 164, 10000, 'C:\\\\Users\\\\0xwighozali\\\\Pictures\\\\Screenshots\\\\24.png', '2023-12-26'),
('8787', 'yuuy8', 'Mie', 42, 76, '', '2023-12-24'),
('AA11', 'Buku', 'Baso', 10, 20022, 'C:\\Users\\0xwighozali\\Pictures\\Screenshots\\Screenshot 2023-10-26 091922.png', '2023-12-21'),
('AA121', 'LOOuu', 'Mie', 0, 33, 'C:\\\\Users\\\\0xwighozali\\\\Pictures\\\\Screenshots\\\\Screenshot 2023-10-26 091512.png', '2023-12-24'),
('AA1222', 'susu', 'Mie', 82, 50, 'C:\\\\Users\\\\0xwighozali\\\\Pictures\\\\Screenshots\\\\Screenshot 2023-10-26 091932.png', '2023-12-24'),
('BANDUL', 'ZUU', 'Baso', 0, 50, 'C:\\\\\\\\Users\\\\\\\\0xwighozali\\\\\\\\Pictures\\\\\\\\Screenshots\\\\\\\\Screenshot 2023-10-26 091932.png', '2023-12-27');

-- --------------------------------------------------------

--
-- Table structure for table `receipt`
--

CREATE TABLE `receipt` (
  `id` int(11) NOT NULL,
  `customer_name` varchar(11) NOT NULL,
  `total` double NOT NULL,
  `date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `receipt`
--

INSERT INTO `receipt` (`id`, `customer_name`, `total`, `date`) VALUES
(1, '1', 363766, '2023-12-26'),
(2, 'Al', 30000, '2023-12-27'),
(3, '', 30000, '2023-12-27'),
(4, '', 30000, '2023-12-27'),
(5, 'bbwk', 30000, '2023-12-27'),
(6, '100', 30000, '2023-12-27'),
(7, 'Syau', 20022, '2023-12-27'),
(8, 'Sumbul', 100, '2023-12-27'),
(9, 'po', 20000, '2023-12-28');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id_user` int(15) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id_user`, `username`, `password`) VALUES
(1, '1', '1');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`product_id`);

--
-- Indexes for table `receipt`
--
ALTER TABLE `receipt`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id_user`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `receipt`
--
ALTER TABLE `receipt`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id_user` int(15) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
