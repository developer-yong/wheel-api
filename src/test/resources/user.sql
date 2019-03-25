/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80013
 Source Host           : localhost:3306
 Source Schema         : demo

 Target Server Type    : MySQL
 Target Server Version : 80013
 File Encoding         : 65001

 Date: 25/03/2019 09:39:19
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` varchar(11) COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户ID',
  `nickname` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '昵称',
  `password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '123456' COMMENT '密码',
  `icon` blob COMMENT '头像',
  `age` int(4) DEFAULT '0' COMMENT '年龄',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `introduction` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '简介',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户表';

-- ----------------------------
-- Records of user
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES ('0', 'Admin', '123456', NULL, 0, '1970-01-01', NULL);
INSERT INTO `user` VALUES ('1', '张三', '123456', NULL, 26, '2019-03-25', NULL);
INSERT INTO `user` VALUES ('2', '李四', '123456', NULL, 25, '2018-10-09', NULL);
INSERT INTO `user` VALUES ('3', '王五', '123456', NULL, 27, '2019-03-12', NULL);
INSERT INTO `user` VALUES ('4', '赵六', '123456', NULL, 30, '2019-03-20', NULL);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
