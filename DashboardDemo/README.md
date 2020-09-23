# Dashboard

用来管理后台页面添加、创建与设置用户权限，可以作为后台管理项目的初始构建点。根据自己的需求添加新的数据页等。

# 使用到的技术

SpringBoot2.3.3; Thymeleaf; AdminLTE-3.0.5（基于Bootstrap）; Shiro；MySQL；MyBatis

```
AdminLTE-3.0.5 下载后里面就已经包含了Bootstrap，所以不需要单独下载。
```

# 初始化数据库

Step1： 在MySQL数据库中创建名为dashboard的库`CREATE DATABASE dashboard`
Step2： 在dashboard.properties中修改数据库相关的配置，比如用户名和密码
Step3： 在pom.xml所在的目录下运行`mvn antrun:run -Pinit-db`

```
实际使用的是Maven的Profile功能。本来Profile功能是依据不同的环境使用不同的配置文件，但这儿使用“init-db”为Key创建一个特殊的环境，在切换到该环境的时候自动创建系统使用的数据库。

如果是docker启动，请添加--max-allowed-packet启动参数： docker run -d -p 3306:3306 --name mysql57  -e MYSQL_ROOT_PASSWORD=123456 -d mysql:5.7 --max-allowed-packet=67108864
```