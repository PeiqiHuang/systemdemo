-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jan 24, 2017 at 08:09 AM
-- Server version: 5.6.17
-- PHP Version: 5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `systemdemo`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin_menu`
--

CREATE TABLE IF NOT EXISTS `admin_menu` (
  `menuid` int(30) NOT NULL AUTO_INCREMENT,
  `menuname` varchar(50) DEFAULT NULL,
  `linkaddress` varchar(100) DEFAULT NULL,
  `menutype` int(3) DEFAULT NULL,
  `menuicon` varchar(50) DEFAULT NULL,
  `sortnum` int(3) DEFAULT NULL,
  `parentid` int(30) DEFAULT NULL,
  `createtime` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`menuid`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `admin_menu`
--

INSERT INTO `admin_menu` (`menuid`, `menuname`, `linkaddress`, `menutype`, `menuicon`, `sortnum`, `parentid`, `createtime`) VALUES
(1, '系统管理', NULL, 1, 'icon-nav-user', 0, 0, '2017-01-22 07:13:18'),
(2, '用户管理', '/admin/auth/userList.do', 2, 'icon-nav-user', 1, 1, '2017-01-22 07:18:09'),
(3, '权限管理', '/admin/auth/menu/menuList.do', 2, NULL, 3, 1, '2017-01-23 00:23:30'),
(4, '角色管理', '/admin/auth/authRoleList.do', 2, 'icon-nav-user', 2, 1, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `admin_role`
--

CREATE TABLE IF NOT EXISTS `admin_role` (
  `roleid` varchar(36) NOT NULL,
  `rolename` varchar(100) DEFAULT NULL,
  `remark` varchar(500) DEFAULT NULL,
  `createtime` timestamp NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `admin_role`
--

INSERT INTO `admin_role` (`roleid`, `rolename`, `remark`, `createtime`) VALUES
('ROLEe0d76285ab8d4acb8f2f18a02d23200b', '运营者', '运营者', '2017-01-22 16:27:32');

-- --------------------------------------------------------

--
-- Table structure for table `admin_roleright`
--

CREATE TABLE IF NOT EXISTS `admin_roleright` (
  `rrid` varchar(36) NOT NULL,
  `roleid` varchar(36) DEFAULT NULL,
  `menuid` varchar(36) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `admin_roleright`
--

INSERT INTO `admin_roleright` (`rrid`, `roleid`, `menuid`) VALUES
('RRID10406710c8b54c35aa5d89fc83de1319', 'ROLEe0d76285ab8d4acb8f2f18a02d23200b', '1'),
('RRID79339644095b426cbf2a5cf6f76af555', 'ROLEe0d76285ab8d4acb8f2f18a02d23200b', '2');

-- --------------------------------------------------------

--
-- Table structure for table `admin_sysuser`
--

CREATE TABLE IF NOT EXISTS `admin_sysuser` (
  `suId` int(30) NOT NULL AUTO_INCREMENT,
  `loginName` varchar(50) DEFAULT NULL,
  `loginPwd` varchar(100) DEFAULT NULL,
  `createTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `realName` varchar(50) DEFAULT NULL,
  `status` int(1) DEFAULT '0',
  `modifier` varchar(50) DEFAULT '0',
  `modifierTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`suId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `admin_sysuser`
--

INSERT INTO `admin_sysuser` (`suId`, `loginName`, `loginPwd`, `createTime`, `realName`, `status`, `modifier`, `modifierTime`) VALUES
(1, 'appAdmin', '8506ae7885795745792fdc30df42eb8d', '2017-01-22 06:35:43', '超级管理员', 0, '0', '2017-01-22 06:35:43'),
(2, 'huangpeiqi', '294d17c910c8e1a88f10998725ad3b2f', '2017-01-22 01:03:01', '黄沛其', 0, '{suId:''1'',loginName:''appAdmin''}', '2017-01-22 01:03:01');

-- --------------------------------------------------------

--
-- Table structure for table `admin_userright`
--

CREATE TABLE IF NOT EXISTS `admin_userright` (
  `urid` varchar(36) NOT NULL,
  `userid` varchar(36) DEFAULT NULL,
  `menuid` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`urid`),
  KEY `userid` (`userid`),
  KEY `menuid` (`menuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `admin_userright`
--

INSERT INTO `admin_userright` (`urid`, `userid`, `menuid`) VALUES
('URID5ae8baf4e11011e684d3c85b767fa088', '2', '1'),
('URID5aea707fe11011e684d3c85b767fa088', '2', '2');

-- --------------------------------------------------------

--
-- Table structure for table `admin_userrole`
--

CREATE TABLE IF NOT EXISTS `admin_userrole` (
  `urid` varchar(36) NOT NULL,
  `userid` varchar(36) DEFAULT NULL,
  `roleid` varchar(36) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `admin_userrole`
--

INSERT INTO `admin_userrole` (`urid`, `userid`, `roleid`) VALUES
('URID53ae3e078eff482eaebcd691811ccdba', '2', 'ROLEe0d76285ab8d4acb8f2f18a02d23200b');

-- --------------------------------------------------------

--
-- Table structure for table `person`
--

CREATE TABLE IF NOT EXISTS `person` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `age` int(11) NOT NULL,
  `gender` int(11) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `person`
--

INSERT INTO `person` (`id`, `name`, `age`, `gender`, `createtime`) VALUES
(1, '小明', 12, 1, '2017-01-23 07:06:15');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
