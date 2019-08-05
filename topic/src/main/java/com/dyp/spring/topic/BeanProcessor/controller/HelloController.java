package com.dyp.spring.topic.BeanProcessor.controller;

import com.dyp.spring.topic.BeanProcessor.service.HelloServiceImplV1;
import com.dyp.spring.topic.BeanProcessor.service.HelloServiceImplV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
    @Autowired
    @Qualifier(value = "helloServiceImplV1")
    private HelloServiceImplV1 helloServiceImplV1;

    @Autowired
    @Qualifier(value = "helloServiceImplV2")
    private HelloServiceImplV2 helloServiceImplV2;

    @GetMapping(value="hello")
    @ResponseBody
    public String sayHello(){
        String rValue;
        if(getHelloVersion()=="A"){
            rValue = helloServiceImplV1.sayHello();
        }else{
            rValue = helloServiceImplV2.sayHello();
        }

        return rValue;
    }

    @GetMapping(value="hi")
    @ResponseBody
    public String sayHi(){
        String rValue;
        if(getHiVersion()=="A"){
            rValue = helloServiceImplV1.sayHi();
        }else{
            rValue = helloServiceImplV2.sayHi();
        }

        return rValue;
    }

    private String getHelloVersion()
    {
        //get version from properties config file or some place
        return "A" ;
    }

    private String getHiVersion()
    {
        //get version from properties config file or some place
        return "A" ;
    }
}
