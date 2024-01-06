-- phpMyAdmin SQL Dump
-- version 4.0.10.1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: May 15, 2021 at 11:58 AM
-- Server version: 5.5.68-MariaDB
-- PHP Version: 5.4.16

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `mysite_multiplayergame`
--

-- --------------------------------------------------------

--
-- Table structure for table `achievements`
--

CREATE TABLE IF NOT EXISTS `achievements` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(45) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  `money_earned` int(11) DEFAULT NULL,
  `how_to_unlock` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='This is for the Time Trial Data' AUTO_INCREMENT=3 ;

-- --------------------------------------------------------

--
-- Table structure for table `bugs`
--

CREATE TABLE IF NOT EXISTS `bugs` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `player_email` varchar(30) NOT NULL,
  `bug_subject` varchar(30) NOT NULL,
  `bug_description` varchar(120) NOT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `priority` tinyint(4) NOT NULL DEFAULT '127',
  `date_submitted` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=6 ;

-- --------------------------------------------------------

--
-- Table structure for table `career_mode_stats`
--

CREATE TABLE IF NOT EXISTS `career_mode_stats` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `users_id` int(11) NOT NULL,
  `career_opponent_id` int(11) NOT NULL,
  `userTime` varchar(20) NOT NULL DEFAULT '00:00:00',
  `speed` int(10) NOT NULL,
  `did_win` tinyint(4) DEFAULT NULL,
  `cdate` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_time_trials_users1_idx` (`users_id`),
  KEY `fk_career_mode_stats_career_opponent1_idx` (`career_opponent_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='This is for the Time Trial Data' AUTO_INCREMENT=117 ;

-- --------------------------------------------------------

--
-- Table structure for table `career_opponent`
--

CREATE TABLE IF NOT EXISTS `career_opponent` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `description` varchar(100) NOT NULL,
  `speed` int(11) DEFAULT NULL,
  `bracket` int(10) NOT NULL DEFAULT '1',
  `money` int(10) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='This is for the Time Trial Data' AUTO_INCREMENT=17 ;

--
-- Dumping data for table `career_opponent`
--

INSERT INTO `career_opponent` (`id`, `name`, `description`, `speed`, `bracket`, `money`) VALUES
(1, 'Billy Bob', '1st Opponent', 5, 1, 25),
(2, 'Bobbie Sue', '2nd Opponent', 10, 1, 30),
(3, 'Jim', '3rd Opponent', 15, 1, 75),
(4, 'Just A Guy', '4th Opponent', 20, 1, 100),
(5, 'Musher', '5th Opponent', 30, 2, 150),
(6, 'Jenny', '6th Opponent', 35, 2, 200),
(7, 'Tip-Toes', '7th Opponent', 40, 2, 275),
(8, 'The Almost Professional', '8th Opponent', 45, 2, 350),
(9, 'Mars the Quick', '9th Opponent', 50, 3, 450),
(10, 'Joey', '10th Opponent', 55, 3, 500),
(11, 'Miko', '11th Opponent', 60, 3, 410),
(12, 'Todd', '12th Opponent', 65, 3, 420),
(13, 'K-Dog', '13th Opponent', 75, 4, 475),
(14, 'Darekts', '14th Opponent', 80, 4, 540),
(15, 'FB9', '15th Opponent', 90, 4, 750),
(16, 'The Universal G.O.A.T. ', 'Final Opponent', 100, 4, 1000);

-- --------------------------------------------------------

--
-- Table structure for table `player_achievements`
--

CREATE TABLE IF NOT EXISTS `player_achievements` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `achievements_id` int(11) NOT NULL,
  `users_id` int(11) NOT NULL,
  `date_unlocked` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_player_achievements_achievements1_idx` (`achievements_id`),
  KEY `fk_player_achievements_users1_idx` (`users_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='This is for the Time Trial Data' AUTO_INCREMENT=3 ;

-- --------------------------------------------------------

--
-- Table structure for table `time_trials`
--

CREATE TABLE IF NOT EXISTS `time_trials` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `users_id` int(11) NOT NULL,
  `userTime` varchar(20) NOT NULL,
  `speed` int(10) NOT NULL,
  `cdate` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_time_trials_users1_idx` (`users_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='This is for the Time Trial Data' AUTO_INCREMENT=151 ;

-- --------------------------------------------------------

--
-- Table structure for table `upgrades`
--

CREATE TABLE IF NOT EXISTS `upgrades` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `effect_name` varchar(25) NOT NULL,
  `img_loc` text NOT NULL,
  `active` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=7 ;

--
-- Dumping data for table `upgrades`
--

INSERT INTO `upgrades` (`id`, `effect_name`, `img_loc`, `active`) VALUES
(1, 'Tires', '', 1),
(2, 'Weight Reduction', '', 1),
(3, 'Aerodynamics', '', 1),
(4, 'Transmission', '', 1),
(5, 'Engine', '', 1);

-- --------------------------------------------------------

--
-- Table structure for table `upgrades_details`
--

CREATE TABLE IF NOT EXISTS `upgrades_details` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `upgrades_id` int(10) NOT NULL,
  `lvl1_effect` varchar(25) NOT NULL,
  `lvl1_amount` int(10) NOT NULL,
  `lvl1_cost` int(10) NOT NULL,
  `lvl2_effect` varchar(25) NOT NULL,
  `lvl2_amount` int(10) NOT NULL,
  `lvl2_cost` int(10) NOT NULL,
  `lvl3_effect` varchar(25) NOT NULL,
  `lvl3_amount` int(10) NOT NULL,
  `lvl3_cost` int(10) NOT NULL,
  `lvl4_effect` varchar(25) NOT NULL,
  `lvl4_amount` int(10) NOT NULL,
  `lvl4_cost` int(10) NOT NULL,
  `lvl5_effect` varchar(25) NOT NULL,
  `lvl5_amount` varchar(10) NOT NULL,
  `lvl5_cost` int(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=6 ;

--
-- Dumping data for table `upgrades_details`
--

INSERT INTO `upgrades_details` (`id`, `upgrades_id`, `lvl1_effect`, `lvl1_amount`, `lvl1_cost`, `lvl2_effect`, `lvl2_amount`, `lvl2_cost`, `lvl3_effect`, `lvl3_amount`, `lvl3_cost`, `lvl4_effect`, `lvl4_amount`, `lvl4_cost`, `lvl5_effect`, `lvl5_amount`, `lvl5_cost`) VALUES
(1, 1, 'Junkyard Tires', 1, 0, 'Standard Tires', 2, 450, 'Slick Tires', 3, 700, 'Racing Tires', 4, 1200, 'Elite Racing Tires', '6', 1500),
(2, 2, 'Industrial Steel', 1, 0, 'Lightweight Steel', 2, 500, 'Aluminum', 3, 850, 'Lightweight Alloy', 4, 1300, 'Carbon Fiber Body', '6', 1700),
(3, 3, 'The Brick', 1, 0, 'The Back Drag', 3, 750, 'Standard Spoiler', 4, 950, 'Super Spoiler', 5, 1500, 'God''s Wing', '7', 1950),
(4, 4, 'Direct Driver', 1, 0, 'The Missing Gears', 4, 950, 'Standard Manual', 5, 1450, '5 Speed Manual', 6, 2000, 'Super Shifter ', '8', 3000),
(5, 5, 'Lawn & Order', 1, 0, '2.0L V4', 4, 1050, '3.5L V8', 6, 1600, 'V12 Guzzler', 7, 2300, 'V24 Tank Killer', '9', 5000);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `password` varchar(50) NOT NULL,
  `email` text NOT NULL,
  `racing_no` int(10) NOT NULL,
  `money` int(10) NOT NULL DEFAULT '500',
  `date_created` date NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='user data' AUTO_INCREMENT=45 ;

-- --------------------------------------------------------

--
-- Table structure for table `user_upgrades`
--

CREATE TABLE IF NOT EXISTS `user_upgrades` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `users_id` int(11) NOT NULL,
  `upgrades_id` int(10) NOT NULL,
  `upgrades_details_id` int(10) NOT NULL,
  `current_upgrade_level` int(11) NOT NULL,
  `active` tinyint(2) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `fk_user_upgrades_users_idx` (`users_id`),
  KEY `fk_user_upgrades_upgrades1_idx` (`upgrades_details_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=305 ;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `career_mode_stats`
--
ALTER TABLE `career_mode_stats`
  ADD CONSTRAINT `fk_career_mode_stats_career_opponent1` FOREIGN KEY (`career_opponent_id`) REFERENCES `career_opponent` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_time_trials_users10` FOREIGN KEY (`users_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `player_achievements`
--
ALTER TABLE `player_achievements`
  ADD CONSTRAINT `fk_player_achievements_achievements1` FOREIGN KEY (`achievements_id`) REFERENCES `achievements` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_player_achievements_users1` FOREIGN KEY (`users_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `time_trials`
--
ALTER TABLE `time_trials`
  ADD CONSTRAINT `fk_time_trials_users1` FOREIGN KEY (`users_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `user_upgrades`
--
ALTER TABLE `user_upgrades`
  ADD CONSTRAINT `fk_user_upgrades_users` FOREIGN KEY (`users_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
