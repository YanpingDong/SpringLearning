
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

# MySQL一些需要注意的数据类型

## 日期类型

MySQL有四个日期类型：date,time, datetime, timestamp

**Date**

名称 | 解释 
----|-----
显示格式 | YYYY-MM-DD
显示范围 | 1601-01-01 到 9999-01-01
应用场景 | 当业务需求中只需要精确到天时，可以用这个时间格式

**DateTime**

名称 | 解释 
----|-----
显示格式 | YYYY-MM-DD HH:mm:ss
显示范围 | 1000-01-01 00:00:00.000000 到 9999-12-31 23:59:59.999999
应用场景 | 当业务需求中需要精确到秒时，可以用这个时间格式

**TimeStamp**

名称 | 解释 
----|-----
显示格式 | YYYY-MM-DD HH:mm:ss
显示范围 | 1970-01-01 00:00:01.000000 to 2038-01-19 03:14:07.999999
应用场景 | 当业务需求中需要精确到秒时，可以用这个时间格式

**Time**

名称 | 解释 
----|-----
显示格式 | HH:mm:ss
显示范围 | 00:00:00 到 23:59:59
应用场景 | 当业务需求中只需要每天的时间，可以用这个时间格式

**datetime与timestamp的区别**

**两者的存储方式不一样** ： 对于TIMESTAMP，它把客户端插入的时间从当前时区转化为UTC（世界标准时间）进行存储。查询时，将其又转化为客户端当前时区进行返回。而对于DATETIME，不做任何改变，基本上是原样输入和输出。

**两者所能存储的时间范围不一样** ： timestamp所能存储的时间范围为：’1970-01-01 00:00:01.000000’ 到 ‘2038-01-19 03:14:07.999999’。 
datetime所能存储的时间范围为：’1000-01-01 00:00:00.000000’ 到 ‘9999-12-31 23:59:59.999999’。

### mybatis数据类型映射

类型名称 | JDBC Type  | Java Type
-------|------|-------
DATE  | DATE | java.util.Date / java.sql.Date
TIME |	TIME | java.sql.Time 
DATETIME |	TIMESTAMP |	java.util.Date / java.sql.Timestamp
TIMESTAMP |	TIMESTAMP| 	java.util.Date / java.sql.Timestamp


```
mybaits里面没有了DATETIME的参数，会报No enum constant org.apche.type.jdbcType.DATETIME错

使用java.util.Date作为接收的目的是在用swagger对外暴露接口模型的时候以string的方式显示并且有默认值，比如："date": "2020-02-22T04:17:22.033Z"。

如果用java.sql.Date: swagger的显示是"date": "string"。

如果用import java.sql.Timestamp： swagger的显示如下
"date": {
    "date": 0,
    "day": 0,
    "hours": 0,
    "minutes": 0,
    "month": 0,
    "nanos": 0,
    "seconds": 0,
    "time": 0,
    "timezoneOffset": 0,
    "year": 0
  }


```

## varchar和char的区别

**所需存储空间** 

char(n)需要固定的存储空间，即当存入的字符长度小于n时，所需空间仍然是n个字符所需的存储空间（由于字符编码不确定，所以长度也无法确定）；

varchar(n)的所需存储空间是随字符长度而变化的，因为varchar类型存储了当前字符的长度；

**存取速度**

char因为长度固定，所以存取速度要比varchar快很多，甚至能快50%，但正因为其长度固定，所以会占据多余的空间，是空间换时间的做法

**最大数据长度**

mysql的vachar字段的类型最大长度是65535;当数据长度小于255时，数据库采用1个字节记录varchar数据长度，当数据长度>255时，需要用两个字节存储长度；

char类型字段的最大长度是255，且255个字节可全部用于存储数据；由于char类型没有记录数据长度，所以导致其尾部的空格在存储时会被去掉；如果某个长度小于n，MySQL就会在它的右边用空格字符补足．（在检索操作中那些填补出来的空格字符将被去掉）

判断该选取VARCHAR还是CHAR，我们可以从以下几个方面来考虑：

- 该字段数据集的平均长度与最大长度是否相差很小，若相差很小优先考虑CHAR类型，反之，考虑VARCHAR类型。
- 若字段存储的是MD5后的哈希值，或一些定长的值，优先选取CHAR类型。
- 若字段经常需要更新，则优先考虑CHAR类型，由于CHAR类型为定长，因此不容易产生碎片。
- 对于字段值存储很小的信息，如性别等，优先选取CHAR类型，因为VARCHAR类型会占用额外的字节保存字符串长度信息。

```
VARCHAR节省了存储空间，所以对性能也有帮助。但是，由于行是变长的，在UPDATE时可能使行变得比原来更长，这就导致需要做额外的工作。如果一个行占用的空间增长，并且在页内没有更多的空间可以存储，在这种情况下，不同的存储引擎的处理方式是不一样的。例如，MyISAM会将行拆成不同的片段存储，InnoDB则需要分裂页来使行可以放进页内。其他一些存储引擎也许从不在原数据位置更新数据。
```

### mybatis数据类型映射

类型名称 | JDBC Type  | Java Type
-------|------|-------
VARCHAR  | VARCHAR | java.lang.String
TIME |	CHAR | java.lang.String


# MySql数据类型与Java数据类型对照表

类型名称 | JDBC Type(数据库类型)  | Java Type
-------|------|-------
VARCHAR 	| 	VARCHAR |	java.lang.String 	
CHAR 	| 	CHAR |	java.lang.String 	
BLOB 	| 	BLOB |	java.lang.byte[] 	
TEXT 	| 	VARCHAR |	java.lang.String 	
INTEGER 	| 	INTEGER UNSIGNED |	java.lang.Long 	
TINYINT 	| 	TINYINT UNSIGNED |	java.lang.Integer 	
SMALLINT 	| 	SMALLINT UNSIGNED 	|java.lang.Integer 
MEDIUMINT 	| 	MEDIUMINT UNSIGNED 	|java.lang.Integer 	
BIT 	| 	BIT |	java.lang.Boolean 	
BIGINT 	| 	BIGINT UNSIGNED |	java.math.BigInteger
FLOAT 	| 	FLOAT |	java.lang.Float 
DOUBLE 	| 	DOUBLE |	java.lang.Double 	
DECIMAL 	| 	DECIMAL |	java.math.BigDecimal  
BOOLEAN 	| 	同TINYINT 	|  	 
ID 	| 	PK (INTEGER UNSIGNED) |	java.lang.Long  
DATE  | DATE | java.util.Date / java.sql.Date
TIME |	TIME | java.sql.Time 
DATETIME |	TIMESTAMP |	java.util.Date / java.sql.Timestamp
TIMESTAMP |	TIMESTAMP| 	java.util.Date / java.sql.Timestamp
YEAR 	| 	YEAR |	java.sql.Date


# 关于数据库自增主键的思考

对于自增主键，常见的问题如下

1. 不利于移植，不同数据库的自增方式不一样。实现也会有差别，当然像liquibase会屏蔽实际区别，这也只是在初始数据库时有用，你不必关心如何建表。但对导入数据，会遇到实际ID值可能会发生变化的情况，如果对含有数据库自增长的ID字段加入外键关系的数据表的数据进行直接进行导入导出时会导致关系的丢失。例如有两个表：School {ID, Name}; Student {ID, SchoolID, Name}; 建立外键关系 Student.SchoolID -> School.ID 。那么，当将学校和学生数据同时导入这两个表时就会遇到麻烦，因为 School.ID 的值是新的序列值，不一定可预测，这就会导致 Student 数据中保持的 SchoolID 与对应的School数据在表中的新的ID不一致。

2. 不方便获取ID值。

3. 分布式架构，分库分表，需要补充办法实现全局ID唯一。比如 自增+步长。但对于老库分表，因为是自增ID，分表是很麻烦的，需要保证分表后老ID在正确表中。数据导入的便利性极大的下降。如果再有外键引用的表处理表分割也需要考虑是否同库问题，而如果该表也是自增ID那么就是灾难了。

所以如果对于会在自增ID上建立表之间的关系，不建议使用自增ID

当然也有优点：

1. 自增，趋势自增，可作为聚集索引，提升查询效率

2. 节省磁盘空间。500W数据，UUID占5.4G,自增ID占2.5G.

3. 查询，写入效率高：查询略优。写入效率自增ID是UUID的四倍。

所以如果ID没有实际作用，只是满足关系数据库中的需要唯一ID这个条件，那么选择自增是极好的。简单的判断就是，该表就是个记录表，和其他表不可能有关联关系。