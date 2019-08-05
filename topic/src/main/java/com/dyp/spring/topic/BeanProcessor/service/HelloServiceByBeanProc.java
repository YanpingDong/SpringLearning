package com.dyp.spring.topic.BeanProcessor.service;

import com.dyp.spring.topic.BeanProcessor.annotation.RoutingSwitch;

@RoutingSwitch("hello.switch")//模拟@Service(value="hello.swithc")
public interface HelloServiceByBeanProc {
    @RoutingSwitch("B")
    String sayHello();
    String sayHi();
}
