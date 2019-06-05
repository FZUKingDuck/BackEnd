/*
Navicat MySQL Data Transfer

Source Server         : study1
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : forum

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2019-06-05 17:49:41
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for class_info
-- ----------------------------
DROP TABLE IF EXISTS `class_info`;
CREATE TABLE `class_info` (
  `id` varchar(255) NOT NULL,
  `teacher` varchar(255) NOT NULL,
  `type` varchar(255) NOT NULL,
  `info` varchar(255) NOT NULL,
  `operator` varchar(255) NOT NULL,
  `creattime` datetime NOT NULL,
  `updatetime` datetime NOT NULL,
  `Remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_class_info_custom` (`teacher`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of class_info
-- ----------------------------

-- ----------------------------
-- Table structure for class_member
-- ----------------------------
DROP TABLE IF EXISTS `class_member`;
CREATE TABLE `class_member` (
  `id` varchar(255) NOT NULL,
  `classid` varchar(255) NOT NULL,
  `user` varchar(255) NOT NULL,
  `status` int(11) NOT NULL,
  `operator` varchar(255) NOT NULL,
  `creattime` datetime NOT NULL,
  `updatetime` datetime NOT NULL,
  `Remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_class_member_class_info` (`classid`),
  KEY `fk_class_member_custom` (`user`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of class_member
-- ----------------------------

-- ----------------------------
-- Table structure for custom
-- ----------------------------
DROP TABLE IF EXISTS `custom`;
CREATE TABLE `custom` (
  `id` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `operator` varchar(255) NOT NULL,
  `creattime` datetime NOT NULL,
  `updatetime` datetime NOT NULL,
  `Remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of custom
-- ----------------------------
INSERT INTO `custom` VALUES ('15586219479869', '13067272181', '123456', '000000', '2019-05-23 00:00:00', '2019-05-23 00:00:00', '55');
INSERT INTO `custom` VALUES ('15586219559692', '1306727218', '123456', '000000', '2019-05-23 00:00:00', '2019-05-23 00:00:00', '1');

-- ----------------------------
-- Table structure for exam
-- ----------------------------
DROP TABLE IF EXISTS `exam`;
CREATE TABLE `exam` (
  `id` varchar(255) NOT NULL,
  `classid` varchar(255) NOT NULL,
  `title` varchar(255) NOT NULL,
  `info` varchar(255) NOT NULL,
  `endtime` date NOT NULL,
  `operator` varchar(255) NOT NULL,
  `creattime` datetime NOT NULL,
  `updatetime` datetime NOT NULL,
  `Remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_exam_classid_class_info` (`classid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of exam
-- ----------------------------

-- ----------------------------
-- Table structure for exam_info
-- ----------------------------
DROP TABLE IF EXISTS `exam_info`;
CREATE TABLE `exam_info` (
  `id` varchar(255) NOT NULL,
  `examid` varchar(255) NOT NULL,
  `user` varchar(255) NOT NULL,
  `score` int(11) NOT NULL,
  `operator` varchar(255) NOT NULL,
  `creattime` datetime NOT NULL,
  `updatetime` datetime NOT NULL,
  `Remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_exam_info_exam` (`examid`),
  KEY `fk_exam_info_custom` (`user`),
  CONSTRAINT `fk_exam_info_custom` FOREIGN KEY (`user`) REFERENCES `custom` (`id`),
  CONSTRAINT `fk_exam_info_exam` FOREIGN KEY (`examid`) REFERENCES `exam` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of exam_info
-- ----------------------------

-- ----------------------------
-- Table structure for friend
-- ----------------------------
DROP TABLE IF EXISTS `friend`;
CREATE TABLE `friend` (
  `id` varchar(255) NOT NULL,
  `user` varchar(255) NOT NULL,
  `friends` varchar(255) NOT NULL,
  `operator` varchar(255) NOT NULL,
  `creattime` datetime NOT NULL,
  `updatetime` datetime NOT NULL,
  `Remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_friend_custom1` (`user`),
  KEY `fk_friend_custom2` (`friends`),
  KEY `fk_friend_custom` (`operator`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of friend
-- ----------------------------

-- ----------------------------
-- Table structure for image_info
-- ----------------------------
DROP TABLE IF EXISTS `image_info`;
CREATE TABLE `image_info` (
  `id` varchar(255) NOT NULL,
  `url` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of image_info
-- ----------------------------
INSERT INTO `image_info` VALUES ('000001', '123465.jpg');

-- ----------------------------
-- Table structure for posts
-- ----------------------------
DROP TABLE IF EXISTS `posts`;
CREATE TABLE `posts` (
  `id` varchar(255) NOT NULL,
  `userid` varchar(255) NOT NULL,
  `title` varchar(255) NOT NULL,
  `type` varchar(255) NOT NULL,
  `info` varchar(255) NOT NULL,
  `authority` varchar(255) NOT NULL,
  `operator` varchar(255) NOT NULL,
  `creattime` datetime NOT NULL,
  `updatetime` datetime NOT NULL,
  `Remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_posts_user_info` (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of posts
-- ----------------------------
INSERT INTO `posts` VALUES ('15586232803646', '15586219479869', '测试1', 'html', '这是一个测试的数据', '1', '15586219479869', '2019-05-23 00:00:00', '2019-05-23 00:00:00', '1');
INSERT INTO `posts` VALUES ('15586232834672', '15586219479869', '测试1', 'html', '这是一个测试的数据', '1', '15586219479869', '2019-05-23 00:00:00', '2019-05-23 00:00:00', '2');
INSERT INTO `posts` VALUES ('15586232840175', '15586219479869', '测试1', 'html', '这是一个测试的数据', '1', '15586219479869', '2019-05-23 00:00:00', '2019-05-23 00:00:00', '3');
INSERT INTO `posts` VALUES ('15586232845788', '15586219479869', '测试1', 'html', '这是一个测试的数据', '1', '15586219479869', '2019-05-23 00:00:00', '2019-05-23 00:00:00', '4');
INSERT INTO `posts` VALUES ('15586232851232', '15586219479869', '测试1', 'html', '这是一个测试的数据', '1', '15586219479869', '2019-05-23 00:00:00', '2019-05-23 00:00:00', '5');
INSERT INTO `posts` VALUES ('15586241027903', '15586219479869', '测试1', 'html', '这是一个测试的数据', '1', '15586219479869', '2019-05-23 00:00:00', '2019-05-23 00:00:00', '6');
INSERT INTO `posts` VALUES ('15586241035317', '15586219479869', '测试1', 'html', '这是一个测试的数据', '1', '15586219479869', '2019-05-23 00:00:00', '2019-05-23 00:00:00', '7');
INSERT INTO `posts` VALUES ('15586241042149', '15586219479869', '测试1', 'html', '这是一个测试的数据', '1', '15586219479869', '2019-05-23 00:00:00', '2019-05-23 00:00:00', '8');
INSERT INTO `posts` VALUES ('15586241047592', '15586219479869', '测试1', 'html', '这是一个测试的数据', '1', '15586219479869', '2019-05-23 00:00:00', '2019-05-23 00:00:00', '9');
INSERT INTO `posts` VALUES ('15586241052832', '15586219479869', '测试1', 'html', '这是一个测试的数据', '1', '15586219479869', '2019-05-23 00:00:00', '2019-05-23 00:00:00', '0');

-- ----------------------------
-- Table structure for reply
-- ----------------------------
DROP TABLE IF EXISTS `reply`;
CREATE TABLE `reply` (
  `id` varchar(255) NOT NULL,
  `postsid` varchar(255) NOT NULL,
  `user` varchar(255) NOT NULL,
  `info` varchar(255) NOT NULL,
  `top` int(11) NOT NULL,
  `operator` varchar(255) NOT NULL,
  `creattime` datetime NOT NULL,
  `updatetime` datetime NOT NULL,
  `Remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_reply_posts` (`postsid`),
  KEY `fk_reply_custom` (`user`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of reply
-- ----------------------------

-- ----------------------------
-- Table structure for talk
-- ----------------------------
DROP TABLE IF EXISTS `talk`;
CREATE TABLE `talk` (
  `id` varchar(255) NOT NULL,
  `user` varchar(255) NOT NULL,
  `to_userid` varchar(255) NOT NULL,
  `info` varchar(255) NOT NULL,
  `operator` varchar(255) NOT NULL,
  `creattime` datetime NOT NULL,
  `updatetime` datetime NOT NULL,
  `Remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_talk_custom1` (`user`),
  KEY `fk_talk_custom2` (`to_userid`),
  KEY `fk_talk_custom3` (`operator`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of talk
-- ----------------------------

-- ----------------------------
-- Table structure for task
-- ----------------------------
DROP TABLE IF EXISTS `task`;
CREATE TABLE `task` (
  `id` varchar(255) NOT NULL,
  `user` varchar(255) NOT NULL,
  `type` varchar(255) NOT NULL,
  `title` varchar(255) NOT NULL,
  `status` varchar(255) NOT NULL,
  `money` int(11) NOT NULL,
  `info` varchar(255) DEFAULT NULL,
  `endtime` datetime DEFAULT NULL,
  `operator` varchar(255) NOT NULL,
  `creattime` datetime NOT NULL,
  `updatetime` datetime NOT NULL,
  `Remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_task_custom2` (`operator`),
  KEY `fk_task_custom` (`user`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of task
-- ----------------------------

-- ----------------------------
-- Table structure for task_list
-- ----------------------------
DROP TABLE IF EXISTS `task_list`;
CREATE TABLE `task_list` (
  `id` varchar(255) NOT NULL,
  `user` varchar(255) NOT NULL,
  `taskid` varchar(255) NOT NULL,
  `status` varchar(255) NOT NULL,
  `operator` varchar(255) NOT NULL,
  `creattime` date NOT NULL,
  `updatetime` date NOT NULL,
  `Remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_task_list_task` (`taskid`),
  KEY `fk_task_list_custom2` (`operator`),
  KEY `fk_task_list_custom` (`user`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of task_list
-- ----------------------------

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `id` varchar(255) NOT NULL,
  `customid` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `sex` int(11) NOT NULL,
  `birth` date NOT NULL,
  `signature` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `operator` varchar(255) NOT NULL,
  `creattime` datetime NOT NULL,
  `updatetime` datetime NOT NULL,
  `Remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_user_info_custom` (`customid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES ('15586219479869', '15586219479869', '测试账号1', '0', '2019-05-23', '测试', '福州', '15586219479869', '2019-05-23 22:54:07', '2019-05-23 22:54:09', '');

-- ----------------------------
-- Table structure for watched
-- ----------------------------
DROP TABLE IF EXISTS `watched`;
CREATE TABLE `watched` (
  `id` varchar(255) NOT NULL,
  `user` varchar(255) NOT NULL,
  `watched_user` varchar(255) NOT NULL,
  `operator` varchar(255) NOT NULL,
  `creattime` datetime NOT NULL,
  `updatetime` datetime NOT NULL,
  `Remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_watched_custom1` (`user`),
  KEY `fk_watched_custom2` (`watched_user`),
  KEY `fk_watched_custom3` (`operator`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of watched
-- ----------------------------
