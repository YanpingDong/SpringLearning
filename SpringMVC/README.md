# Spring MVC

本章主要讲如何快速的使用spring boot快速搭建一个Web MVC服务。

最快的方案是使用`https://start.spring.io`网站，然后按要求填写一些基本说明，并在search for dependencies中输入Web关键字,如下图所示

![](pic/startspringio.png)

## RequestMapping初探

### 如果不设置method

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

[本文示例代码](source/SpringMVC)