# 初始项目

最快的方案是使用`https://start.spring.io`网站，然后按要求填写一些基本说明，并在search for dependencies中输入Web关键字,如下图所示

![](pic/startspringio.png)

# RequestMapping初探

## 如果不设置method

如果不设置method参数，只要路径匹配，可以接收所有方法（GET PUT POST DELETE）的请求

如果设置了method则只接收指定类型的请求

没设定method，源代码见如下：

```java
@RequestMapping(value = "{name}")
public String sayHi1(@PathVariable(name = "name")
                            String name)
{
    return "hi " + name  ;
}
```

没设定method，通过curl测试结果如下：

```sh
$ curl -X GET localhost:8080/mvc/jim
hi jim
$ curl -X PUT localhost:8080/mvc/jim
hi jim
$ curl -X DELTE localhost:8080/mvc/jim
hi jim
$ curl -X POST localhost:8080/mvc/jim
hi jim
$ curl -X OPTION localhost:8080/mvc/hi?name=jim
hi jim
```

设定method源码如下：

```java
@RequestMapping(value = "get/{name}", method = RequestMethod.GET)
    public String sayHi3(@PathVariable(name = "name")
                                 String name)
    {
        return "hi " + name  ;
    }@RequestMapping(value = "get/{name}", method = RequestMethod.GET)
    public String sayHi3(@PathVariable(name = "name")
                                 String name)
    {
        return "hi " + name  ;
    }
```

设定method后如果请求方法不匹配会报错

```sh
$ curl -X GET localhost:8080/mvc/get/jim
hi jim
$ curl -X POST localhost:8080/mvc/get/jim
{"timestamp":"2020-02-05T13:47:56.280+0000","status":405,"error":"Method Not Allowed","message":"Request method 'POST' not supported","path":"/mvc/get/jim"}
```

## GET,DELETE,PUT,POST获取数据的方法

可以通过路径参数、请求参数、请求体、cookie、请求头中得到前端传来的数据。可以使用Spring框架提供的注解，也可以直接从HttpServletRequest中读入，当然也可以通过HttpServletResponse干预Spring框架写返回信息。

示例代码如下：

```java
@RequestMapping(value = "people/info/{id}", method = RequestMethod.PUT)
    public People sayHi7(@PathVariable(name="id") int id,
                         @RequestParam int num,
                         @RequestBody People people,
                         @RequestHeader(name = "headInfo") String headInfo,
                         @CookieValue("username") String username,
                         HttpServletRequest request,
                         HttpServletResponse response)
    {
        System.out.println("cookie value: " + username);
        people.setId(id);
        people.setNum(num);
        return people ;
    }
```

可以通过curl请求，并结合断点查看数据获取情况：

```sh
curl -X PUT --cookie 'username=dyp' --header 'headInfo: test' --header 'Content-Type: application/json' --header 'Accept: application/json' -d '{"name":"tom","sex":"female","age":18}' localhost:8080/mvc/people/info/1?num=2
```

SpringMVC通过“路径”+“请求方式”来定位Controller层的响应方法。所以下面的写法是合法的，多个同名路径。

```java
@RequestMapping(value = "people/info/{id}", method = RequestMethod.POST)
    public People sayHi4(@PathVariable(name="id") int id,
                         @RequestParam int num,
                         @RequestBody People people)
    {
        people.setId(id);
        people.setNum(num);
        return people ;
    }

    @RequestMapping(value = "people/info/{id}", method = RequestMethod.GET)
    public People sayHi5(@PathVariable(name="id") int id,
                         @RequestParam int num,
                         @RequestBody People people)
    {
        people.setId(id);
        people.setNum(num);
        return people ;
    }

    @RequestMapping(value = "people/info/{id}", method = RequestMethod.DELETE)
    public People sayHi6(@PathVariable(name="id") int id,
                         @RequestParam int num,
                         @RequestBody People people)
    {
        people.setId(id);
        people.setNum(num);
        return people ;
    }
```

对与获取RequestBody来说，如果没有给出Content-Type的话GET、DELETE会直接报400错，并不像POST和PUT有默认的解析格式（如果不指定POST，PUT默认application/x-www-form-urlencoded;charset=UTF-8）。

测试错误示例如下：
```sh
$ curl -X GET -d '{"name":"tom","sex":"female","age":18}' localhost:8080/mvc/people/info/1?num=2
{"timestamp":"2020-02-06T07:20:56.388+0000","status":400,"error":"Bad Request","message":"Required request body is missing: public com.dyp.demo.model.People com.dyp.demo.ctr.RestfulApi.sayHi5(int,int,com.dyp.demo.model.People)","path":"/mvc/people/info/1"}

$ curl -X DELETE -d '{"name":"tom","sex":"female","age":18}' localhost:8080/mvc/people/info/1?num=2
{"timestamp":"2020-02-06T07:23:26.961+0000","status":400,"error":"Bad Request","message":"Required request body is missing: public com.dyp.demo.model.People com.dyp.demo.ctr.RestfulApi.sayHi6(int,int,com.dyp.demo.model.People)","path":"/mvc/people/info/1"}

$ curl -X POST -d '{"name":"tom","sex":"fe localhost:8080/mvc/people/info/1?num=2
{"timestamp":"2020-02-06T07:21:30.348+0000","status":415,"error":"Unsupported Media Type","message":"Content type 'application/x-www-form-urlencoded;charset=UTF-8' not supported","path":"/mvc/people/info/1"}


$ curl -X PUT  -d '{"name":"tom","sex":"female","age":18}' localhost:8080/mvc/people/info/1?num=2
{"timestamp":"2020-02-06T07:27:47.482+0000","status":415,"error":"Unsupported Media Type","message":"Content type 'application/x-www-form-urlencoded;charset=UTF-8' not supported","path":"/mvc/people/info/1"}
```

[本文示例代码](source/RequestMappingDemo)

# Thymeleaf

Thymeleaf与SpringMVC的视图技术，及SpringBoot的自动化配置集成非常完美，几乎没有任何成本，你只用关注Thymeleaf的语法即可。

特点：

- 它可以让美工在浏览器查看页面的静态效果，也可以让程序员在服务器查看带数据的动态页面效果。这是由于它支持 html 原型，然后在 html 标签里增加额外的属性来达到模板+数据的展示方式。浏览器解释 html 时会忽略未定义的标签属性，所以 thymeleaf 的模板可以静态地运行。当和Spring整合后，thymeleaf模板引擎会依据数据动态的替换thymeleaf标注部分，比如替换数据、循环操作等。

- 与SpringBoot完美整合，SpringBoot提供了Thymeleaf的默认配置，并且为Thymeleaf设置了视图解析器，我们可以像以前操作jsp一样来操作Thymeleaf。代码几乎没有任何区别，就是在模板语法上有区别。

## SpringBoot与Thymeleaf整合

如果是Maven项目，引入如下依赖就可以了。

```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
```

引入后，在`org.springframework.boot.autoconfigure.thymeleaf.ThymeleafProperties`中可以看到默认了模板存放位置、模板后缀。

```java
public class ThymeleafProperties {
    private static final Charset DEFAULT_ENCODING;
    public static final String DEFAULT_PREFIX = "classpath:/templates/";
    public static final String DEFAULT_SUFFIX = ".html";
    private boolean checkTemplate = true;
    private boolean checkTemplateLocation = true;
    private String prefix = "classpath:/templates/";
    private String suffix = ".html";
    private String mode = "HTML";
```