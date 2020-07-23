-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         10.4.13-MariaDB - mariadb.org binary distribution
-- SO del servidor:              Win64
-- HeidiSQL Versión:             11.0.0.5919
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Volcando estructura para tabla agrofinca.animal
CREATE TABLE IF NOT EXISTS `animal` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tipo` int(11) NOT NULL,
  `raza` int(11) NOT NULL,
  `nombre` varchar(50) DEFAULT NULL,
  `caracterizacion` varchar(250) DEFAULT NULL,
  `hierro` enum('S','N') DEFAULT 'N',
  `fecha_nacimiento` date DEFAULT NULL,
  `fecha_compra` date DEFAULT NULL,
  `genero` enum('MACHO','HEMBRA') NOT NULL,
  `castrado` enum('SI','NO') NOT NULL DEFAULT 'NO',
  `fecha_castracion` date DEFAULT NULL,
  `estado` enum('VIVO','MUERTO','VENDIDA') NOT NULL DEFAULT 'VIVO',
  PRIMARY KEY (`id`),
  KEY `FK_animal_parametros` (`tipo`),
  KEY `FK_animal_parametros_2` (`raza`),
  CONSTRAINT `FK_animal_parametros` FOREIGN KEY (`tipo`) REFERENCES `parametros` (`id`),
  CONSTRAINT `FK_animal_parametros_2` FOREIGN KEY (`raza`) REFERENCES `parametros` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=latin1 COMMENT='Registro de los animales presentes en la finca';

-- Volcando datos para la tabla agrofinca.animal: ~22 rows (aproximadamente)
/*!40000 ALTER TABLE `animal` DISABLE KEYS */;
INSERT INTO `animal` (`id`, `tipo`, `raza`, `nombre`, `caracterizacion`, `hierro`, `fecha_nacimiento`, `fecha_compra`, `genero`, `castrado`, `fecha_castracion`, `estado`) VALUES
	(1, 5, 2, 'TORO BLANCO', NULL, 'N', NULL, NULL, 'MACHO', 'NO', '0000-00-00', 'VIVO'),
	(2, 5, 2, 'VACA JANNETH', NULL, 'N', NULL, NULL, 'HEMBRA', 'NO', '0000-00-00', 'VIVO'),
	(3, 5, 2, 'VACA BLANCA', NULL, 'N', NULL, NULL, 'HEMBRA', 'NO', '0000-00-00', 'VIVO'),
	(4, 5, 2, 'VACA NEGRA CACHUDA', NULL, 'N', NULL, NULL, 'HEMBRA', 'NO', '0000-00-00', 'VENDIDA'),
	(5, 5, 2, 'NOVILLO JANNETH', NULL, 'N', NULL, NULL, 'MACHO', 'NO', '0000-00-00', 'VIVO'),
	(6, 5, 2, 'TERNERO JANNETH', NULL, 'N', NULL, NULL, 'MACHO', 'NO', '0000-00-00', 'VIVO'),
	(7, 5, 2, 'ROJO 1', NULL, 'N', NULL, NULL, 'MACHO', 'NO', '0000-00-00', 'VIVO'),
	(8, 5, 2, 'ROJO 2', NULL, 'N', NULL, NULL, 'MACHO', 'NO', '0000-00-00', 'VIVO'),
	(9, 5, 2, 'ROJO 3', NULL, 'N', NULL, NULL, 'MACHO', 'NO', '0000-00-00', 'VIVO'),
	(10, 5, 2, 'ROJO 4', NULL, 'N', NULL, NULL, 'MACHO', 'NO', '0000-00-00', 'VIVO'),
	(11, 5, 2, 'ROJO 5', NULL, 'N', NULL, NULL, 'MACHO', 'NO', '0000-00-00', 'VIVO'),
	(12, 5, 2, 'ROJO 6', NULL, 'N', NULL, NULL, 'MACHO', 'NO', '0000-00-00', 'VIVO'),
	(13, 5, 2, 'ROJO 7', NULL, 'N', NULL, NULL, 'MACHO', 'NO', '0000-00-00', 'VIVO'),
	(14, 5, 2, 'ROJO 8', NULL, 'N', NULL, NULL, 'MACHO', 'NO', '0000-00-00', 'VIVO'),
	(15, 5, 2, 'ROJO 9', NULL, 'N', NULL, NULL, 'MACHO', 'NO', '0000-00-00', 'VIVO'),
	(16, 5, 2, 'ROJO 10', NULL, 'N', NULL, NULL, 'MACHO', 'NO', '0000-00-00', 'VIVO'),
	(32, 6, 16, 'TORNADO', NULL, 'N', NULL, NULL, 'MACHO', 'SI', NULL, 'VIVO'),
	(33, 6, 16, 'CORONELL', NULL, 'N', NULL, NULL, 'MACHO', 'SI', NULL, 'VIVO'),
	(34, 6, 16, 'MORO BLANCO', NULL, 'N', NULL, NULL, 'MACHO', 'SI', NULL, 'VIVO'),
	(35, 6, 16, 'CASTAÑO MECHUDO', NULL, 'N', NULL, NULL, 'MACHO', 'SI', NULL, 'VIVO'),
	(36, 7, 7, 'CANELA', 'Mula de color canela', 'N', NULL, NULL, 'HEMBRA', 'NO', NULL, 'VIVO'),
	(37, 7, 7, 'MONA', 'Mula baya de color amarillo', 'N', NULL, NULL, 'HEMBRA', 'NO', NULL, 'VIVO'),
	(38, 5, 2, 'VACA BLANCA TOPA QUEBRADA-HONDA', 'Vaca blanca topa', 'N', NULL, '2020-06-20', 'HEMBRA', 'NO', NULL, 'VIVO'),
	(39, 5, 2, 'VACA BLANCA PURA - REGISTRO', 'Vaca brahaman con registro', 'N', NULL, '2020-06-23', 'HEMBRA', 'NO', NULL, 'VIVO');
/*!40000 ALTER TABLE `animal` ENABLE KEYS */;

-- Volcando estructura para tabla agrofinca.animal_costos
CREATE TABLE IF NOT EXISTS `animal_costos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `animal` int(11) NOT NULL,
  `evento` int(11) NOT NULL,
  `fecha` date NOT NULL,
  `valor` decimal(10,0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `FK__parametros` (`evento`) USING BTREE,
  KEY `FK_animal_costos_animal` (`animal`),
  CONSTRAINT `FK_animal_costos_animal` FOREIGN KEY (`animal`) REFERENCES `animal` (`id`),
  CONSTRAINT `animal_costos_ibfk_1` FOREIGN KEY (`evento`) REFERENCES `parametros` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

-- Volcando datos para la tabla agrofinca.animal_costos: ~11 rows (aproximadamente)
/*!40000 ALTER TABLE `animal_costos` DISABLE KEYS */;
INSERT INTO `animal_costos` (`id`, `animal`, `evento`, `fecha`, `valor`) VALUES
	(1, 38, 19, '2020-06-20', 1700000),
	(2, 7, 19, '2020-06-01', 1200000),
	(3, 8, 19, '2020-06-01', 1200000),
	(4, 9, 19, '2020-06-01', 1200000),
	(5, 10, 19, '2020-06-01', 1200000),
	(6, 11, 19, '2020-06-01', 1200000),
	(7, 12, 19, '2020-06-01', 1200000),
	(8, 13, 19, '2020-06-01', 1200000),
	(9, 14, 19, '2020-06-01', 1200000),
	(10, 15, 19, '2020-06-01', 1200000),
	(11, 16, 19, '2020-06-01', 1200000),
	(12, 4, 20, '2020-06-23', 1800000),
	(13, 39, 19, '2020-06-23', 2600000);
/*!40000 ALTER TABLE `animal_costos` ENABLE KEYS */;

-- Volcando estructura para tabla agrofinca.animal_eventos
CREATE TABLE IF NOT EXISTS `animal_eventos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `animal` int(11) NOT NULL,
  `evento` int(11) NOT NULL,
  `fecha` date NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `FK_animal_eventos_animal` (`animal`),
  KEY `FK_animal_eventos_parametros` (`evento`),
  CONSTRAINT `FK_animal_eventos_animal` FOREIGN KEY (`animal`) REFERENCES `animal` (`id`),
  CONSTRAINT `FK_animal_eventos_parametros` FOREIGN KEY (`evento`) REFERENCES `parametros` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

-- Volcando datos para la tabla agrofinca.animal_eventos: ~0 rows (aproximadamente)
/*!40000 ALTER TABLE `animal_eventos` DISABLE KEYS */;
/*!40000 ALTER TABLE `animal_eventos` ENABLE KEYS */;

-- Volcando estructura para tabla agrofinca.animal_imagen
CREATE TABLE IF NOT EXISTS `animal_imagen` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `animal` int(11) NOT NULL,
  `fecha` date NOT NULL,
  `nota` text NOT NULL DEFAULT '',
  `imagen` blob NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  KEY `FK__animal` (`animal`),
  CONSTRAINT `FK__animal` FOREIGN KEY (`animal`) REFERENCES `animal` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='Registro de imagenes de cada animal';

-- Volcando datos para la tabla agrofinca.animal_imagen: ~0 rows (aproximadamente)
/*!40000 ALTER TABLE `animal_imagen` DISABLE KEYS */;
/*!40000 ALTER TABLE `animal_imagen` ENABLE KEYS */;

-- Volcando estructura para tabla agrofinca.animal_pastoreo
CREATE TABLE IF NOT EXISTS `animal_pastoreo` (
  `pastoreo` int(11) NOT NULL,
  `animal` int(11) NOT NULL,
  PRIMARY KEY (`pastoreo`,`animal`) USING BTREE,
  KEY `FK_animal_pastoreo_animal` (`animal`),
  CONSTRAINT `FK_animal_pastoreo_animal` FOREIGN KEY (`animal`) REFERENCES `animal` (`id`),
  CONSTRAINT `FK_animal_pastoreo_potrero_actividad` FOREIGN KEY (`pastoreo`) REFERENCES `potrero_actividad` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

-- Volcando datos para la tabla agrofinca.animal_pastoreo: ~61 rows (aproximadamente)
/*!40000 ALTER TABLE `animal_pastoreo` DISABLE KEYS */;
INSERT INTO `animal_pastoreo` (`pastoreo`, `animal`) VALUES
	(1, 1),
	(1, 2),
	(1, 3),
	(1, 4),
	(1, 5),
	(1, 6),
	(1, 7),
	(1, 8),
	(1, 9),
	(1, 10),
	(1, 11),
	(1, 12),
	(1, 13),
	(1, 14),
	(1, 15),
	(1, 16),
	(2, 1),
	(2, 2),
	(2, 3),
	(2, 4),
	(2, 5),
	(2, 6),
	(2, 7),
	(2, 8),
	(2, 9),
	(2, 10),
	(2, 11),
	(2, 12),
	(2, 13),
	(2, 14),
	(2, 15),
	(2, 16),
	(2, 32),
	(2, 33),
	(2, 34),
	(2, 35),
	(2, 36),
	(2, 37),
	(3, 1),
	(3, 2),
	(3, 3),
	(3, 5),
	(3, 6),
	(3, 7),
	(3, 8),
	(3, 9),
	(3, 10),
	(3, 11),
	(3, 12),
	(3, 13),
	(3, 14),
	(3, 15),
	(3, 16),
	(3, 32),
	(3, 33),
	(3, 34),
	(3, 35),
	(3, 36),
	(3, 37),
	(3, 38),
	(3, 39);
/*!40000 ALTER TABLE `animal_pastoreo` ENABLE KEYS */;

-- Volcando estructura para tabla agrofinca.animal_peso
CREATE TABLE IF NOT EXISTS `animal_peso` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `animal` int(11) NOT NULL,
  `evento` int(11) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `peso` decimal(10,0) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK__parametros` (`evento`),
  KEY `FK_animal_peso_animal` (`animal`),
  CONSTRAINT `FK__parametros` FOREIGN KEY (`evento`) REFERENCES `parametros` (`id`),
  CONSTRAINT `FK_animal_peso_animal` FOREIGN KEY (`animal`) REFERENCES `animal` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla agrofinca.animal_peso: ~0 rows (aproximadamente)
/*!40000 ALTER TABLE `animal_peso` DISABLE KEYS */;
/*!40000 ALTER TABLE `animal_peso` ENABLE KEYS */;

-- Volcando estructura para tabla agrofinca.animal_vacunas
CREATE TABLE IF NOT EXISTS `animal_vacunas` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `animal` int(11) NOT NULL,
  `fecha` date NOT NULL,
  `tipo` int(11) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `laboratorio` varchar(50) NOT NULL,
  `dosis` varchar(50) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `FK_animal_vacunas_animal` (`animal`),
  KEY `FK_animal_vacunas_parametros` (`tipo`),
  CONSTRAINT `FK_animal_vacunas_animal` FOREIGN KEY (`animal`) REFERENCES `animal` (`id`),
  CONSTRAINT `FK_animal_vacunas_parametros` FOREIGN KEY (`tipo`) REFERENCES `parametros` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

-- Volcando datos para la tabla agrofinca.animal_vacunas: ~0 rows (aproximadamente)
/*!40000 ALTER TABLE `animal_vacunas` DISABLE KEYS */;
/*!40000 ALTER TABLE `animal_vacunas` ENABLE KEYS */;

-- Volcando estructura para tabla agrofinca.finca
CREATE TABLE IF NOT EXISTS `finca` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL DEFAULT '',
  `area` decimal(10,0) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla agrofinca.finca: ~2 rows (aproximadamente)
/*!40000 ALTER TABLE `finca` DISABLE KEYS */;
INSERT INTO `finca` (`id`, `nombre`, `area`) VALUES
	(1, 'LA ESTRELLA', 0),
	(2, 'EL DIAMANTE', 19);
/*!40000 ALTER TABLE `finca` ENABLE KEYS */;

-- Volcando estructura para función agrofinca.f_calculo_dias_descanso
DELIMITER //
CREATE FUNCTION `f_calculo_dias_descanso`(`fecha_limpia` DATE,
	`ocupado` ENUM('S','N')
) RETURNS int(11)
    COMMENT 'Función que calcula el tiempo de descanso de un potrero'
BEGIN
	DECLARE dias INT;
	SET dias = 0;
	IF fecha_limpia IS NOT NULL AND ocupado = 'N' THEN
		set dias = timestampdiff(DAY,fecha_limpia,CURDATE());
	END IF;
	
	RETURN dias;
END//
DELIMITER ;

-- Volcando estructura para tabla agrofinca.parametros
CREATE TABLE IF NOT EXISTS `parametros` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(150) NOT NULL,
  `id_padre` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_parametros_parametros` (`id_padre`),
  CONSTRAINT `FK_parametros_parametros` FOREIGN KEY (`id_padre`) REFERENCES `parametros` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla agrofinca.parametros: ~17 rows (aproximadamente)
/*!40000 ALTER TABLE `parametros` DISABLE KEYS */;
INSERT INTO `parametros` (`id`, `descripcion`, `id_padre`) VALUES
	(1, 'RAZAS BOVINAS', NULL),
	(2, 'BRAHAMAN', 1),
	(3, 'CRIOLLA', 1),
	(4, 'TIPOS DE ANIMAL', NULL),
	(5, 'BOVINO', 4),
	(6, 'EQUINO', 4),
	(7, 'MULAR', 4),
	(8, 'CAPRINO', 4),
	(9, 'OVINO', 4),
	(10, 'EVENTOS PESO ANIMAL', NULL),
	(11, 'NACIMIENTO', 10),
	(12, 'COMPRA', 10),
	(13, 'VENTA', 10),
	(14, 'PESAJE REGULAR', 10),
	(15, 'RAZAS EQUINAS', NULL),
	(16, 'CRIOLLO', 15),
	(17, 'CUARTO DE MILLA', 15),
	(18, 'TRANSACCIONES', NULL),
	(19, 'COMPRA', 18),
	(20, 'VENTA', 18);
/*!40000 ALTER TABLE `parametros` ENABLE KEYS */;

-- Volcando estructura para tabla agrofinca.persona
CREATE TABLE IF NOT EXISTS `persona` (
  `id` int(11) NOT NULL,
  `tipo_documento` enum('CC','TI','CE') NOT NULL,
  `num_documento` bigint(20) NOT NULL DEFAULT 0,
  `primer_nombre` varchar(50) NOT NULL DEFAULT '',
  `segundo_nombre` varchar(50) DEFAULT '',
  `primer_apellido` varchar(50) NOT NULL DEFAULT '',
  `segundo_apellido` varchar(50) DEFAULT '',
  `fecha_nacimiento` date DEFAULT NULL,
  `genero` enum('H','M') NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='Almacena la infrmación de las personas';

-- Volcando datos para la tabla agrofinca.persona: ~0 rows (aproximadamente)
/*!40000 ALTER TABLE `persona` DISABLE KEYS */;
/*!40000 ALTER TABLE `persona` ENABLE KEYS */;

-- Volcando estructura para tabla agrofinca.potrero
CREATE TABLE IF NOT EXISTS `potrero` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `finca` int(11) NOT NULL,
  `nombre` varchar(50) NOT NULL DEFAULT '',
  `descripcion` varchar(200) DEFAULT '',
  `pasto` varchar(200) DEFAULT '',
  `area` decimal(10,0) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_potrero_finca` (`finca`),
  CONSTRAINT `FK_potrero_finca` FOREIGN KEY (`finca`) REFERENCES `finca` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla agrofinca.potrero: ~12 rows (aproximadamente)
/*!40000 ALTER TABLE `potrero` DISABLE KEYS */;
INSERT INTO `potrero` (`id`, `finca`, `nombre`, `descripcion`, `pasto`, `area`) VALUES
	(1, 1, 'LA CAJITA', '', '', NULL),
	(2, 1, 'EL AHOGADO', '', '', NULL),
	(3, 1, 'LOS PANTANOS', '', '', NULL),
	(4, 1, 'EL TOLEDO', '', '', NULL),
	(5, 1, 'EL ESTABLO', '', '', NULL),
	(6, 1, 'EL GUAYABO', '', '', NULL),
	(7, 1, 'GARCIA', '', '', NULL),
	(8, 1, 'CASA PARA ARRIBA', '', '', NULL),
	(9, 1, 'LOS POLOS', '', '', NULL),
	(10, 1, 'TRANSFORMADOR 1', '', '', NULL),
	(11, 1, 'TRANSFORMADOR 2', '', '', NULL),
	(12, 1, 'PUENTE CHIRAJARA', '', '', NULL),
	(13, 1, 'LA PLATANERA', '', '', NULL),
	(14, 1, 'LA CORRAJELA', '', '', NULL);
/*!40000 ALTER TABLE `potrero` ENABLE KEYS */;

-- Volcando estructura para tabla agrofinca.potrero_actividad
CREATE TABLE IF NOT EXISTS `potrero_actividad` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `potrero` int(11) NOT NULL DEFAULT 0,
  `fecha_ingreso` date DEFAULT NULL,
  `fecha_salida` date DEFAULT NULL,
  `cantidad_bovinos` int(11) DEFAULT NULL,
  `cantidad_equinos` int(11) DEFAULT NULL,
  `cantidad_mulares` int(11) DEFAULT NULL,
  `fecha_limpia` date DEFAULT NULL,
  `dias_descanso` int(11) DEFAULT timestampdiff(DAY,`fecha_limpia`,curdate()),
  `dias_carga` int(11) DEFAULT NULL,
  `ocupado` enum('S','N') NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id`),
  KEY `FK_potrero_actividad_potrero` (`potrero`),
  CONSTRAINT `FK_potrero_actividad_potrero` FOREIGN KEY (`potrero`) REFERENCES `potrero` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla agrofinca.potrero_actividad: ~3 rows (aproximadamente)
/*!40000 ALTER TABLE `potrero_actividad` DISABLE KEYS */;
INSERT INTO `potrero_actividad` (`id`, `potrero`, `fecha_ingreso`, `fecha_salida`, `cantidad_bovinos`, `cantidad_equinos`, `cantidad_mulares`, `fecha_limpia`, `dias_descanso`, `dias_carga`, `ocupado`) VALUES
	(1, 7, '2020-05-01', '2020-05-21', 16, 4, 2, '2020-06-09', NULL, 20, 'N'),
	(2, 8, '2020-06-06', '2020-06-20', 16, 4, 2, '2020-06-26', NULL, 14, 'N'),
	(3, 14, '2020-06-20', NULL, 17, 4, 2, NULL, NULL, NULL, 'N');
/*!40000 ALTER TABLE `potrero_actividad` ENABLE KEYS */;

-- Volcando estructura para vista agrofinca.v_potrero_mtto
-- Creando tabla temporal para superar errores de dependencia de VIEW
CREATE TABLE `v_potrero_mtto` (
	`id` INT(11) NOT NULL,
	`potrero` INT(11) NOT NULL,
	`fecha_ingreso` DATE NULL,
	`fecha_salida` DATE NULL,
	`cantidad_bovinos` INT(11) NULL,
	`cantidad_equinos` INT(11) NULL,
	`cantidad_mulares` INT(11) NULL,
	`fecha_limpia` DATE NULL,
	`dias_descanso` INT(11) NULL,
	`dias_carga` INT(11) NULL,
	`ocupado` ENUM('S','N') NOT NULL COLLATE 'latin1_swedish_ci'
) ENGINE=MyISAM;

-- Volcando estructura para disparador agrofinca.after_despaste
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `after_despaste` BEFORE UPDATE ON `potrero_actividad` FOR EACH ROW BEGIN
	IF NEW.fecha_salida IS NOT NULL THEN
		SET NEW.dias_carga = (SELECT TIMESTAMPDIFF(DAY, NEW.fecha_ingreso, NEW.fecha_salida)); 
	END IF;
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Volcando estructura para vista agrofinca.v_potrero_mtto
-- Eliminando tabla temporal y crear estructura final de VIEW
DROP TABLE IF EXISTS `v_potrero_mtto`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `v_potrero_mtto` AS SELECT id,
	potrero,
	fecha_ingreso,
	fecha_salida,
	cantidad_bovinos,
	cantidad_equinos,
	cantidad_mulares,
	fecha_limpia,
	f_calculo_dias_descanso(fecha_limpia, ocupado) AS dias_descanso,
	dias_carga,
	ocupado
FROM potrero_actividad ;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
