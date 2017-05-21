-- phpMyAdmin SQL Dump
-- version 4.4.14
-- http://www.phpmyadmin.net
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 14-04-2017 a las 06:10:55
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
-- Estructura de tabla para la tabla `alumno`
--

CREATE TABLE IF NOT EXISTS `alumno` (
  `nombre` varchar(50) DEFAULT NULL,
  `apellidoPaterno` varchar(50) DEFAULT NULL,
  `nombreTutor` varchar(50) DEFAULT NULL,
  `apellidoMaterno` varchar(50) DEFAULT NULL,
  `telefonoTutor` varchar(50) DEFAULT NULL,
  `fechaNacimiento` date DEFAULT NULL,
  `calle` varchar(50) DEFAULT NULL,
  `numero` varchar(50) DEFAULT NULL,
  `colonia` varchar(50) DEFAULT NULL,
  `correo` varchar(50) DEFAULT NULL,
  `telefono` varchar(50) DEFAULT NULL,
  `estado` tinyint(1) DEFAULT NULL,
  `matriculaAlumno` varchar(50) NOT NULL,
  `fechaInscripcion` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `alumno`
--

INSERT INTO `alumno` (`nombre`, `apellidoPaterno`, `nombreTutor`, `apellidoMaterno`, `telefonoTutor`, `fechaNacimiento`, `calle`, `numero`, `colonia`, `correo`, `telefono`, `estado`, `matriculaAlumno`, `fechaInscripcion`) VALUES
('Luis Fernando', 'Amores', 'Alma Rosa Alejandre Vicencio', 'Alejandre', '2281114099', '1995-10-23', 'Melchor Musguit', '7', 'Revolucion', 'gomez.alejandre@gmail.com', '2282229579', 1, '0170329211527', NULL),
('Iris', 'Gomez', 'Alma Rosa Alejandre Vicencio', 'Lopez', '2281114099', '1995-10-23', 'Melchor Musguit', '7', 'Revolucion', 'gomez.alejandre@gmail.com', '2282229579', 1, '0170329211736', NULL),
('Luis Fernando', 'Gomez', 'Alma Rosa Alejandre Vicencio', 'Alejandre', '2281114099', '1995-11-23', 'Melchor Musguit', '7', 'Revolucion', 'gomez.alejandre@gmail.com', '2282229579', 1, '0170329211817', NULL),
('Victor Manuel', 'Gomez', 'Alma Rosa Alejandre Vicencio', 'Alejandre', '2281114099', '1995-10-23', 'Melchor Musguit', '7', 'Revolucion', 'gomez.alejandre@gmail.com', '2282229579', 1, '0170330011555', NULL),
('Victor Manuel', 'Gomez', 'Alma Rosa Alejandre Vicencio', 'Alejandre', '2281114099', '1995-09-20', 'Melchor Musguit', '7', 'Revolucion', 'gomez.alejandre@gmail.com', '2282229579', 1, '0170330011948', NULL),
('Juan', 'Gomez', 'Alma Rosa Alejandre Vicencio', 'Alejandre', '2281114099', '1995-10-23', 'Melchor Musguit', '7', 'Revolucion', 'gomez.alejandre@gmail.com', '2282229579', 1, '0170330014620', NULL),
('Juan', 'Gomez', 'Alma Rosa Alejandre Vicencio', 'Alejandre', '2281114099', '1995-09-20', 'Melchor Musguit', '7', 'Revolucion', 'gomez.alejandre@gmail.com', '2282229579', 1, '0170330014759', NULL),
('Victor Manuel', 'Gomez', 'Alma Rosa Alejandre Vicencio', 'Alejandre', '2281114099', '1995-09-20', 'Melchor Musguit', '7', 'Revolucion', 'gomez.alejandre@gmail.com', '2282229579', 1, '0170330015608', NULL),
('Victor Manuel', 'Gomez', 'Alma Rosa Alejandre Vicencio', 'Alejandre', '2281114099', '1995-07-21', 'Melchor Musguit', '7', 'Revolucion', 'gomez.alejandre@gmail.com', '2282229579', 1, '0170330015622', NULL),
('Luis Fernando', 'Gomez', 'Alma Rosa Alejandre Vicencio', 'Alejandre', '2281114099', '1995-11-23', 'Melchor Musguit', '7', 'Revolucion', 'gomez.alejandre@gmail.com', '2282229579', 1, '0170330171609', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `asistencia`
--

CREATE TABLE IF NOT EXISTS `asistencia` (
  `folioAsistencia` int(11) NOT NULL,
  `folioListaGrupo` int(11) DEFAULT NULL,
  `fecha` date DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `asistencia`
--

INSERT INTO `asistencia` (`folioAsistencia`, `folioListaGrupo`, `fecha`) VALUES
(1, NULL, '2017-03-16'),
(3, NULL, '2017-03-16'),
(4, NULL, '2017-03-16'),
(5, NULL, '2017-03-19'),
(6, NULL, '2017-03-19'),
(7, NULL, '2017-03-19'),
(8, NULL, '2017-03-19'),
(9, NULL, '2017-03-19'),
(10, NULL, '2017-03-19'),
(11, NULL, '2017-03-19'),
(12, NULL, '2017-03-19'),
(13, NULL, '2017-03-19'),
(14, NULL, '2017-03-19'),
(15, NULL, '2017-03-30'),
(16, NULL, '2017-03-30'),
(17, NULL, '2017-03-30');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `capital`
--

CREATE TABLE IF NOT EXISTS `capital` (
  `monto` varchar(50) DEFAULT NULL,
  `motivo` varchar(50) DEFAULT NULL,
  `fechaCreacion` date DEFAULT NULL,
  `tipo` char(1) DEFAULT NULL,
  `folioCapital` int(11) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `capital`
--

INSERT INTO `capital` (`monto`, `motivo`, `fechaCreacion`, `tipo`, `folioCapital`) VALUES
('100', '2 Sillas', '2017-03-29', 'S', 26),
('100', '2 Sillas', '2017-03-29', 'S', 27),
('100', '2 Sillas', '2017-03-29', 'S', 28),
('100', '2 Sillas', '2017-03-29', 'S', 29),
('100', '2 Sillas', '2017-03-29', 'S', 30),
('100', '2 Sillas', '2017-03-29', 'S', 31),
('100', '2 Sillas', '2017-03-29', 'S', 32),
('100', '2 Sillas', '2017-03-29', 'S', 33),
('100', '2 Sillas', '2017-03-30', 'S', 34);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `colaborador`
--

CREATE TABLE IF NOT EXISTS `colaborador` (
  `titulo` varchar(50) DEFAULT NULL,
  `matriculaColaborador` varchar(50) NOT NULL,
  `nombre` varchar(50) DEFAULT NULL,
  `apellidoPaterno` varchar(50) DEFAULT NULL,
  `apellidoMaterno` varchar(50) DEFAULT NULL,
  `calle` varchar(50) DEFAULT NULL,
  `fechaRegistro` varchar(50) DEFAULT NULL,
  `colonia` varchar(50) DEFAULT NULL,
  `telefono` varchar(50) DEFAULT NULL,
  `estado` tinyint(1) DEFAULT NULL,
  `fechaNacimiento` date DEFAULT NULL,
  `numero` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `colaborador`
--

INSERT INTO `colaborador` (`titulo`, `matriculaColaborador`, `nombre`, `apellidoPaterno`, `apellidoMaterno`, `calle`, `fechaRegistro`, `colonia`, `telefono`, `estado`, `fechaNacimiento`, `numero`) VALUES
('Ingeniero en Danzas Indanzables', '0170329211527', 'Luis Fernando', 'Gomez', 'Alejandre', 'Melchor Musguit', NULL, 'Revolucion', '2282229579', 1, '2017-03-29', '7'),
('Ingeniero en Danzas Indanzables', '0170329211736', 'Luis Fernando', 'Gomez', 'Alejandre', 'Melchor Musguit', NULL, 'Revolucion', '2282229579', 1, '2017-03-29', '7'),
('Maestra en Danzas Danzables', '0170330171116', 'Alma Rosa', 'Vicencio', 'Alejandre', 'Unidad Buena Vista', NULL, 'Buena Vista', '2281125347', 1, '2017-03-30', '6F'),
('Ingeniero en Danzas Indanzables', '0170330171609', 'Luis Fernando', 'Gomez', 'Alejandre', 'Melchor Musguit', NULL, 'Revolucion', '2282229579', 1, '2017-03-30', '7');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `grupo`
--

CREATE TABLE IF NOT EXISTS `grupo` (
  `nombre` varchar(50) DEFAULT NULL,
  `estado` tinyint(1) DEFAULT NULL,
  `precio` varchar(50) DEFAULT NULL,
  `matriculaGrupo` varchar(50) NOT NULL DEFAULT '',
  `matriculaColaborador` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `grupo`
--

INSERT INTO `grupo` (`nombre`, `estado`, `precio`, `matriculaGrupo`, `matriculaColaborador`) VALUES
('Danza Arabe Alluh Balbar', 1, '1000', 'G0170329211528', '0170329211527'),
('Danza Arabe Alluh Balbar', 1, '1000', 'G0170329211736', '0170329211527'),
('Danza Arabe Alluh Balbar', 1, '1000', 'G0170330171610', '0170329211527');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `horario`
--

CREATE TABLE IF NOT EXISTS `horario` (
  `dia` varchar(50) DEFAULT NULL,
  `horaInicio` varchar(50) DEFAULT NULL,
  `horaFin` varchar(50) DEFAULT NULL,
  `salon` varchar(50) DEFAULT NULL,
  `folioHorario` int(11) NOT NULL,
  `matriculaGrupo` varchar(50) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=661 DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `horario`
--

INSERT INTO `horario` (`dia`, `horaInicio`, `horaFin`, `salon`, `folioHorario`, `matriculaGrupo`) VALUES
('Lunes', '13:00', '14:00', '104', 340, 'G0170330171610'),
('Lunes', '13:00', '15:00', '104', 656, 'G0170329211736'),
('Martes', '08:00', '09:15', '123', 657, 'G0170329211736'),
('Jueves', '08:00', '09:15', '15', 658, 'G0170329211736'),
('Viernes', '08:00', '10:00', '2', 659, 'G0170329211736'),
('Sábado', '08:00', '09:45', '3', 660, 'G0170329211736');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `listagrupo`
--

CREATE TABLE IF NOT EXISTS `listagrupo` (
  `folioListaGrupo` int(11) NOT NULL,
  `matriculaGrupo` varchar(50) DEFAULT NULL,
  `matriculaAlumno` varchar(50) DEFAULT NULL,
  `estado` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=893 DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `listagrupo`
--

INSERT INTO `listagrupo` (`folioListaGrupo`, `matriculaGrupo`, `matriculaAlumno`, `estado`) VALUES
(887, 'G0170329211736', '0170329211527', 1),
(888, 'G0170329211736', '0170329211736', 0),
(889, 'G0170329211736', '0170329211817', 0),
(890, 'G0170329211736', '0170330011555', 0),
(891, 'G0170329211736', '0170330011948', 0),
(892, 'G0170329211736', '0170330171609', 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `promociones`
--

CREATE TABLE IF NOT EXISTS `promociones` (
  `folioPromocion` int(11) NOT NULL,
  `nombre` varchar(50) DEFAULT NULL,
  `tipo` tinyint(1) DEFAULT NULL,
  `porcentajeDescuento` varchar(50) DEFAULT NULL,
  `cantidadCupones` varchar(50) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `promociones`
--

INSERT INTO `promociones` (`folioPromocion`, `nombre`, `tipo`, `porcentajeDescuento`, `cantidadCupones`) VALUES
(15, 'Primeros 4400', 1, '100', '4400'),
(16, 'Primeros 4400', 1, '100', '4400'),
(17, 'Primeros 4400', 1, '100', '4400');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `alumno`
--
ALTER TABLE `alumno`
  ADD PRIMARY KEY (`matriculaAlumno`);

--
-- Indices de la tabla `asistencia`
--
ALTER TABLE `asistencia`
  ADD PRIMARY KEY (`folioAsistencia`),
  ADD KEY `folioListaGrupo` (`folioListaGrupo`);

--
-- Indices de la tabla `capital`
--
ALTER TABLE `capital`
  ADD PRIMARY KEY (`folioCapital`);

--
-- Indices de la tabla `colaborador`
--
ALTER TABLE `colaborador`
  ADD PRIMARY KEY (`matriculaColaborador`);

--
-- Indices de la tabla `grupo`
--
ALTER TABLE `grupo`
  ADD PRIMARY KEY (`matriculaGrupo`),
  ADD KEY `matriculaColaborador` (`matriculaColaborador`);

--
-- Indices de la tabla `horario`
--
ALTER TABLE `horario`
  ADD PRIMARY KEY (`folioHorario`),
  ADD KEY `horario_ibfk_1` (`matriculaGrupo`);

--
-- Indices de la tabla `listagrupo`
--
ALTER TABLE `listagrupo`
  ADD PRIMARY KEY (`folioListaGrupo`),
  ADD KEY `matriculaGrupo` (`matriculaGrupo`),
  ADD KEY `matriculaAlumno` (`matriculaAlumno`);

--
-- Indices de la tabla `promociones`
--
ALTER TABLE `promociones`
  ADD PRIMARY KEY (`folioPromocion`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `asistencia`
--
ALTER TABLE `asistencia`
  MODIFY `folioAsistencia` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=18;
--
-- AUTO_INCREMENT de la tabla `capital`
--
ALTER TABLE `capital`
  MODIFY `folioCapital` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=35;
--
-- AUTO_INCREMENT de la tabla `horario`
--
ALTER TABLE `horario`
  MODIFY `folioHorario` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=661;
--
-- AUTO_INCREMENT de la tabla `listagrupo`
--
ALTER TABLE `listagrupo`
  MODIFY `folioListaGrupo` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=893;
--
-- AUTO_INCREMENT de la tabla `promociones`
--
ALTER TABLE `promociones`
  MODIFY `folioPromocion` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=18;
--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `asistencia`
--
ALTER TABLE `asistencia`
  ADD CONSTRAINT `asistencia_ibfk_1` FOREIGN KEY (`folioListaGrupo`) REFERENCES `listagrupo` (`folioListaGrupo`);

--
-- Filtros para la tabla `grupo`
--
ALTER TABLE `grupo`
  ADD CONSTRAINT `grupo_ibfk_1` FOREIGN KEY (`matriculaColaborador`) REFERENCES `colaborador` (`matriculaColaborador`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `horario`
--
ALTER TABLE `horario`
  ADD CONSTRAINT `horario_ibfk_1` FOREIGN KEY (`matriculaGrupo`) REFERENCES `grupo` (`matriculaGrupo`);

--
-- Filtros para la tabla `listagrupo`
--
ALTER TABLE `listagrupo`
  ADD CONSTRAINT `listagrupo_ibfk_2` FOREIGN KEY (`matriculaGrupo`) REFERENCES `grupo` (`matriculaGrupo`),
  ADD CONSTRAINT `listagrupo_ibfk_3` FOREIGN KEY (`matriculaAlumno`) REFERENCES `alumno` (`matriculaAlumno`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
