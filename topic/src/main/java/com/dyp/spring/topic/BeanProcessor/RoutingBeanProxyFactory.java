package com.dyp.spring.topic.BeanProcessor;

import com.dyp.spring.topic.BeanProcessor.annotation.RoutingSwitch;
import com.dyp.spring.topic.BeanProcessor.annotation.RoutingVersion;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Map;

public class RoutingBeanProxyFactory {
    public static Object createProxy(Class targetClass, Map<String, Object> beans) {
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setInterfaces(targetClass);
        proxyFactory.addAdvice(new VersionRoutingMethodInterceptor(targetClass, beans));
        return proxyFactory.getProxy();
    }

    /**
     * 根据注解内容使用不同的“被代理对像”
     */
    static class VersionRoutingMethodInterceptor implements MethodInterceptor {
        private String classSwitch;
        // 存储不同情况下同类型的备选类，这里只存了两个，当然可以做的很多。这里内只是示意一下（改成Map<String,Object>就可以用多种）
        private Object beanOfSwitchOn;
        private Object beanOfSwitchOff;

        public VersionRoutingMethodInterceptor(Class targetClass, Map<String, Object> beans) {
            String interfaceName = StringUtils.uncapitalize(targetClass.getSimpleName());
            if(targetClass.isAnnotationPresent(RoutingSwitch.class)){
                this.classSwitch =((RoutingSwitch)targetClass.getAnnotation(RoutingSwitch.class)).value();
            }
            this.beanOfSwitchOn = beans.get(this.buildBeanName(interfaceName, true));
            this.beanOfSwitchOff = beans.get(this.buildBeanName(interfaceName, false));
        }

        private String buildBeanName(String interfaceName, boolean isSwitchOn) {
            return interfaceName + "Impl" + (isSwitchOn ? "V2" : "V1");
        }

        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            Method method = invocation.getMethod();
            String switchName = this.classSwitch;
            //从RoutingSwitch中获取注解值
            if (method.isAnnotationPresent(RoutingSwitch.class)) {
                switchName = method.getAnnotation(RoutingSwitch.class).value();
            }
            if (StringUtils.isEmpty(switchName)) {
                throw new IllegalStateException("RoutingSwitch's value is blank, method:" + method.getName());
            }
            return invocation.getMethod().invoke(getTargetBean(switchName), invocation.getArguments());
        }

        //依据不同的RoutingSwitch注解值反回不同的Java对像
        public Object getTargetBean(String switchName) {
            boolean switchOn;
            if (RoutingVersion.A.equalsIgnoreCase(switchName)) {
                switchOn = false;
            } else if (RoutingVersion.B.equalsIgnoreCase(switchName)) {
                switchOn = true;
            } else {
                switchOn = false;
            }
            return switchOn ? beanOfSwitchOn : beanOfSwitchOff;
        }
    }
}