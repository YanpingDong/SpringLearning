
MariaDB和Mybatis依赖

```xml
<!-- Mybatis starter -->
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>2.0.1</version>
</dependency>

<!-- MariaDB驱动 -->
<dependency>
    <groupId>org.mariadb.jdbc</groupId>
    <artifactId>mariadb-java-client</artifactId>
    <version>2.4.1</version>
</dependency>
```

准备数据库环境

```sql
1. 先用ROOT登录创建dyp用户
CREATE USER dyp IDENTIFIED BY '1234567890';
2. 用ROOT登录创建数据库
CREATE database test;
3. 把test数据库授权给dyp用户
GRANT ALL ON test.* TO 'dyp'@'%' WITH GRANT OPTION;
4. 用dyp登录创建下面两个表
create table test.user
(
    id bigint not null AUTO_INCREMENT comment '主键' primary key,
    age int null comment '年龄',
    password varchar(32) null comment '密码',
    sex int null comment '性别',
    username varchar(32) null comment '用户名'
);

create table test.city
(
    id bigint not null comment '主键' primary key,
    province_id bigint null comment 'provice id',
    city_name varchar(32) null comment 'city anme',
    description varchar(32) null comment 'descritpion'
)
```