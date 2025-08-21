-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Aug 18, 2025 at 11:56 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `pixelshop`
--

-- --------------------------------------------------------

--
-- Table structure for table `cart`
--

CREATE TABLE `cart` (
  `id_cart` int(11) NOT NULL,
  `id_produk` int(11) DEFAULT NULL,
  `id_customer` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `kategori`
--

CREATE TABLE `kategori` (
  `id_kategori` int(11) NOT NULL,
  `nama` varchar(50) DEFAULT NULL,
  `deskripsi` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `kategori`
--

INSERT INTO `kategori` (`id_kategori`, `nama`, `deskripsi`) VALUES
(1, 'ACC', 'aksesoris'),
(2, 'FOOD', 'nom'),
(5, 'TECH', 'Electronik');

-- --------------------------------------------------------

--
-- Table structure for table `nota`
--

CREATE TABLE `nota` (
  `id_nota` int(11) NOT NULL,
  `order_time` datetime DEFAULT NULL,
  `receive_time` datetime DEFAULT NULL,
  `status` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `nota`
--

INSERT INTO `nota` (`id_nota`, `order_time`, `receive_time`, `status`) VALUES
(1, '2025-07-24 12:04:53', NULL, 1),
(2, '2025-07-24 13:19:14', NULL, 1),
(3, '2025-07-24 13:19:57', NULL, 1),
(4, '2025-07-24 13:20:24', NULL, 1),
(5, '2025-07-24 13:24:40', NULL, 1),
(6, '2025-07-24 13:31:40', NULL, 1),
(7, '2025-07-24 14:19:43', NULL, 1),
(8, '2025-07-29 10:57:03', NULL, 1),
(9, '2025-07-29 11:08:01', NULL, 1),
(10, '2025-07-29 11:10:09', NULL, 1),
(11, '2025-07-29 11:11:13', NULL, 1),
(12, '2025-07-29 11:12:16', NULL, 1),
(13, '2025-08-18 10:11:37', NULL, 2),
(14, '2025-08-18 14:13:29', '2025-08-18 14:58:05', 3);

-- --------------------------------------------------------

--
-- Table structure for table `produk`
--

CREATE TABLE `produk` (
  `id_produk` int(11) NOT NULL,
  `nama` varchar(200) DEFAULT NULL,
  `id_kategori` int(11) DEFAULT NULL,
  `jumlah` int(11) DEFAULT NULL,
  `harga` int(11) DEFAULT NULL,
  `id_supplier` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `produk`
--

INSERT INTO `produk` (`id_produk`, `nama`, `id_kategori`, `jumlah`, `harga`, `id_supplier`) VALUES
(1, 'Suspicious Charm', 1, 5, 200000, 1),
(3, 'another one', 1, 6, 23000, 1),
(10, '[DealMaker]', 5, 1, 999999, 2),
(12, 'IT\'S TV TIME', 5, 1, 9999, 13);

-- --------------------------------------------------------

--
-- Table structure for table `transaksi`
--

CREATE TABLE `transaksi` (
  `id_transaksi` int(11) NOT NULL,
  `id_customer` int(11) DEFAULT NULL,
  `id_produk` int(11) DEFAULT NULL,
  `total` int(11) DEFAULT NULL,
  `id_supplier` int(11) DEFAULT NULL,
  `id_nota` int(11) DEFAULT NULL,
  `status` tinyint(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `transaksi`
--

INSERT INTO `transaksi` (`id_transaksi`, `id_customer`, `id_produk`, `total`, `id_supplier`, `id_nota`, `status`) VALUES
(1, 3, 1, 200000, 1, 1, 0),
(2, 3, 2, 100000, 1, 1, 0),
(3, 3, 3, 23000, 1, 1, 0),
(4, 3, 2, 100000, 1, 1, 0),
(5, 3, 3, 23000, 1, 2, 0),
(6, 3, 1, 200000, 1, 5, 0),
(7, 3, 1, 200000, 1, 6, 0),
(8, 3, 2, 100000, 1, 6, 0),
(9, 0, 1, 200000, 1, 7, 0),
(10, 0, 2, 100000, 1, 7, 0),
(11, 3, 1, 200000, 1, 8, 0),
(12, 3, 3, 23000, 1, 9, 0),
(13, 3, 2, 100000, 1, 10, 0),
(14, 3, 3, 23000, 1, 10, 0),
(15, 3, 3, 23000, 1, 11, 0),
(16, 3, 3, 23000, 1, 12, 0),
(17, 3, 12, 9999, 13, 13, 0),
(19, 15, 12, 9999, 13, 14, 1),
(20, 15, 12, 9999, 13, 14, 1);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id_user` int(11) NOT NULL,
  `nama` varchar(60) NOT NULL,
  `username` varchar(60) NOT NULL,
  `password` varchar(60) NOT NULL,
  `level` enum('1','2','3') NOT NULL,
  `notelp` varchar(255) DEFAULT NULL,
  `alamat1` text DEFAULT NULL,
  `alamat2` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id_user`, `nama`, `username`, `password`, `level`, `notelp`, `alamat1`, `alamat2`) VALUES
(1, 'Kevin Kaslana', 'ZeroDegree', '0', '2', '', '', ''),
(2, 'Ryukusune', 'Ryuku', '17514', '1', NULL, NULL, NULL),
(3, 'Kurumi', 'Kurumi', 'Kurumi', '3', NULL, NULL, NULL),
(5, 'test', 'test', 'test', '3', 'test', 'test', NULL),
(9, 'test', 'test', 'test', '3', '085668499103', 'test', NULL),
(10, 'Logitech', 'E', 'E', '3', '089773864722', 'E', NULL),
(11, 'Chichi', 'chichi', 'chichi', '3', '085668499103', 'Baloi', NULL),
(12, 'Nagisa', 'Nagisa', '0', '3', '12313123123123', '123', NULL),
(13, 'Kris', 'KRIS', '1225', '2', '087656736482', 'HomeTown', ''),
(15, 'Yae Sakura', 'Miko', '123', '3', '123123123123', 'test', NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `cart`
--
ALTER TABLE `cart`
  ADD PRIMARY KEY (`id_cart`);

--
-- Indexes for table `kategori`
--
ALTER TABLE `kategori`
  ADD PRIMARY KEY (`id_kategori`);

--
-- Indexes for table `nota`
--
ALTER TABLE `nota`
  ADD PRIMARY KEY (`id_nota`);

--
-- Indexes for table `produk`
--
ALTER TABLE `produk`
  ADD PRIMARY KEY (`id_produk`);

--
-- Indexes for table `transaksi`
--
ALTER TABLE `transaksi`
  ADD PRIMARY KEY (`id_transaksi`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id_user`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `cart`
--
ALTER TABLE `cart`
  MODIFY `id_cart` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT for table `kategori`
--
ALTER TABLE `kategori`
  MODIFY `id_kategori` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `nota`
--
ALTER TABLE `nota`
  MODIFY `id_nota` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT for table `produk`
--
ALTER TABLE `produk`
  MODIFY `id_produk` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT for table `transaksi`
--
ALTER TABLE `transaksi`
  MODIFY `id_transaksi` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id_user` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
