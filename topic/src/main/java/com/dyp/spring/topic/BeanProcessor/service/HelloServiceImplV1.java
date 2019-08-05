package com.dyp.spring.topic.BeanProcessor.service;

import org.springframework.stereotype.Service;



@Service(value = "helloServiceImplV1")
public class HelloServiceImplV1 implements HelloService{
    public String sayHello(){
        return "Hello from V1";
    }
    public String sayHi(){
        return "Hi from V1";
    }
}
