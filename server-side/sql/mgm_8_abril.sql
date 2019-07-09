-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1:3306
-- Tiempo de generación: 08-04-2019 a las 16:05:44
-- Versión del servidor: 10.2.17-MariaDB
-- Versión de PHP: 7.2.13

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `u281511508_mgm`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `almacen`
--

CREATE TABLE `almacen` (
  `idAlmacen` int(11) NOT NULL,
  `cantidad` double DEFAULT NULL,
  `fecha` datetime DEFAULT NULL,
  `autorizado` tinyint(4) DEFAULT NULL,
  `idProducto` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `caja`
--

CREATE TABLE `caja` (
  `idCaja` int(11) NOT NULL,
  `descripcion` varchar(400) COLLATE utf8_unicode_ci DEFAULT NULL,
  `monto` double DEFAULT NULL,
  `abierto` tinyint(4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cajaCorte`
--

CREATE TABLE `cajaCorte` (
  `idCajaCorte` int(11) NOT NULL,
  `idCaja` int(11) DEFAULT NULL,
  `idPersonal` int(11) DEFAULT NULL,
  `tipo` int(11) DEFAULT NULL,
  `monto` double DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `hora` time DEFAULT NULL,
  `idPersonalAut` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `citas`
--

CREATE TABLE `citas` (
  `idCita` int(11) NOT NULL,
  `idPaciente` int(11) DEFAULT NULL,
  `fecha` datetime DEFAULT NULL,
  `efectuada` tinyint(4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `clinicas`
--

CREATE TABLE `clinicas` (
  `idClinica` int(11) NOT NULL,
  `nombre` text COLLATE utf8_unicode_ci DEFAULT NULL,
  `calle` text COLLATE utf8_unicode_ci DEFAULT NULL,
  `colonia` text COLLATE utf8_unicode_ci DEFAULT NULL,
  `cp` tinytext COLLATE utf8_unicode_ci DEFAULT NULL,
  `telefono` tinytext COLLATE utf8_unicode_ci DEFAULT NULL,
  `estado` text COLLATE utf8_unicode_ci DEFAULT NULL,
  `pais` text COLLATE utf8_unicode_ci DEFAULT NULL,
  `titulo` text COLLATE utf8_unicode_ci NOT NULL,
  `descripcion` text COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cobrosPendientes`
--

CREATE TABLE `cobrosPendientes` (
  `idCobroPendiente` int(11) NOT NULL,
  `cantidad` double DEFAULT NULL,
  `fecha` datetime DEFAULT NULL,
  `idTratamientoRecetado` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `costoTratamientos`
--

CREATE TABLE `costoTratamientos` (
  `idCostoTratamiento` int(11) NOT NULL,
  `costo` double DEFAULT NULL,
  `fecha` datetime DEFAULT NULL,
  `idTratamiento` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detallesTratamientos`
--

CREATE TABLE `detallesTratamientos` (
  `idDetalleTratamiento` int(11) NOT NULL,
  `idTratamiento` int(11) NOT NULL,
  `idProducto` int(11) NOT NULL,
  `cantidad` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `fotos`
--

CREATE TABLE `fotos` (
  `idFoto` int(11) NOT NULL,
  `archivo` text COLLATE utf8_unicode_ci DEFAULT NULL,
  `idHistorial` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `historial`
--

CREATE TABLE `historial` (
  `idHistorial` int(11) NOT NULL,
  `fecha` datetime DEFAULT NULL,
  `descripcion` text COLLATE utf8_unicode_ci DEFAULT NULL,
  `idPaciente` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `horarios`
--

CREATE TABLE `horarios` (
  `idHorarios` int(11) NOT NULL,
  `lunes` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `martes` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `miercoles` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `jueves` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `viernes` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `sabado` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `domingo` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `toleranciaEntrada` int(11) DEFAULT NULL,
  `toleranciaSalida` int(11) DEFAULT NULL,
  `idPersonal` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pacientes`
--

CREATE TABLE `pacientes` (
  `idPaciente` int(11) NOT NULL,
  `nombre` text COLLATE utf8_unicode_ci DEFAULT NULL,
  `apellidos` text COLLATE utf8_unicode_ci DEFAULT NULL,
  `email` text COLLATE utf8_unicode_ci DEFAULT NULL,
  `telefono` text COLLATE utf8_unicode_ci DEFAULT NULL,
  `movil` text COLLATE utf8_unicode_ci DEFAULT NULL,
  `clave` text COLLATE utf8_unicode_ci DEFAULT NULL,
  `idClinica` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pagosTratamientos`
--

CREATE TABLE `pagosTratamientos` (
  `idPagoTratamiento` int(11) NOT NULL,
  `cantidad` double DEFAULT NULL,
  `fecha` datetime DEFAULT NULL,
  `idTratamientoRecetado` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `personal`
--

CREATE TABLE `personal` (
  `idPersonal` int(11) NOT NULL,
  `nombre` text COLLATE utf8_unicode_ci DEFAULT NULL,
  `apellidos` text COLLATE utf8_unicode_ci DEFAULT NULL,
  `email` text COLLATE utf8_unicode_ci DEFAULT NULL,
  `telefono` tinytext COLLATE utf8_unicode_ci DEFAULT NULL,
  `movil` tinytext COLLATE utf8_unicode_ci DEFAULT NULL,
  `usuario` tinytext COLLATE utf8_unicode_ci DEFAULT NULL,
  `clave` tinytext COLLATE utf8_unicode_ci DEFAULT NULL,
  `codigo` text COLLATE utf8_unicode_ci NOT NULL,
  `idClinica` int(11) NOT NULL,
  `tipo` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `precioProductos`
--

CREATE TABLE `precioProductos` (
  `idPrecioProducto` int(11) NOT NULL,
  `precio` double DEFAULT NULL,
  `fecha` datetime DEFAULT NULL,
  `idProducto` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `productos`
--

CREATE TABLE `productos` (
  `idProducto` int(11) NOT NULL,
  `clave` text COLLATE utf8_unicode_ci DEFAULT NULL,
  `nombre` text COLLATE utf8_unicode_ci DEFAULT NULL,
  `descripcion` text COLLATE utf8_unicode_ci DEFAULT NULL,
  `cantidadMinima` int(11) NOT NULL,
  `barCode` text COLLATE utf8_unicode_ci DEFAULT NULL,
  `idTipoProducto` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `relojChecador`
--

CREATE TABLE `relojChecador` (
  `idRelojChecador` int(11) NOT NULL,
  `idPersonal` int(11) DEFAULT NULL,
  `entrada` datetime DEFAULT NULL,
  `salida` datetime DEFAULT NULL,
  `toleranciaEntrada` int(11) DEFAULT NULL,
  `toleranciaSalida` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `servicios`
--

CREATE TABLE `servicios` (
  `idServicio` int(11) NOT NULL,
  `nombre` text COLLATE utf8_unicode_ci DEFAULT NULL,
  `descripcion` text COLLATE utf8_unicode_ci DEFAULT NULL,
  `costo` double DEFAULT NULL,
  `idClinica` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tiposProductos`
--

CREATE TABLE `tiposProductos` (
  `idTipoProducto` int(11) NOT NULL,
  `clave` text COLLATE utf8_unicode_ci DEFAULT NULL,
  `nombre` text COLLATE utf8_unicode_ci DEFAULT NULL,
  `descripcion` text COLLATE utf8_unicode_ci DEFAULT NULL,
  `idClinica` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tratamientos`
--

CREATE TABLE `tratamientos` (
  `idTratamiento` int(11) NOT NULL,
  `nombre` text COLLATE utf8_unicode_ci DEFAULT NULL,
  `descripcion` text COLLATE utf8_unicode_ci DEFAULT NULL,
  `sesiones` int(11) DEFAULT NULL,
  `caducidad` int(11) DEFAULT NULL,
  `idClinica` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tratamientosRecetados`
--

CREATE TABLE `tratamientosRecetados` (
  `idTratamientoRecetado` int(11) NOT NULL,
  `sesiones` int(11) DEFAULT NULL,
  `sesionesTotales` int(11) DEFAULT NULL,
  `caducidad` datetime DEFAULT NULL,
  `costo` double NOT NULL,
  `idTratamiento` int(11) DEFAULT NULL,
  `idHistorial` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ventasProductos`
--

CREATE TABLE `ventasProductos` (
  `idVentaProductos` int(11) NOT NULL,
  `fecha` datetime DEFAULT NULL,
  `cantidadProductos` int(11) DEFAULT NULL,
  `subtotal` double DEFAULT NULL,
  `iva` double DEFAULT NULL,
  `total` double DEFAULT NULL,
  `idPersonal` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ventasProductosDetalles`
--

CREATE TABLE `ventasProductosDetalles` (
  `idVentaProductosDetalle` int(11) NOT NULL,
  `cantidad` int(11) DEFAULT NULL,
  `costo` double DEFAULT NULL,
  `total` double DEFAULT NULL,
  `idProducto` int(11) DEFAULT NULL,
  `idVentasProductos` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `almacen`
--
ALTER TABLE `almacen`
  ADD PRIMARY KEY (`idAlmacen`),
  ADD KEY `fk_almacen_productos1` (`idProducto`);

--
-- Indices de la tabla `caja`
--
ALTER TABLE `caja`
  ADD PRIMARY KEY (`idCaja`);

--
-- Indices de la tabla `cajaCorte`
--
ALTER TABLE `cajaCorte`
  ADD PRIMARY KEY (`idCajaCorte`),
  ADD KEY `fk_cajaCorte_personal1` (`idPersonal`),
  ADD KEY `fk_cajaCorte_caja1` (`idCaja`),
  ADD KEY `fk_cajaCorte_personal2` (`idPersonalAut`);

--
-- Indices de la tabla `citas`
--
ALTER TABLE `citas`
  ADD PRIMARY KEY (`idCita`),
  ADD KEY `fk_citas_pacientes1` (`idPaciente`);

--
-- Indices de la tabla `clinicas`
--
ALTER TABLE `clinicas`
  ADD PRIMARY KEY (`idClinica`);

--
-- Indices de la tabla `cobrosPendientes`
--
ALTER TABLE `cobrosPendientes`
  ADD PRIMARY KEY (`idCobroPendiente`),
  ADD KEY `fk_cobrosPendientes_tratamientosRecetados1` (`idTratamientoRecetado`);

--
-- Indices de la tabla `costoTratamientos`
--
ALTER TABLE `costoTratamientos`
  ADD PRIMARY KEY (`idCostoTratamiento`),
  ADD KEY `fk_precioTratamientos_tratamientos1` (`idTratamiento`);

--
-- Indices de la tabla `detallesTratamientos`
--
ALTER TABLE `detallesTratamientos`
  ADD PRIMARY KEY (`idDetalleTratamiento`),
  ADD KEY `fk_detallesTratamientos_tratamientos1` (`idTratamiento`),
  ADD KEY `fk_detallesTratamientos_productos1` (`idProducto`);

--
-- Indices de la tabla `fotos`
--
ALTER TABLE `fotos`
  ADD PRIMARY KEY (`idFoto`),
  ADD KEY `fk_fotos_historial1` (`idHistorial`);

--
-- Indices de la tabla `historial`
--
ALTER TABLE `historial`
  ADD PRIMARY KEY (`idHistorial`),
  ADD KEY `fk_historial_pacientes1` (`idPaciente`);

--
-- Indices de la tabla `horarios`
--
ALTER TABLE `horarios`
  ADD PRIMARY KEY (`idHorarios`),
  ADD UNIQUE KEY `idPersonal` (`idPersonal`);

--
-- Indices de la tabla `pacientes`
--
ALTER TABLE `pacientes`
  ADD PRIMARY KEY (`idPaciente`),
  ADD KEY `fk_pacientes_clinicas1` (`idClinica`);

--
-- Indices de la tabla `pagosTratamientos`
--
ALTER TABLE `pagosTratamientos`
  ADD PRIMARY KEY (`idPagoTratamiento`),
  ADD KEY `fk_pagosTratamientos_tratamientosRecetados1` (`idTratamientoRecetado`);

--
-- Indices de la tabla `personal`
--
ALTER TABLE `personal`
  ADD PRIMARY KEY (`idPersonal`),
  ADD KEY `fk_personal_clinicas1` (`idClinica`);

--
-- Indices de la tabla `precioProductos`
--
ALTER TABLE `precioProductos`
  ADD PRIMARY KEY (`idPrecioProducto`),
  ADD KEY `fk_precios_productos1` (`idProducto`);

--
-- Indices de la tabla `productos`
--
ALTER TABLE `productos`
  ADD PRIMARY KEY (`idProducto`),
  ADD KEY `fk_productos_tiposProductos1` (`idTipoProducto`);

--
-- Indices de la tabla `relojChecador`
--
ALTER TABLE `relojChecador`
  ADD PRIMARY KEY (`idRelojChecador`),
  ADD KEY `fk_relojChecador_personal1` (`idPersonal`);

--
-- Indices de la tabla `servicios`
--
ALTER TABLE `servicios`
  ADD PRIMARY KEY (`idServicio`),
  ADD KEY `fk_servicios_clinicas1` (`idClinica`);

--
-- Indices de la tabla `tiposProductos`
--
ALTER TABLE `tiposProductos`
  ADD PRIMARY KEY (`idTipoProducto`),
  ADD KEY `fk_tiposProductos_clinicas1` (`idClinica`);

--
-- Indices de la tabla `tratamientos`
--
ALTER TABLE `tratamientos`
  ADD PRIMARY KEY (`idTratamiento`),
  ADD KEY `fk_tratamientos_clinicas1` (`idClinica`);

--
-- Indices de la tabla `tratamientosRecetados`
--
ALTER TABLE `tratamientosRecetados`
  ADD PRIMARY KEY (`idTratamientoRecetado`),
  ADD KEY `fk_tratamientosRecetados_historial1` (`idHistorial`),
  ADD KEY `fk_tratamientosRecetados_tratamientos1` (`idTratamiento`);

--
-- Indices de la tabla `ventasProductos`
--
ALTER TABLE `ventasProductos`
  ADD PRIMARY KEY (`idVentaProductos`),
  ADD KEY `fk_ventasProductos_personal1` (`idPersonal`);

--
-- Indices de la tabla `ventasProductosDetalles`
--
ALTER TABLE `ventasProductosDetalles`
  ADD PRIMARY KEY (`idVentaProductosDetalle`),
  ADD KEY `fk_ventasProductosDetalles_productos1` (`idProducto`),
  ADD KEY `fk_ventasProductosDetalles_ventasProductos1` (`idVentasProductos`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `almacen`
--
ALTER TABLE `almacen`
  MODIFY `idAlmacen` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `caja`
--
ALTER TABLE `caja`
  MODIFY `idCaja` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `cajaCorte`
--
ALTER TABLE `cajaCorte`
  MODIFY `idCajaCorte` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `citas`
--
ALTER TABLE `citas`
  MODIFY `idCita` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `clinicas`
--
ALTER TABLE `clinicas`
  MODIFY `idClinica` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `cobrosPendientes`
--
ALTER TABLE `cobrosPendientes`
  MODIFY `idCobroPendiente` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `costoTratamientos`
--
ALTER TABLE `costoTratamientos`
  MODIFY `idCostoTratamiento` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `detallesTratamientos`
--
ALTER TABLE `detallesTratamientos`
  MODIFY `idDetalleTratamiento` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `fotos`
--
ALTER TABLE `fotos`
  MODIFY `idFoto` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `historial`
--
ALTER TABLE `historial`
  MODIFY `idHistorial` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `horarios`
--
ALTER TABLE `horarios`
  MODIFY `idHorarios` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `pacientes`
--
ALTER TABLE `pacientes`
  MODIFY `idPaciente` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `pagosTratamientos`
--
ALTER TABLE `pagosTratamientos`
  MODIFY `idPagoTratamiento` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `personal`
--
ALTER TABLE `personal`
  MODIFY `idPersonal` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `precioProductos`
--
ALTER TABLE `precioProductos`
  MODIFY `idPrecioProducto` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `productos`
--
ALTER TABLE `productos`
  MODIFY `idProducto` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `relojChecador`
--
ALTER TABLE `relojChecador`
  MODIFY `idRelojChecador` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `servicios`
--
ALTER TABLE `servicios`
  MODIFY `idServicio` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `tiposProductos`
--
ALTER TABLE `tiposProductos`
  MODIFY `idTipoProducto` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `tratamientos`
--
ALTER TABLE `tratamientos`
  MODIFY `idTratamiento` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `tratamientosRecetados`
--
ALTER TABLE `tratamientosRecetados`
  MODIFY `idTratamientoRecetado` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `ventasProductos`
--
ALTER TABLE `ventasProductos`
  MODIFY `idVentaProductos` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `ventasProductosDetalles`
--
ALTER TABLE `ventasProductosDetalles`
  MODIFY `idVentaProductosDetalle` int(11) NOT NULL AUTO_INCREMENT;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `almacen`
--
ALTER TABLE `almacen`
  ADD CONSTRAINT `fk_almacen_productos1` FOREIGN KEY (`idProducto`) REFERENCES `productos` (`idProducto`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `cajaCorte`
--
ALTER TABLE `cajaCorte`
  ADD CONSTRAINT `fk_cajaCorte_caja1` FOREIGN KEY (`idCaja`) REFERENCES `caja` (`idCaja`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_cajaCorte_personal1` FOREIGN KEY (`idPersonal`) REFERENCES `personal` (`idPersonal`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_cajaCorte_personal2` FOREIGN KEY (`idPersonalAut`) REFERENCES `personal` (`idPersonal`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `citas`
--
ALTER TABLE `citas`
  ADD CONSTRAINT `fk_citas_pacientes1` FOREIGN KEY (`idPaciente`) REFERENCES `pacientes` (`idPaciente`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `cobrosPendientes`
--
ALTER TABLE `cobrosPendientes`
  ADD CONSTRAINT `fk_cobrosPendientes_tratamientosRecetados1` FOREIGN KEY (`idTratamientoRecetado`) REFERENCES `tratamientosRecetados` (`idTratamientoRecetado`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `costoTratamientos`
--
ALTER TABLE `costoTratamientos`
  ADD CONSTRAINT `fk_precioTratamientos_tratamientos1` FOREIGN KEY (`idTratamiento`) REFERENCES `tratamientos` (`idTratamiento`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `detallesTratamientos`
--
ALTER TABLE `detallesTratamientos`
  ADD CONSTRAINT `fk_detallesTratamientos_productos1` FOREIGN KEY (`idProducto`) REFERENCES `productos` (`idProducto`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_detallesTratamientos_tratamientos1` FOREIGN KEY (`idTratamiento`) REFERENCES `tratamientos` (`idTratamiento`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `fotos`
--
ALTER TABLE `fotos`
  ADD CONSTRAINT `fk_fotos_historial1` FOREIGN KEY (`idHistorial`) REFERENCES `historial` (`idHistorial`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `historial`
--
ALTER TABLE `historial`
  ADD CONSTRAINT `fk_historial_pacientes1` FOREIGN KEY (`idPaciente`) REFERENCES `pacientes` (`idPaciente`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `horarios`
--
ALTER TABLE `horarios`
  ADD CONSTRAINT `fk_horarios_personal1` FOREIGN KEY (`idPersonal`) REFERENCES `personal` (`idPersonal`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `pacientes`
--
ALTER TABLE `pacientes`
  ADD CONSTRAINT `fk_pacientes_clinicas1` FOREIGN KEY (`idClinica`) REFERENCES `clinicas` (`idClinica`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `pagosTratamientos`
--
ALTER TABLE `pagosTratamientos`
  ADD CONSTRAINT `fk_pagosTratamientos_tratamientosRecetados1` FOREIGN KEY (`idTratamientoRecetado`) REFERENCES `tratamientosRecetados` (`idTratamientoRecetado`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `personal`
--
ALTER TABLE `personal`
  ADD CONSTRAINT `fk_personal_clinicas1` FOREIGN KEY (`idClinica`) REFERENCES `clinicas` (`idClinica`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `precioProductos`
--
ALTER TABLE `precioProductos`
  ADD CONSTRAINT `fk_precios_productos1` FOREIGN KEY (`idProducto`) REFERENCES `productos` (`idProducto`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `productos`
--
ALTER TABLE `productos`
  ADD CONSTRAINT `fk_productos_tiposProductos1` FOREIGN KEY (`idTipoProducto`) REFERENCES `tiposProductos` (`idTipoProducto`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `relojChecador`
--
ALTER TABLE `relojChecador`
  ADD CONSTRAINT `fk_relojChecador_personal1` FOREIGN KEY (`idPersonal`) REFERENCES `personal` (`idPersonal`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `servicios`
--
ALTER TABLE `servicios`
  ADD CONSTRAINT `fk_servicios_clinicas1` FOREIGN KEY (`idClinica`) REFERENCES `clinicas` (`idClinica`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `tiposProductos`
--
ALTER TABLE `tiposProductos`
  ADD CONSTRAINT `fk_tiposProductos_clinicas1` FOREIGN KEY (`idClinica`) REFERENCES `clinicas` (`idClinica`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `tratamientos`
--
ALTER TABLE `tratamientos`
  ADD CONSTRAINT `fk_tratamientos_clinicas1` FOREIGN KEY (`idClinica`) REFERENCES `clinicas` (`idClinica`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `tratamientosRecetados`
--
ALTER TABLE `tratamientosRecetados`
  ADD CONSTRAINT `fk_tratamientosRecetados_historial1` FOREIGN KEY (`idHistorial`) REFERENCES `historial` (`idHistorial`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_tratamientosRecetados_tratamientos1` FOREIGN KEY (`idTratamiento`) REFERENCES `tratamientos` (`idTratamiento`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `ventasProductos`
--
ALTER TABLE `ventasProductos`
  ADD CONSTRAINT `fk_ventasProductos_personal1` FOREIGN KEY (`idPersonal`) REFERENCES `personal` (`idPersonal`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `ventasProductosDetalles`
--
ALTER TABLE `ventasProductosDetalles`
  ADD CONSTRAINT `fk_ventasProductosDetalles_productos1` FOREIGN KEY (`idProducto`) REFERENCES `productos` (`idProducto`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_ventasProductosDetalles_ventasProductos1` FOREIGN KEY (`idVentasProductos`) REFERENCES `ventasProductos` (`idVentaProductos`) ON DELETE NO ACTION ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
