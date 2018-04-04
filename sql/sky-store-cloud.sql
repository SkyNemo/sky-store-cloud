DROP DATABASE IF EXISTS store;
CREATE DATABASE store DEFAULT CHARACTER SET utf8;
USE store;

-- ----------------------------
-- Table structure for user
-- 用户表
-- ----------------------------
CREATE TABLE user (
  id int(11) NOT NULL AUTO_INCREMENT,
  name varchar(255) DEFAULT NULL COMMENT '用户名',
  password varchar(255) DEFAULT NULL COMMENT '密码，加密存储',
  phone varchar(255) DEFAULT NULL COMMENT '注册手机号',
  email varchar(255) DEFAULT NULL COMMENT '注册邮箱',
  PRIMARY KEY (id),
  UNIQUE KEY `name` (`name`) USING BTREE,
  UNIQUE KEY `phone` (`phone`) USING BTREE,
  UNIQUE KEY `email` (`email`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 



-- ----------------------------
-- Table structure for category
-- 分类表
-- ----------------------------
CREATE TABLE category (
  id int(11) NOT NULL AUTO_INCREMENT,
  name varchar(255) DEFAULT NULL COMMENT '分类名',
  PRIMARY KEY (id)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;
 
-- ----------------------------
-- Table structure for property
-- 属性表
-- ----------------------------
CREATE TABLE property (
  id int(11) NOT NULL AUTO_INCREMENT,
  cid int(11) DEFAULT NULL COMMENT '分类表id',
  name varchar(255) DEFAULT NULL,
  PRIMARY KEY (id),
  KEY cid (cid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for product
-- 产品表
-- ----------------------------
CREATE TABLE product (
  id int(11) NOT NULL AUTO_INCREMENT,
  name varchar(255) DEFAULT NULL COMMENT '产品名',
  subTitle varchar(255) DEFAULT NULL COMMENT '小标题',
  originalPrice float DEFAULT NULL COMMENT '原始价格',
  promotePrice float DEFAULT NULL COMMENT '优惠价格',
  stock int(11) DEFAULT NULL COMMENT '库存',
  cid int(11) DEFAULT NULL COMMENT '分类表id',
  createDate datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (id),
  KEY cid (cid)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for propertyvalue
-- 产品属性值表
-- ---------------------------- 
CREATE TABLE propertyvalue (
  id int(11) NOT NULL AUTO_INCREMENT,
  pid int(11) DEFAULT NULL COMMENT '产品表id',
  ptid int(11) DEFAULT NULL COMMENT '属性表id',
  value varchar(255) DEFAULT NULL COMMENT '属性值',
  PRIMARY KEY (id),
  KEY ptid (ptid),
  KEY pid (pid)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;


 -- ----------------------------
-- Table structure for productimage
-- 产品图片表
-- ---------------------------- 
CREATE TABLE productimage (
  id int(11) NOT NULL AUTO_INCREMENT,
  pid int(11) DEFAULT NULL COMMENT '产品表id',
  type varchar(255) DEFAULT NULL COMMENT '图片类型 分:单个图片和详情图片',
  PRIMARY KEY (id),
  KEY pid (pid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



-- ----------------------------
-- Table structure for review
-- 评论表
-- ---------------------------- 
CREATE TABLE review (
  id int(11) NOT NULL AUTO_INCREMENT,
  content varchar(4000) DEFAULT NULL COMMENT '评论内容',
  uid int(11) DEFAULT NULL COMMENT '用户表id',
  pid int(11) DEFAULT NULL COMMENT '产品表id',
  createDate datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (id),
  KEY pid (pid),
  KEY uid (uid) 
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;



-- ----------------------------
-- Table structure for order_
-- 订单表
-- ---------------------------- 
CREATE TABLE order_ (
  id int(11) NOT NULL AUTO_INCREMENT,
  orderCode varchar(255) DEFAULT NULL COMMENT '订单号',
  address varchar(255) DEFAULT NULL COMMENT '收货地址',
  post varchar(255) DEFAULT NULL COMMENT '邮编',
  receiver varchar(255) DEFAULT NULL COMMENT '收货人信息',
  mobile varchar(255) DEFAULT NULL COMMENT '手机号码',
  userMessage varchar(255) DEFAULT NULL COMMENT '用户备注信息',
  createDate datetime DEFAULT NULL COMMENT '订单创建日期',
  payDate datetime DEFAULT NULL COMMENT '支付日期',
  deliveryDate datetime DEFAULT NULL COMMENT '发货日期',
  confirmDate datetime DEFAULT NULL COMMENT '确认收货日期',
  uid int(11) DEFAULT NULL COMMENT '用户表id',
  status varchar(255) DEFAULT NULL COMMENT '订单状态',
  PRIMARY KEY (id),
  KEY uid(uid)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for orderitem
-- 订单项表
-- ---------------------------- 
CREATE TABLE orderitem (
  id int(11) NOT NULL AUTO_INCREMENT,
  pid int(11) DEFAULT NULL COMMENT '产品表id',
  oid int(11) DEFAULT NULL COMMENT '订单表id',
  uid int(11) DEFAULT NULL COMMENT '用户表id',
  number int(11) DEFAULT NULL COMMENT '购买数量',
  PRIMARY KEY (id),
  KEY uid (uid),
  KEY pid (pid),
  KEY oid(oid)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;



