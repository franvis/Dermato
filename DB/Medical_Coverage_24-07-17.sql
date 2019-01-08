/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE MedicalCoverage (
  idMedicalCoverage int(11) NOT NULL AUTO_INCREMENT,
  medicalCoverageName varchar(100) DEFAULT NULL,
  PRIMARY KEY (idMedicalCoverage)
) ENGINE=InnoDB AUTO_INCREMENT=127 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
INSERT INTO MedicalCoverage (idMedicalCoverage, medicalCoverageName) VALUES (1,'OSPLAD'),(2,'SADAIC'),(3,'OSPRERA'),(4,'PAMI'),(5,'ATSA'),(6,'IAPOS'),(7,'SAT'),(8,'SUTIAGA'),(10,'UOM'),(11,'SOLIDARIA SALUD'),(12,'CAMI PLAN BASICO'),(13,'OSPIL'),(14,'OSDOP'),(15,'AMS PS5P'),(16,'AMUR'),(17,'OSECAC'),(18,'SOIVA'),(19,'OSDE'),(20,'OSPESGA'),(21,'JERARQUICOS SALUD'),(22,'AMS PS1'),(23,'CAMI PLAN ESPECIAL'),(24,'CAMI PLAN DORADO'),(25,'CAMI PLAN T'),(26,'AMS PS100'),(27,'AMS PG'),(28,'AMS PGP'),(29,'AMS PS5'),(30,'AMS GP'),(31,'OSBA'),(32,'OSPAC B'),(33,'ASE PDP'),(34,'OSPAC A'),(35,'NUMEN'),(36,'CAMIONEROS'),(37,'LEY N§:5.110'),(38,'OSPERCIN'),(39,'LUZ Y FUERZA'),(40,'OSSEG'),(41,'CAJA DE INGENIEROS'),(42,'DOCTHOS'),(43,'ASE PLAN E'),(44,'CIENCIAS ECONOMICAS'),(45,'UOCRA'),(46,'OPDEA'),(47,'SMATA'),(48,'FEDERADA 25 DE JUNIO'),(49,'ACA SALUD'),(50,'CAJA FORENSE'),(51,'SWISS MEDICAL'),(52,'SM SALUD'),(53,'PROTECCION FAMILIAR'),(54,'AMS A101'),(55,'ASE CDP'),(56,'SAMCO'),(57,'FUTBOLISTAS AGREMIAD'),(58,'POLICIA FEDERAL'),(59,'AMS SB'),(60,'AMS SB'),(61,'OSACRA'),(62,'AMS A1'),(63,'DASUTEN'),(64,'ASE D100'),(65,'FATFA'),(66,'OSAPM'),(67,'PLAN SALUD FAMILIAR'),(68,'GASTRONOMICOS'),(69,'ASE CD1'),(70,'OSPIM'),(71,'OMINT'),(72,'ASE AMCD'),(73,'AMS PC'),(74,'AMS PLAN AM5'),(75,'UP'),(76,'MEDICUS'),(77,'ANDAR VISITAR'),(78,'ASE CDO'),(79,'AATRA'),(80,'AMPS-AM1'),(81,'UTA'),(82,'AMPS PLAN SALUD FAMI'),(83,'AMS PS1PLUS'),(84,'PLAN S.FAMILIAR EMP.'),(85,'OSPIA'),(86,'OSTEL'),(87,'AMS SB PLUS'),(88,'ASE D5'),(89,'CAMI P.DORADO CONV.'),(90,'AS.UNION TAMBEROS F.'),(91,'ACA SALUD PLAN CERCA'),(92,'PLASTICOS'),(93,'ASE P.S1 PLUS'),(94,'OSPIV'),(95,'MAS CAMI'),(96,'MEDIFE'),(97,'AMS P 100 PLUS'),(98,'OSTCARA'),(99,'ECLESIASTICA S.PEDRO'),(100,'CORREOS'),(101,'UNIV.DEL LITORAL'),(102,'IOSE'),(103,'OSPIFER'),(104,'OSPA VIAL'),(105,'SOLIDARIA BEN HUR'),(106,'CAMI PLAN JOVEN'),(107,'OSSIMRA'),(108,'SANCOR 500'),(109,'SANCOR 1000'),(110,'SANCOR 2000'),(111,'SANCOR 3000'),(112,'SANCOR 4000'),(113,'CAMI PLAN PREMIUM'),(114,'OSPIP'),(115,'OSPAT'),(116,'OSPECOM'),(117,'EMPLEADO DE FARMACIA'),(118,'OSPERSAM'),(119,'FEDERADA SALUD'),(120,'OSPEDYC'),(121,'GALENO'),(122,'CAMI PLAN PLATA'),(123,'CAMI PLAN ADVANCE'),(124,'OSPIF'),(125,'PREVENCION SALUD'),(126,'OSCARA');