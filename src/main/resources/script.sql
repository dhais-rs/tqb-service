CREATE TABLE `user` (
    `id` int(11) NOT NULL,
    `nick_name` varchar(50) DEFAULT NULL COMMENT '用户名称',
    `user_name` varchar(50) DEFAULT NULL COMMENT '用户名',
    `password` varchar(100) DEFAULT NULL COMMENT '密码',
    `state` smallint(6) DEFAULT NULL COMMENT '状态 0启用 1删除',
    `creator` varchar(50) DEFAULT NULL COMMENT '创建人',
    `create_by` varchar(50) DEFAULT NULL COMMENT '创建人id',
    `create_date` datetime DEFAULT NULL COMMENT '创建时间',
    `updater` varchar(50) DEFAULT NULL COMMENT '更新人',
    `udpate_by` varchar(50) DEFAULT NULL COMMENT '更新人id',
    `update_date` datetime DEFAULT NULL COMMENT '更新时间',
    `user_level` smallint(6) DEFAULT NULL COMMENT '用户等级 0超级管理员 1管理员',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

CREATE TABLE `banner` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `banner_name` varchar(128) DEFAULT NULL COMMENT '轮播图名称',
    `url` varchar(512) DEFAULT NULL COMMENT '地址',
    `status` smallint(6) DEFAULT '0' COMMENT '状态 0停用 1启用',
    `creator` varchar(50) DEFAULT NULL COMMENT '创建人',
    `create_by` varchar(50) DEFAULT NULL COMMENT '创建人账号',
    `create_date` datetime DEFAULT NULL COMMENT '创建时间',
    `updater` varchar(50) DEFAULT NULL COMMENT '更新人',
    `update_by` varchar(50) DEFAULT NULL COMMENT '更新人编码',
    `update_date` datetime DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;