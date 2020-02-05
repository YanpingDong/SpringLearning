package com.dyp.demo.ctr;

import org.springframework.web.bind.annotation.*;

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
}
