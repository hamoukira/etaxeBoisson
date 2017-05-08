-- phpMyAdmin SQL Dump
-- version 4.6.4
-- https://www.phpmyadmin.net/
--
-- Client :  127.0.0.1
-- Généré le :  Lun 08 Mai 2017 à 12:39
-- Version du serveur :  5.7.14
-- Version de PHP :  7.0.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `etaxecommynaldb`
--

-- --------------------------------------------------------

--
-- Structure de la table `activite`
--

CREATE TABLE `activite` (
  `ID` bigint(20) NOT NULL,
  `NOM` varchar(255) DEFAULT NULL,
  `TVA` double DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Contenu de la table `activite`
--

INSERT INTO `activite` (`ID`, `NOM`, `TVA`) VALUES
(1, 'cafe', 12),
(2, 'hotel', 13),
(3, 'bar', 15),
(4, 'fastFood', 12.5);

-- --------------------------------------------------------

--
-- Structure de la table `commune`
--

CREATE TABLE `commune` (
  `ID` bigint(20) NOT NULL,
  `NAME` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Contenu de la table `commune`
--

INSERT INTO `commune` (`ID`, `NAME`) VALUES
(1, 'Hivernage'),
(2, 'Menara'),
(3, 'medina'),
(4, 'Nakhil'),
(5, 'sidi youssef ben ali');

-- --------------------------------------------------------

--
-- Structure de la table `device`
--

CREATE TABLE `device` (
  `ID` bigint(20) NOT NULL,
  `BROWSER` varchar(255) DEFAULT NULL,
  `DEVICECATEGORIE` varchar(255) DEFAULT NULL,
  `OPERATINGSYSTEM` varchar(255) DEFAULT NULL,
  `USER_LOGIN` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Contenu de la table `device`
--

INSERT INTO `device` (`ID`, `BROWSER`, `DEVICECATEGORIE`, `OPERATINGSYSTEM`, `USER_LOGIN`) VALUES
(1, 'Firefox', 'Personal computer', 'Windows', 'test');

-- --------------------------------------------------------

--
-- Structure de la table `history`
--

CREATE TABLE `history` (
  `ID` bigint(20) NOT NULL,
  `INOUTTIMESTAMP` datetime DEFAULT NULL,
  `TYPE` int(11) DEFAULT NULL,
  `USERLOGIN` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Contenu de la table `history`
--

INSERT INTO `history` (`ID`, `INOUTTIMESTAMP`, `TYPE`, `USERLOGIN`) VALUES
(1, '2017-05-07 06:10:51', 1, 'test'),
(2, '2017-05-07 06:41:27', 1, 'test'),
(3, '2017-05-07 06:44:41', 2, 'test'),
(4, '2017-05-07 06:45:05', 1, 'test'),
(5, '2017-05-07 06:46:11', 2, 'test'),
(6, '2017-05-07 06:46:15', 1, 'test'),
(7, '2017-05-07 08:42:48', 1, 'test'),
(8, '2017-05-07 08:43:30', 2, 'test'),
(9, '2017-05-07 08:44:28', 1, 'test'),
(10, '2017-05-07 09:03:42', 1, 'test'),
(11, '2017-05-07 09:06:05', 2, 'test'),
(12, '2017-05-07 09:06:48', 1, 'test'),
(13, '2017-05-07 09:43:59', 2, 'test'),
(14, '2017-05-07 09:50:18', 1, 'test'),
(15, '2017-05-07 09:50:21', 2, 'test'),
(16, '2017-05-07 09:50:25', 1, 'test'),
(17, '2017-05-07 10:06:34', 1, 'test'),
(18, '2017-05-07 10:06:37', 2, 'test'),
(19, '2017-05-07 10:06:40', 1, 'test'),
(20, '2017-05-07 10:17:09', 2, 'test'),
(21, '2017-05-07 10:17:13', 1, 'test'),
(22, '2017-05-07 10:17:27', 2, 'test'),
(23, '2017-05-07 10:21:23', 1, 'test'),
(24, '2017-05-07 10:27:01', 1, 'test'),
(25, '2017-05-07 10:43:23', 2, 'test'),
(26, '2017-05-07 10:44:00', 1, 'test'),
(27, '2017-05-07 11:22:51', 2, 'test'),
(28, '2017-05-07 11:22:54', 1, 'test'),
(29, '2017-05-07 11:33:59', 2, 'test'),
(30, '2017-05-07 11:34:10', 2, 'test'),
(31, '2017-05-07 11:34:28', 2, 'test'),
(32, '2017-05-07 11:34:54', 1, 'test'),
(33, '2017-05-07 12:21:50', 1, 'test'),
(34, '2017-05-07 12:28:56', 2, 'test'),
(35, '2017-05-07 12:59:11', 1, 'test'),
(36, '2017-05-07 13:03:07', 1, 'test'),
(37, '2017-05-07 13:07:26', 1, 'test'),
(38, '2017-05-07 13:14:25', 2, 'test'),
(39, '2017-05-07 13:57:07', 1, 'test'),
(40, '2017-05-07 14:23:28', 2, 'test'),
(41, '2017-05-08 12:19:40', 1, 'test'),
(42, '2017-05-08 12:33:11', 2, 'test'),
(43, '2017-05-08 12:53:32', 1, 'test'),
(44, '2017-05-08 12:53:37', 2, 'test'),
(45, '2017-05-08 12:53:45', 1, 'test'),
(46, '2017-05-08 12:53:53', 2, 'test');

-- --------------------------------------------------------

--
-- Structure de la table `journal`
--

CREATE TABLE `journal` (
  `ID` bigint(20) NOT NULL,
  `CLASSNAME` varchar(255) DEFAULT NULL,
  `DATEDEMODIFICATION` datetime DEFAULT NULL,
  `MESSAGE` text,
  `NEWVALUE` text,
  `OLDEVALUE` text,
  `TYPEDACTION` int(11) DEFAULT NULL,
  `USERLOGIN` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `locale`
--

CREATE TABLE `locale` (
  `ID` bigint(20) NOT NULL,
  `COMPLEMENTADRESS` varchar(255) DEFAULT NULL,
  `NOM` varchar(255) DEFAULT NULL,
  `GERANT_ID` bigint(20) DEFAULT NULL,
  `PROPRIETE_ID` bigint(20) DEFAULT NULL,
  `RUE_ID` bigint(20) DEFAULT NULL,
  `POSITION_ID` bigint(20) DEFAULT NULL,
  `TYPELOCAL_ID` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Contenu de la table `locale`
--

INSERT INTO `locale` (`ID`, `COMPLEMENTADRESS`, `NOM`, `GERANT_ID`, `PROPRIETE_ID`, `RUE_ID`, `POSITION_ID`, `TYPELOCAL_ID`) VALUES
(1, 'N1', 'Hot55', 2, 1, 2, 1, 1),
(2, 'N3', 'la flame', NULL, 4, 4, 2, 1),
(3, 'N2', 'Red', NULL, 5, 6, 3, 2),
(4, 'N22', 'skyfive', 3, NULL, 4, 4, 3);

-- --------------------------------------------------------

--
-- Structure de la table `position`
--

CREATE TABLE `position` (
  `ID` bigint(20) NOT NULL,
  `LAT` double DEFAULT NULL,
  `LNG` double DEFAULT NULL,
  `COMMUNE_ID` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Contenu de la table `position`
--

INSERT INTO `position` (`ID`, `LAT`, `LNG`, `COMMUNE_ID`) VALUES
(1, 31.63678745265228, -8.047459603403695, NULL),
(2, 31.63353553970217, -8.005187988019316, NULL),
(3, 31.627214080237376, -7.992399215436308, NULL),
(4, 31.635874005442144, -8.015230178571073, NULL);

-- --------------------------------------------------------

--
-- Structure de la table `quartier`
--

CREATE TABLE `quartier` (
  `ID` bigint(20) NOT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `SECTEUR_ID` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Contenu de la table `quartier`
--

INSERT INTO `quartier` (`ID`, `NAME`, `SECTEUR_ID`) VALUES
(1, 'Hivernage', 1),
(2, 'Hay zitoune', 2),
(3, 'hartie', 3),
(4, 'lalla hia', 6),
(5, 'masmoudi', 6),
(6, 'Massira 1', 5),
(7, 'massira 2', 5);

-- --------------------------------------------------------

--
-- Structure de la table `redevable`
--

CREATE TABLE `redevable` (
  `ID` bigint(20) NOT NULL,
  `CIN` varchar(255) DEFAULT NULL,
  `DATEDECOMMENCEMENT` date DEFAULT NULL,
  `MAIL` varchar(255) DEFAULT NULL,
  `NATURE` int(11) DEFAULT NULL,
  `NOM` varchar(255) DEFAULT NULL,
  `PRENOM` varchar(255) DEFAULT NULL,
  `RC` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Contenu de la table `redevable`
--

INSERT INTO `redevable` (`ID`, `CIN`, `DATEDECOMMENCEMENT`, `MAIL`, `NATURE`, `NOM`, `PRENOM`, `RC`) VALUES
(1, 'az123', '2014-05-01', 'hamoukira@gmail.com', 2, 'lmarbouh', 'mhamed', NULL),
(2, 'non', '2005-05-02', 'quik@mail.com', 1, 'non', 'Quik', 'az1234'),
(3, 'ae1234', '2013-05-01', 'fatima@mail.com', 1, 'lmarbouh', 'fatim zehra', NULL),
(4, 'az1235', '2012-05-02', 'safa@mail.com', 2, 'el jemli', 'safae', NULL),
(5, 'az1236', '2015-05-03', 'hanane@mail.com', 2, 'el asri', 'hanane', NULL);

-- --------------------------------------------------------

--
-- Structure de la table `rue`
--

CREATE TABLE `rue` (
  `ID` bigint(20) NOT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `QUARTIER_ID` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Contenu de la table `rue`
--

INSERT INTO `rue` (`ID`, `NAME`, `QUARTIER_ID`) VALUES
(1, 'dahak', 7),
(2, 'Rue jaber ibn hayan', 1),
(3, 'Rue ibn toufail', 5),
(4, 'Rue zellaqa', 2),
(5, 'Rue ibn ayta', 3),
(6, 'Rue ibn Banna', 4);

-- --------------------------------------------------------

--
-- Structure de la table `secteur`
--

CREATE TABLE `secteur` (
  `ID` bigint(20) NOT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `COMMUNE_ID` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Contenu de la table `secteur`
--

INSERT INTO `secteur` (`ID`, `NAME`, `COMMUNE_ID`) VALUES
(1, 'HIVERNAGE', 1),
(2, 'GUELIZ', 1),
(3, 'AMRCHICH', 1),
(4, 'HAY MOHAMADI', 1),
(5, 'Massirra', 3),
(6, 'Targa', 3);

-- --------------------------------------------------------

--
-- Structure de la table `tauxretardboisontrim`
--

CREATE TABLE `tauxretardboisontrim` (
  `ID` bigint(20) NOT NULL,
  `TAUXRETARDAUTREMOIS` double DEFAULT NULL,
  `TAUXRETARDPREMIERMOIS` double DEFAULT NULL,
  `TAUXBOISSONTAXE_ID` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Contenu de la table `tauxretardboisontrim`
--

INSERT INTO `tauxretardboisontrim` (`ID`, `TAUXRETARDAUTREMOIS`, `TAUXRETARDPREMIERMOIS`, `TAUXBOISSONTAXE_ID`) VALUES
(1, 6, 8, 1),
(2, 7, 8, 2),
(3, 9, 11, 3),
(5, 6.7, 8, 4);

-- --------------------------------------------------------

--
-- Structure de la table `tauxtaxeboisson`
--

CREATE TABLE `tauxtaxeboisson` (
  `ID` bigint(20) NOT NULL,
  `TAUX` double DEFAULT NULL,
  `ACTIVITE_ID` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Contenu de la table `tauxtaxeboisson`
--

INSERT INTO `tauxtaxeboisson` (`ID`, `TAUX`, `ACTIVITE_ID`) VALUES
(1, 10, 1),
(2, 9, 2),
(3, 12, 3),
(4, 10, 4);

-- --------------------------------------------------------

--
-- Structure de la table `taxeannuelboisson`
--

CREATE TABLE `taxeannuelboisson` (
  `ID` bigint(20) NOT NULL,
  `ANNEE` int(11) DEFAULT NULL,
  `FINISHED` int(11) DEFAULT NULL,
  `MONTANTTAXEANNUEL` double DEFAULT NULL,
  `LOCALE_ID` bigint(20) DEFAULT NULL,
  `REDEVABLE_ID` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Contenu de la table `taxeannuelboisson`
--

INSERT INTO `taxeannuelboisson` (`ID`, `ANNEE`, `FINISHED`, `MONTANTTAXEANNUEL`, `LOCALE_ID`, `REDEVABLE_ID`) VALUES
(1, 2015, -1, 16188.48, 1, 2),
(2, 2016, 0, 48214.29, 2, 4),
(3, 2016, 0, 173849.92, 3, 5),
(4, 2015, 0, 231631.87, 3, 5),
(5, 2015, 0, 41610.39, 2, 4),
(6, 2016, 0, 118396.45, 1, 2),
(7, 2014, 0, 47550.87, 1, 2),
(8, 2015, 0, 1285.73, 5, 2);

-- --------------------------------------------------------

--
-- Structure de la table `taxetrimboisson`
--

CREATE TABLE `taxetrimboisson` (
  `ID` bigint(20) NOT NULL,
  `CHIFFREAFFAIREHT` double DEFAULT NULL,
  `CHIFFREAFFAIRETTC` double DEFAULT NULL,
  `DATEACTUEL` date DEFAULT NULL,
  `MONTANTRETARDAUTREMOIS` double DEFAULT NULL,
  `MONTANTRETARDPREMIERMOIS` double DEFAULT NULL,
  `MONTANTTAXE` double DEFAULT NULL,
  `MONTANTTOTALRETARD` double DEFAULT NULL,
  `MONTANTTOTALTAXE` double DEFAULT NULL,
  `NUMEROTRIM` int(11) DEFAULT NULL,
  `TAXEYEAR` date DEFAULT NULL,
  `USERLOGIN` varchar(255) DEFAULT NULL,
  `LOCAL_ID` bigint(20) DEFAULT NULL,
  `REDEVABLE_ID` bigint(20) DEFAULT NULL,
  `TAXEANNUELBOISSON_ID` bigint(20) DEFAULT NULL,
  `USER_LOGIN` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Contenu de la table `taxetrimboisson`
--

INSERT INTO `taxetrimboisson` (`ID`, `CHIFFREAFFAIREHT`, `CHIFFREAFFAIRETTC`, `DATEACTUEL`, `MONTANTRETARDAUTREMOIS`, `MONTANTRETARDPREMIERMOIS`, `MONTANTTAXE`, `MONTANTTOTALRETARD`, `MONTANTTOTALTAXE`, `NUMEROTRIM`, `TAXEYEAR`, `USERLOGIN`, `LOCAL_ID`, `REDEVABLE_ID`, `TAXEANNUELBOISSON_ID`, `USER_LOGIN`) VALUES
(1, 2142.86, 2400, '2017-05-07', 3085.72, 171.43, 214.29, 3257.15, 3471.44, 1, '2015-01-01', NULL, 1, 1, 1, 'test'),
(2, 1800, 2016, '2017-05-07', 2268, 144, 180, 2412, 2592, 2, '2015-01-01', NULL, 1, 1, 1, 'test'),
(3, 66964.29, 75000, '2017-05-07', 36160.72, 5357.15, 6696.43, 41517.86, 48214.29, 2, '2016-01-01', NULL, 2, 4, 2, 'test'),
(4, 217312.39, 245563, '2017-05-07', 136906.81, 17385, 19558.12, 154291.8, 173849.92, 2, '2016-01-01', NULL, 3, 5, 3, 'test'),
(5, 8035.72, 9000, '2017-05-07', 8678.58, 642.86, 803.58, 9321.44, 10125.02, 3, '2015-01-01', NULL, 1, 2, 1, 'test'),
(6, 141238.94, 159600, '2017-05-07', 207621.25, 11299.12, 12711.51, 218920.36, 231631.87, 2, '2015-01-01', NULL, 3, 5, 4, 'test'),
(7, 33024.11, 36987, '2017-05-07', 35666.04, 2641.93, 3302.42, 38307.97, 41610.39, 3, '2015-01-01', NULL, 2, 4, 5, 'test'),
(8, 219252.68, 245563, '2017-05-07', 78930.97, 17540.22, 21925.27, 96471.18, 118396.45, 3, '2016-01-01', NULL, 1, 2, 6, 'test'),
(9, 22014.29, 24656, '2017-05-07', 43588.3, 1761.15, 2201.43, 45349.44, 47550.87, 2, '2014-01-01', NULL, 1, 1, 7, 'test');

-- --------------------------------------------------------

--
-- Structure de la table `userr`
--

CREATE TABLE `userr` (
  `LOGIN` varchar(255) NOT NULL,
  `ADMINN` tinyint(1) DEFAULT '0',
  `ADRESSAGE` tinyint(1) DEFAULT '0',
  `BLOCKED` tinyint(1) DEFAULT '0',
  `EMAIL` varchar(255) DEFAULT NULL,
  `LOCALS` tinyint(1) DEFAULT '0',
  `NBRCNX` int(11) DEFAULT NULL,
  `NOM` varchar(255) DEFAULT NULL,
  `PASSWRD` varchar(255) DEFAULT NULL,
  `PRENOM` varchar(255) DEFAULT NULL,
  `REDEVABLE` tinyint(1) DEFAULT '0',
  `TAXES` tinyint(1) DEFAULT '0',
  `TEL` varchar(255) DEFAULT NULL,
  `COMMUNE_ID` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Contenu de la table `userr`
--

INSERT INTO `userr` (`LOGIN`, `ADMINN`, `ADRESSAGE`, `BLOCKED`, `EMAIL`, `LOCALS`, `NBRCNX`, `NOM`, `PASSWRD`, `PRENOM`, `REDEVABLE`, `TAXES`, `TEL`, `COMMUNE_ID`) VALUES
('user1', 0, 1, 0, 'hamoukira@gmail.com', 1, 0, 'Lmarbouh', 'f2d81a260dea8a100dd517984e53c56a7523d96942a834b9cdc249bd4e8c7aa9', 'Mhamed', 1, 0, '0679462424', 2),
('admin', 1, 1, 0, 'test@test.test', 1, 0, 'Lmarbouh', 'f2d81a260dea8a100dd517984e53c56a7523d96942a834b9cdc249bd4e8c7aa9', 'Mhamed', 1, 1, '0679462424', 1),
('user2', 0, 0, 0, 'hamoukira@gmail.com', 1, 0, 'el jemli', 'f2d81a260dea8a100dd517984e53c56a7523d96942a834b9cdc249bd4e8c7aa9', 'safae', 0, 1, '0679462424', 2);

--
-- Index pour les tables exportées
--

--
-- Index pour la table `activite`
--
ALTER TABLE `activite`
  ADD PRIMARY KEY (`ID`);

--
-- Index pour la table `commune`
--
ALTER TABLE `commune`
  ADD PRIMARY KEY (`ID`);

--
-- Index pour la table `device`
--
ALTER TABLE `device`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `FK_DEVICE_USER_LOGIN` (`USER_LOGIN`);

--
-- Index pour la table `history`
--
ALTER TABLE `history`
  ADD PRIMARY KEY (`ID`);

--
-- Index pour la table `journal`
--
ALTER TABLE `journal`
  ADD PRIMARY KEY (`ID`);

--
-- Index pour la table `locale`
--
ALTER TABLE `locale`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `FK_LOCALE_GERANT_ID` (`GERANT_ID`),
  ADD KEY `FK_LOCALE_PROPRIETE_ID` (`PROPRIETE_ID`),
  ADD KEY `FK_LOCALE_TYPELOCAL_ID` (`TYPELOCAL_ID`),
  ADD KEY `FK_LOCALE_POSITION_ID` (`POSITION_ID`),
  ADD KEY `FK_LOCALE_RUE_ID` (`RUE_ID`);

--
-- Index pour la table `position`
--
ALTER TABLE `position`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `FK_POSITION_COMMUNE_ID` (`COMMUNE_ID`);

--
-- Index pour la table `quartier`
--
ALTER TABLE `quartier`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `FK_QUARTIER_SECTEUR_ID` (`SECTEUR_ID`);

--
-- Index pour la table `redevable`
--
ALTER TABLE `redevable`
  ADD PRIMARY KEY (`ID`);

--
-- Index pour la table `rue`
--
ALTER TABLE `rue`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `FK_RUE_QUARTIER_ID` (`QUARTIER_ID`);

--
-- Index pour la table `secteur`
--
ALTER TABLE `secteur`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `FK_SECTEUR_COMMUNE_ID` (`COMMUNE_ID`);

--
-- Index pour la table `tauxretardboisontrim`
--
ALTER TABLE `tauxretardboisontrim`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `FK_TAUXRETARDBOISONTRIM_TAUXBOISSONTAXE_ID` (`TAUXBOISSONTAXE_ID`);

--
-- Index pour la table `tauxtaxeboisson`
--
ALTER TABLE `tauxtaxeboisson`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `FK_TAUXTAXEBOISSON_ACTIVITE_ID` (`ACTIVITE_ID`);

--
-- Index pour la table `taxeannuelboisson`
--
ALTER TABLE `taxeannuelboisson`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `FK_TAXEANNUELBOISSON_REDEVABLE_ID` (`REDEVABLE_ID`),
  ADD KEY `FK_TAXEANNUELBOISSON_LOCALE_ID` (`LOCALE_ID`);

--
-- Index pour la table `taxetrimboisson`
--
ALTER TABLE `taxetrimboisson`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `FK_TAXETRIMBOISSON_REDEVABLE_ID` (`REDEVABLE_ID`),
  ADD KEY `FK_TAXETRIMBOISSON_TAXEANNUELBOISSON_ID` (`TAXEANNUELBOISSON_ID`),
  ADD KEY `FK_TAXETRIMBOISSON_LOCAL_ID` (`LOCAL_ID`),
  ADD KEY `FK_TAXETRIMBOISSON_USER_LOGIN` (`USER_LOGIN`);

--
-- Index pour la table `userr`
--
ALTER TABLE `userr`
  ADD PRIMARY KEY (`LOGIN`),
  ADD KEY `FK_USERR_COMMUNE_ID` (`COMMUNE_ID`);

--
-- AUTO_INCREMENT pour les tables exportées
--

--
-- AUTO_INCREMENT pour la table `commune`
--
ALTER TABLE `commune`
  MODIFY `ID` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT pour la table `device`
--
ALTER TABLE `device`
  MODIFY `ID` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT pour la table `history`
--
ALTER TABLE `history`
  MODIFY `ID` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=47;
--
-- AUTO_INCREMENT pour la table `journal`
--
ALTER TABLE `journal`
  MODIFY `ID` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT pour la table `quartier`
--
ALTER TABLE `quartier`
  MODIFY `ID` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT pour la table `redevable`
--
ALTER TABLE `redevable`
  MODIFY `ID` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT pour la table `rue`
--
ALTER TABLE `rue`
  MODIFY `ID` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT pour la table `secteur`
--
ALTER TABLE `secteur`
  MODIFY `ID` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT pour la table `tauxretardboisontrim`
--
ALTER TABLE `tauxretardboisontrim`
  MODIFY `ID` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT pour la table `taxetrimboisson`
--
ALTER TABLE `taxetrimboisson`
  MODIFY `ID` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
