/*
Navicat MySQL Data Transfer

Source Server         : study1
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : forum

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2019-06-21 01:43:39
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
-- Table structure for class_task
-- ----------------------------
DROP TABLE IF EXISTS `class_task`;
CREATE TABLE `class_task` (
  `id` varchar(255) NOT NULL,
  `classid` varchar(255) NOT NULL COMMENT '班级id',
  `name` varchar(255) NOT NULL COMMENT '任务名',
  `info` varchar(255) NOT NULL COMMENT '任务信息',
  `type` varchar(255) NOT NULL COMMENT '班级任务/教师任务',
  `endtime` datetime NOT NULL COMMENT '截至时间',
  `operator` varchar(255) NOT NULL,
  `createtime` datetime NOT NULL,
  `updatetime` datetime NOT NULL,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of class_task
-- ----------------------------

-- ----------------------------
-- Table structure for custom
-- ----------------------------
DROP TABLE IF EXISTS `custom`;
CREATE TABLE `custom` (
  `id` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `power` varchar(255) NOT NULL,
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
INSERT INTO `custom` VALUES ('15586219479869', '13067272181', '0', '123456', '000000', '2019-05-23 00:00:00', '2019-05-23 00:00:00', '55');
INSERT INTO `custom` VALUES ('15586219559692', '1306727218', '1', '123456', '000000', '2019-05-23 00:00:00', '2019-05-23 00:00:00', '1');

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
-- Table structure for favorite
-- ----------------------------
DROP TABLE IF EXISTS `favorite`;
CREATE TABLE `favorite` (
  `id` varchar(255) NOT NULL,
  `userid` varchar(255) NOT NULL,
  `postslist` varchar(255) NOT NULL,
  `operator` varchar(255) NOT NULL,
  `createtime` datetime NOT NULL,
  `updatetime` datetime NOT NULL,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of favorite
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
  `number` int(255) NOT NULL,
  `readnum` int(255) NOT NULL,
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
INSERT INTO `posts` VALUES ('15610468723601', '15586219479869', '发帖测试', 'java', '这是一个测试的发帖', '0', '0', '1', '15586219479869', '2019-06-20 16:07:52', '2019-06-20 16:07:52', null);
INSERT INTO `posts` VALUES ('15610468827172', '15586219479869', '发帖测试', 'java', '这是一个测试的发帖', '0', '0', '1', '15586219479869', '2019-06-20 16:08:03', '2019-06-20 16:08:03', null);
INSERT INTO `posts` VALUES ('15610468835471', '15586219479869', '发帖测试', 'java', '这是一个测试的发帖', '0', '0', '1', '15586219479869', '2019-06-20 16:08:04', '2019-06-20 16:08:04', null);
INSERT INTO `posts` VALUES ('15610468844162', '15586219479869', '发帖测试', 'java', '这是一个测试的发帖', '0', '0', '1', '15586219479869', '2019-06-20 16:08:04', '2019-06-20 16:08:04', null);
INSERT INTO `posts` VALUES ('15610468849348', '15586219479869', '发帖测试', 'java', '这是一个测试的发帖', '0', '0', '1', '15586219479869', '2019-06-20 16:08:05', '2019-06-20 16:08:05', null);
INSERT INTO `posts` VALUES ('15610468854515', '15586219479869', '发帖测试', 'java', '这是一个测试的发帖', '0', '0', '1', '15586219479869', '2019-06-20 16:08:05', '2019-06-20 16:08:05', null);
INSERT INTO `posts` VALUES ('15610468858932', '15586219479869', '发帖测试', 'java', '这是一个测试的发帖', '3', '0', '1', '15586219479869', '2019-06-20 16:08:06', '2019-06-20 16:08:06', null);
INSERT INTO `posts` VALUES ('15610469489299', '15586219479869', '发帖测试', 'java', '这是一个测试的发帖', '0', '0', '1', '15586219479869', '2019-06-20 16:09:09', '2019-06-20 16:09:09', null);
INSERT INTO `posts` VALUES ('15610469496825', '15586219479869', '发帖测试', 'java', '这是一个测试的发帖', '0', '0', '1', '15586219479869', '2019-06-20 16:09:10', '2019-06-20 16:09:10', null);
INSERT INTO `posts` VALUES ('15610469503463', '15586219479869', '发帖测试', 'java', '这是一个测试的发帖', '0', '0', '1', '15586219479869', '2019-06-20 16:09:10', '2019-06-20 16:09:10', null);
INSERT INTO `posts` VALUES ('15610469507743', '15586219479869', '发帖测试', 'java', '这是一个测试的修改帖子', '2', '1', '1', '15586219479869', '2019-06-20 16:09:11', '2019-06-20 16:14:37', null);
INSERT INTO `posts` VALUES ('15610475270537', '15586219479869', '发帖测试', 'html', '这是一个测试的发帖', '0', '0', '1', '15586219479869', '2019-06-20 16:18:47', '2019-06-20 16:18:47', null);
INSERT INTO `posts` VALUES ('15610475297965', '15586219479869', '发帖测试', 'html', '这是一个测试的发帖', '0', '0', '1', '15586219479869', '2019-06-20 16:18:50', '2019-06-20 16:18:50', null);

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
INSERT INTO `reply` VALUES ('15610471718747', '15610469507743', '15586219479869', '回复测试', '2', '15586219479869', '2019-06-20 16:12:52', '2019-06-20 16:12:52', null);

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
  `birth` datetime NOT NULL,
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
INSERT INTO `user_info` VALUES ('15586219479869', '15586219479869', '测试账号1', '0', '2019-05-23 00:00:00', '测试', '福州', '15586219479869', '2019-05-23 22:54:07', '2019-05-23 22:54:09', '');

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
