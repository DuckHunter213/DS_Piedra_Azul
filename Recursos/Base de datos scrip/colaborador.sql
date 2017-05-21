-- phpMyAdmin SQL Dump
-- version 4.4.14
-- http://www.phpmyadmin.net
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 01-05-2017 a las 23:18:06
-- Versión del servidor: 5.6.26
-- Versión de PHP: 5.6.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `alessandra`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `colaborador`
--

CREATE TABLE IF NOT EXISTS `colaborador` (
  `titulo` varchar(50) DEFAULT NULL,
  `matriculaColaborador` varchar(50) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `apellidoPaterno` varchar(50) NOT NULL,
  `apellidoMaterno` varchar(50) NOT NULL,
  `calle` varchar(50) DEFAULT NULL,
  `fechaRegistro` date NOT NULL,
  `fechaPago` date NOT NULL,
  `colonia` varchar(50) DEFAULT NULL,
  `telefono` varchar(50) DEFAULT NULL,
  `estado` tinyint(1) DEFAULT NULL,
  `fechaNacimiento` date DEFAULT NULL,
  `numero` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `colaborador`
--

INSERT INTO `colaborador` (`titulo`, `matriculaColaborador`, `nombre`, `apellidoPaterno`, `apellidoMaterno`, `calle`, `fechaRegistro`, `fechaPago`, `colonia`, `telefono`, `estado`, `fechaNacimiento`, `numero`) VALUES
('Ingeniero en Danzas Indanzables', '0170329211527', 'Luis Fernando', 'Gomez', 'Alejandre', 'Melchor Musguit', '2017-03-01', '2017-03-01', 'Revolucion', '2282229579', 1, '2017-03-29', '7'),
('Ingeniero en Danzas Indanzables', '0170329211736', 'Luis Fernando', 'Gomez', 'Alejandre', 'Melchor Musguit', '0000-00-00', '0000-00-00', 'Revolucion', '2282229579', 1, '2017-03-29', '7'),
('Maestra en Danzas Danzables', '0170330171116', 'Alma Rosa', 'Vicencio', 'Alejandre', 'Unidad Buena Vista', '0000-00-00', '0000-00-00', 'Buena Vista', '2281125347', 1, '2017-03-30', '6F'),
('Ingeniero en Danzas Indanzables', '0170330171609', 'Luis Fernando', 'Gomez', 'Alejandre', 'Melchor Musguit', '0000-00-00', '0000-00-00', 'Revolucion', '2282229579', 1, '2017-03-30', '7');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `colaborador`
--
ALTER TABLE `colaborador`
  ADD PRIMARY KEY (`matriculaColaborador`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
