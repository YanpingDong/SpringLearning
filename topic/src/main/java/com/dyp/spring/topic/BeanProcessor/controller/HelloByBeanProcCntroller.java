package com.dyp.spring.topic.BeanProcessor.controller;

import com.dyp.spring.topic.BeanProcessor.annotation.RoutingInjected;
import com.dyp.spring.topic.BeanProcessor.service.HelloService;
import com.dyp.spring.topic.BeanProcessor.service.HelloServiceByBeanProc;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloByBeanProcCntroller {

    @RoutingInjected //一个标识，通知RoutingBeanPostProcessor要对该类型成员做操作
    private HelloServiceByBeanProc helloService;

    @GetMapping(value="helloP")
    @ResponseBody
    public String sayHello(){
        return helloService.sayHello();
    }

    @GetMapping(value="hiP")
    @ResponseBody
    public String sayHi(){
        return helloService.sayHi();
    }
}
