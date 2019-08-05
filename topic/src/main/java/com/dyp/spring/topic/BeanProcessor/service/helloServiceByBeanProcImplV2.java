package com.dyp.spring.topic.BeanProcessor.service;

import org.springframework.stereotype.Service;

@Service(value = "helloServiceByBeanProcImplV2")
public class helloServiceByBeanProcImplV2 implements HelloServiceByBeanProc{

    public String sayHello(){
        return "Hello from V2";
    }
    public String sayHi(){
        return "Hi from V2";
    }
}
