package com.dyp.spring.topic.BeanProcessor.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RoutingInjected {
}
