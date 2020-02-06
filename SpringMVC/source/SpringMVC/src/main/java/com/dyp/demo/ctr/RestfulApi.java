package com.dyp.demo.ctr;

import com.dyp.demo.model.People;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping(value = "/mvc")
public class RestfulApi {

    @RequestMapping(value = "{name}")
    public String sayHi1(@PathVariable(name = "name")
                             String name)
    {
        return "hi " + name  ;
    }

    @RequestMapping(value = "hi")
    public String sayHi2(@RequestParam(name = "name")
                                     String name)
    {
        return "hi " + name;
    }

    @RequestMapping(value = "get/{name}", method = RequestMethod.GET)
    public String sayHi3(@PathVariable(name = "name")
                                 String name)
    {
        return "hi " + name  ;
    }

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
        System.out.println(request.getCookies());
        System.out.println(request.getHeaderNames());
        System.out.println(request.getParameterNames());
        try {
            request.getInputStream();//可以通过这个获取request body数据但需要自己解析
        } catch (IOException e) {
            e.printStackTrace();
        }
        people.setId(id);
        people.setNum(num);

        return people ;
    }
}
