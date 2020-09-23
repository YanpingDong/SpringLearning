SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables */
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS image;

/* Create Tables */
CREATE TABLE user
(
    user_id int NOT NULL COMMENT '用户id',
    user_name varchar(255) NOT NULL COMMENT '用户名字',
    name varchar(255) COMMENT '系统身份身份，比如：管理员',
    password varchar(64) COMMENT '创建者',
    salt datetime COMMENT '创建时间',
    state varchar(64) COMMENT '更新者',
    create_time datetime COMMENT '创建时间',
    PRIMARY KEY (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT = '用户信息表';


CREATE TABLE image (
    id varchar(70) NOT NULL COMMENT '图片Id',
    name varchar(70) NOT NULL COMMENT '图片名称',
    type varchar(32) NOT NULL COMMENT '图片数据类型',
    create_date datetime NOT NULL COMMENT '创建时间',
    data mediumblob NOT NULL COMMENT '图片数据',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='图片数据信息表';