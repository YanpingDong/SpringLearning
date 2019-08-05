package com.dyp.spring.topic.BeanProcessor.service;

import org.springframework.stereotype.Service;

@Service(value = "helloServiceImplV2")
public class HelloServiceImplV2 implements HelloService{
    public String sayHello(){
        return "Hello from V2";
    }
    public String sayHi(){
        return "Hi from V2";
    }
}