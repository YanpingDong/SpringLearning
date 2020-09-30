SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables */
DROP TABLE IF EXISTS sys_user;
DROP TABLE IF EXISTS sys_image;
DROP TABLE IF EXISTS sys_role;
DROP TABLE IF EXISTS sys_permission;
DROP TABLE IF EXISTS sys_role_permission;
DROP TABLE IF EXISTS sys_user_role;

/* Create Tables */
CREATE TABLE sys_user
(
    user_id int NOT NULL AUTO_INCREMENT COMMENT '用户id',
    user_name varchar(255) NOT NULL COMMENT '用户名字',
    name varchar(255) COMMENT '系统身份身份，比如：管理员',
    password varchar(64) COMMENT '创建者',
    salt varchar(64) COMMENT '创建时间',
    email varchar(64) COMMENT '邮箱',
    tel varchar(64) COMMENT '电话',
    state  bit(1)  COMMENT '可用状态',
    create_time datetime COMMENT '创建时间',
    PRIMARY KEY (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT = '用户信息表';

CREATE TABLE sys_role
(
    role_id int NOT NULL AUTO_INCREMENT COMMENT '角色id',
    available bit(1) NOT NULL COMMENT '是否可用',
    description varchar(255) COMMENT '描述，比如：超级管理员，高级用户等',
    `role` varchar(64) COMMENT '角色名',
    PRIMARY KEY (role_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT = '系统角色表';

CREATE TABLE sys_permission
(
    permission_id int NOT NULL AUTO_INCREMENT COMMENT '角色id',
    available bit(1) NOT NULL COMMENT '是否可用',
    permission_name varchar(64) COMMENT '权限名称。比如：系统管理，用户管理等',
    parent_id int COMMENT '直接父权限id',
    parent_ids varchar(64) COMMENT '所以父权限id',
    permission varchar(64) COMMENT '权限',
    resource_type varchar(64) COMMENT '界面资源类型',
    url varchar(64) COMMENT '界面路径',
    level int COMMENT '级别',
    PRIMARY KEY (permission_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT = '系统角色表';

CREATE TABLE sys_role_permission
(
    id int NOT NULL AUTO_INCREMENT COMMENT 'id',
    permission_id int NOT NULL  COMMENT '权限id，fk',
    role_id int NOT NULL COMMENT '角色Id，fk',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT = '系统角色表';

CREATE TABLE sys_user_role
(
    id int NOT NULL AUTO_INCREMENT COMMENT 'id',
    user_id int NOT NULL  COMMENT '权限id，fk',
    role_id int NOT NULL COMMENT '角色Id，fk',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT = '系统角色表';

CREATE TABLE sys_image (
    id varchar(70) NOT NULL COMMENT '图片Id',
    name varchar(70) NOT NULL COMMENT '图片名称',
    type varchar(32) NOT NULL COMMENT '图片数据类型',
    create_date datetime NOT NULL COMMENT '创建时间',
    data mediumblob NOT NULL COMMENT '图片数据',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='图片数据信息表';

/* Insert Data */
/* write sys_user */
INSERT INTO `sys_user` (`user_id`,`user_name`,`name`,`password`,`salt`,`state`,email, tel,create_time)
VALUES ('1', 'admin', '管理员', 'd3c59d25033dbf980d29554025c23a75', '8d78869f470951332959580424d4bf4f', 1,"lear521@163.com","12345678900",sysdate());
INSERT INTO `sys_user` (`user_id`,`user_name`,`name`,`password`,`salt`,`state`,email, tel,create_time)
VALUES ('2', 'test', '管理员', 'd3c59d25033dbf980d29554025c23a75', '8d78869f470951332959580424d4bf4f', 1,"lear521@163.com","12345678900",sysdate());

/* write sys_role */
INSERT INTO `sys_role` (`role_id`,`available`,`description`,`role`) VALUES (1,1,'超级管理员','admin');
INSERT INTO `sys_role` (`role_id`,`available`,`description`,`role`) VALUES (2,1,'高级用户','powerUser');
INSERT INTO `sys_role` (`role_id`,`available`,`description`,`role`) VALUES (3,1,'普通用户','user');
INSERT INTO `sys_role` (`role_id`,`available`,`description`,`role`) VALUES (4,1,'游客','guest');

/* write sys_permission */
/* 系统管理菜单 */
INSERT INTO `sys_permission` (`permission_id`,`available`,`permission_name`,`parent_id`,`parent_ids`,`permission`,`resource_type`,`url`,level)
VALUES (1,1,'系统管理',0,'0','system:view','menu','#',1);

INSERT INTO `sys_permission` (`permission_id`,`available`,`permission_name`,`parent_id`,`parent_ids`,`permission`,`resource_type`,`url`,level)
VALUES (2,1,'用户管理',1,'1','user:view','menu','user/ulist',2);
INSERT INTO `sys_permission` (`permission_id`,`available`,`permission_name`,`parent_id`,`parent_ids`,`permission`,`resource_type`,`url`,level)
VALUES (3,1,'用户添加',2,'1/2','user:add','button','user/userAdd',3);
INSERT INTO `sys_permission` (`permission_id`,`available`,`permission_name`,`parent_id`,`parent_ids`,`permission`,`resource_type`,`url`,level)
VALUES (4,1,'用户修改',2,'1/2','user:edit','button','user/userEdit',3);
INSERT INTO `sys_permission` (`permission_id`,`available`,`permission_name`,`parent_id`,`parent_ids`,`permission`,`resource_type`,`url`,level)
VALUES (5,1,'用户删除',2,'1/2','user:del','button','user/userDel',3);

INSERT INTO `sys_permission` (`permission_id`,`available`,`permission_name`,`parent_id`,`parent_ids`,`permission`,`resource_type`,`url`,level)
VALUES (6,1,'角色管理',1,'1','role:view','menu','role/rlist',2);
INSERT INTO `sys_permission` (`permission_id`,`available`,`permission_name`,`parent_id`,`parent_ids`,`permission`,`resource_type`,`url`,level)
VALUES (7,1,'角色添加',6,'1/6','role:add','button','role/roleAdd',3);
INSERT INTO `sys_permission` (`permission_id`,`available`,`permission_name`,`parent_id`,`parent_ids`,`permission`,`resource_type`,`url`,level)
VALUES (8,1,'角色修改',6,'1/6','role:edit','button','role/roleEdit',3);
INSERT INTO `sys_permission` (`permission_id`,`available`,`permission_name`,`parent_id`,`parent_ids`,`permission`,`resource_type`,`url`,level)
VALUES (9,1,'角色删除',6,'1/6','role:del','button','role/roleDel',3);
INSERT INTO `sys_permission` (`permission_id`,`available`,`permission_name`,`parent_id`,`parent_ids`,`permission`,`resource_type`,`url`,level)
VALUES (10,1,'角色授权',6,'1/6','role:authorize','button','user/authorize',3);
INSERT INTO `sys_permission` (`permission_id`,`available`,`permission_name`,`parent_id`,`parent_ids`,`permission`,`resource_type`,`url`,level)
VALUES (11,1,'权限管理',1,'1','user:view','menu','permission/plist',2);
INSERT INTO `sys_permission` (`permission_id`,`available`,`permission_name`,`parent_id`,`parent_ids`,`permission`,`resource_type`,`url`,level)
VALUES (12,1,'修改密码',1,'1','user:view','menu','/user/toChangePassword',2);

/* 日志管理菜单 */
INSERT INTO `sys_permission` (`permission_id`,`available`,`permission_name`,`parent_id`,`parent_ids`,`permission`,`resource_type`,`url`,level)
VALUES (13,1,'日志管理',0,'0','log:view','menu','#',1);
INSERT INTO `sys_permission` (`permission_id`,`available`,`permission_name`,`parent_id`,`parent_ids`,`permission`,`resource_type`,`url`,level)
VALUES (14,1,'日志管理',13,'13','log:view','menu','/log/list',2);

/* 内容管理菜单 */
INSERT INTO `sys_permission` (`permission_id`,`available`,`permission_name`,`parent_id`,`parent_ids`,`permission`,`resource_type`,`url`,level)
VALUES (15,1,'内容管理',0,'0','cms:view','menu','#',1);
INSERT INTO `sys_permission` (`permission_id`,`available`,`permission_name`,`parent_id`,`parent_ids`,`permission`,`resource_type`,`url`,level)
VALUES (16,1,'内容编辑',15,'15','cms:edit','menu','/cms/editor',2);


/* write sys_role_permission */
INSERT INTO `sys_role_permission` (`permission_id`,`role_id`) VALUES (1,1);
INSERT INTO `sys_role_permission` (`permission_id`,`role_id`) VALUES (2,1);
INSERT INTO `sys_role_permission` (`permission_id`,`role_id`) VALUES (3,1);
INSERT INTO `sys_role_permission` (`permission_id`,`role_id`) VALUES (4,1);
INSERT INTO `sys_role_permission` (`permission_id`,`role_id`) VALUES (5,1);
INSERT INTO `sys_role_permission` (`permission_id`,`role_id`) VALUES (6,1);
INSERT INTO `sys_role_permission` (`permission_id`,`role_id`) VALUES (7,1);
INSERT INTO `sys_role_permission` (`permission_id`,`role_id`) VALUES (8,1);
INSERT INTO `sys_role_permission` (`permission_id`,`role_id`) VALUES (9,1);
INSERT INTO `sys_role_permission` (`permission_id`,`role_id`) VALUES (10,1);
INSERT INTO `sys_role_permission` (`permission_id`,`role_id`) VALUES (11,1);
INSERT INTO `sys_role_permission` (`permission_id`,`role_id`) VALUES (12,1);
INSERT INTO `sys_role_permission` (`permission_id`,`role_id`) VALUES (13,1);
INSERT INTO `sys_role_permission` (`permission_id`,`role_id`) VALUES (14,1);
INSERT INTO `sys_role_permission` (`permission_id`,`role_id`) VALUES (15,1);
INSERT INTO `sys_role_permission` (`permission_id`,`role_id`) VALUES (16,1);


INSERT INTO `sys_role_permission` (`permission_id`,`role_id`) VALUES (1,2);
INSERT INTO `sys_role_permission` (`permission_id`,`role_id`) VALUES (2,2);
INSERT INTO `sys_role_permission` (`permission_id`,`role_id`) VALUES (7,2);
INSERT INTO `sys_role_permission` (`permission_id`,`role_id`) VALUES (8,2);
INSERT INTO `sys_role_permission` (`permission_id`,`role_id`) VALUES (9,2);

INSERT INTO `sys_user_role` (`role_id`,`user_id`) VALUES (1,1);
INSERT INTO `sys_user_role` (`role_id`,`user_id`) VALUES (2,2);
INSERT INTO `sys_user_role` (`role_id`,`user_id`) VALUES (3,2);