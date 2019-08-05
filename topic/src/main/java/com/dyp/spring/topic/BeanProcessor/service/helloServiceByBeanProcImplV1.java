package com.dyp.spring.topic.BeanProcessor.service;

import org.springframework.stereotype.Service;

@Service(value = "helloServiceByBeanProcImplV1")
public class helloServiceByBeanProcImplV1 implements HelloServiceByBeanProc{

    public String sayHello(){
        return "Hello from V1";
    }
    public String sayHi(){
        return "Hi from V1";
    }
}
