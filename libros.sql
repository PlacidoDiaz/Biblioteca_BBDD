-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 17-12-2022 a las 17:57:50
-- Versión del servidor: 10.4.21-MariaDB
-- Versión de PHP: 7.3.31

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `biblioteca`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `libros`
--

CREATE TABLE `libros` (
  `idlibros` int(11) NOT NULL,
  `titulo` varchar(45) NOT NULL,
  `autor` varchar(45) NOT NULL,
  `editorial` varchar(45) NOT NULL,
  `prestado` tinyint(1) NOT NULL,
  `fechaPrestamo` date DEFAULT NULL,
  `fechaDevolucion` date DEFAULT NULL,
  `isbn` varchar(45) NOT NULL,
  `fechaAlta` timestamp NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `libros`
--

INSERT INTO `libros` (`idlibros`, `titulo`, `autor`, `editorial`, `prestado`, `fechaPrestamo`, `fechaDevolucion`, `isbn`, `fechaAlta`) VALUES
(2, 'Las Llanuras del Transito', 'Auel,Jean M.', 'Maeva', 1, '2017-03-30', '2017-03-19', '978-84-95354-15-0', '2017-03-13 22:00:00'),
(13, 'El maestro de esgrima', 'Perez Reverte;Arturo', 'Bibliotex', 1, '2017-02-01', '2017-02-06', '978-84-8130-252-3', '2017-03-13 22:00:00'),
(14, 'La tabla de flandez', 'Perez Reverte;Arturo', 'DeBolsillo', 0, '2017-03-05', '2017-03-10', '978-84-8450-382-8', '2017-03-13 22:00:00'),
(15, 'El clan del osos cavernario', 'Auel,Jean M.', 'Maeva', 1, '2017-03-10', '2017-03-10', '978-84-96231-63-4', '2017-03-13 22:00:00'),
(22, 'La Catedral del Mar', 'Idelfonso Falcone', 'Umbriel', 1, '2017-03-18', '2017-03-18', '978-84-95618-71-9', '2017-03-13 22:00:00'),
(23, 'Milena o el femur..', 'PeZepeda;Jorge', 'Planeta', 0, '2017-03-20', '2017-03-20', '978-84-08-13405-3', '2017-03-13 22:00:00'),
(43, 'El abcd del MSDos 5', 'Garcia;Francisco Javier', 'Rama', 1, '2018-04-12', '2018-04-17', '978-84-7897-061-2', '2018-04-12 04:06:00');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `libros`
--
ALTER TABLE `libros`
  ADD PRIMARY KEY (`idlibros`),
  ADD UNIQUE KEY `isbn_UNIQUE` (`isbn`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `libros`
--
ALTER TABLE `libros`
  MODIFY `idlibros` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=45;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
