SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables */
DROP TABLE IF EXISTS cms_article_data;

/* Create Tables */
CREATE TABLE cms_article_data
(
	id varchar(80) NOT NULL COMMENT '编号',
	title varchar(255) NOT NULL COMMENT '标题',
	description varchar(255) COMMENT '描述、摘要',
	create_by varchar(64) COMMENT '创建者',
    create_date datetime COMMENT '创建时间',
    update_by varchar(64) COMMENT '更新者',
    update_date datetime COMMENT '更新时间',
	content longtext COMMENT '文章内容',
	copy_from varchar(255) COMMENT '文章来源',
	PRIMARY KEY (id)
) COMMENT = '文章详表';


/* Create Indexes */
CREATE INDEX cms_article_create_by ON cms_article_data (create_by ASC);
