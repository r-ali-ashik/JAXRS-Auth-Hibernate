DROP TABLE IF EXISTS `tbl_ROLE`;
CREATE TABLE `tbl_ROLE` (
  `ROLE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `ROLE_NAME` varchar(40) NOT NULL,
  PRIMARY KEY (`ROLE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

INSERT INTO `tbl_ROLE` VALUES (1,'ROLE_ADMIN'),(2,'ROLE_USER'),(3,'ROLE_MEMBER'),(4,'ROLE_CLIENT');


DROP TABLE IF EXISTS `tbl_USER`;
CREATE TABLE `tbl_USER` (
  `USER_ID` int(11) NOT NULL AUTO_INCREMENT,
  `FIRST_NAME` varchar(40) NOT NULL,
  `LAST_NAME` varchar(40) NOT NULL,
  `USER_NAME` varchar(40) NOT NULL,
  `PASSWORD` varchar(40) NOT NULL,
  PRIMARY KEY (`USER_ID`),
  UNIQUE KEY `tbl_USER_USER_NAME_uindex` (`USER_NAME`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

INSERT INTO `tbl_USER` VALUES 
(1,'admin','admin','admin','admin'),
(2,'user','user','user','user'),
(3,'client','client','client','client');


DROP TABLE IF EXISTS `tbl_USER_ROLE`;
CREATE TABLE `tbl_USER_ROLE` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ROLE_ID` int(11) NOT NULL,
  `USER_ID` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `tbl_USER_ROLE_tbl_ROLE_ROLE_ID_fk` (`ROLE_ID`),
  KEY `tbl_USER_ROLE_tbl_USER__USER_ID_fk` (`USER_ID`),
  CONSTRAINT `tbl_USER_ROLE_tbl_ROLE_ROLE_ID_fk` FOREIGN KEY (`ROLE_ID`) REFERENCES `tbl_ROLE` (`ROLE_ID`),
  CONSTRAINT `tbl_USER_ROLE_tbl_USER__USER_ID_fk` FOREIGN KEY (`USER_ID`) REFERENCES `tbl_USER` (`USER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

INSERT INTO `tbl_USER_ROLE` VALUES (1,1,1),(2,2,2),(3,4,3),(4,3,1);