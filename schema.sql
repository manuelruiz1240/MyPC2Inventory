-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 13-01-2022 a las 22:24:39
-- Versión del servidor: 10.4.17-MariaDB
-- Versión de PHP: 8.0.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `mypc2inventory`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `mpi_pc`
--

CREATE TABLE `mpi_pc` (
  `id` int(11) NOT NULL,
  `host_name` varchar(200) DEFAULT NULL,
  `session_username` varchar(100) DEFAULT NULL,
  `ip_address` varchar(50) DEFAULT NULL,
  `mac_address` varchar(50) DEFAULT NULL,
  `os_name` varchar(100) DEFAULT NULL,
  `os_type` varchar(50) DEFAULT NULL,
  `cpu_name` varchar(200) DEFAULT NULL,
  `gpu_name_1` varchar(200) DEFAULT NULL,
  `gpu_name_2` varchar(200) DEFAULT NULL,
  `storage_1` varchar(50) DEFAULT NULL,
  `storage_2` varchar(50) DEFAULT NULL,
  `storage_3` varchar(50) DEFAULT NULL,
  `ram_total` varchar(50) DEFAULT NULL,
  `ram_avaliable` varchar(50) DEFAULT NULL,
  `free_swap_size` varchar(50) NOT NULL,
  `bios_manufacturer` varchar(200) DEFAULT NULL,
  `bios_version` varchar(200) DEFAULT NULL,
  `bios_date` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `mpi_pc`
--

INSERT INTO `mpi_pc` (`id`, `host_name`, `session_username`, `ip_address`, `mac_address`, `os_name`, `os_type`, `cpu_name`, `gpu_name_1`, `gpu_name_2`, `storage_1`, `storage_2`, `storage_3`, `ram_total`, `ram_avaliable`, `free_swap_size`, `bios_manufacturer`, `bios_version`, `bios_date`) VALUES
(1, 'sistemas3', 'sistemas3', '192.168.56.1', '0A-00-27-00-00-0C', 'Windows 10', 'amd64', 'Intel(R) Core(TM) i5-9500 CPU @ 3.00GHz (6 cores)', 'Intel(R) UHD Graphics 630', 'null', '929', '0', '0', '7', '1', '1', 'Dell Inc.', 'DELL   - 1072009', '0000-00-00');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `mpi_software`
--

CREATE TABLE `mpi_software` (
  `id` int(11) NOT NULL,
  `name` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `mpi_pc`
--
ALTER TABLE `mpi_pc`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `mpi_software`
--
ALTER TABLE `mpi_software`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `mpi_pc`
--
ALTER TABLE `mpi_pc`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `mpi_software`
--
ALTER TABLE `mpi_software`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
