# BeanPostProcessor

BeanPostProcessor是Spring IOC容器给我们提供的一个扩展接口，让我们有机会对Spring初始化的对像做干预。接口声明如下：

```java
public interface BeanPostProcessor {
    //bean初始化方法调用前被调用
    Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException;
    //bean初始化方法调用后被调用
    Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException;

}
```

BeanPostProcessor的实现类注册到Spring IOC容器后，对于该Spring IOC容器所创建的每个bean实例在初始化方法（如afterPropertiesSet和任意已声明的init方法）调用前使用BeanPostProcessor中的方法，大体调用顺序如下：

1. Spring IOC容器实例化Bean
2. 调用BeanPostProcessor的postProcessBeforeInitialization方法
3. 调用bean实例的初始化方法
4. 调用BeanPostProcessor的postProcessAfterInitialization方法

Spring容器通过BeanPostProcessor给了我们一个机会对Spring管理的bean进行再加工。比如：我们可以修改bean的属性，可以给bean生成一个动态代理实例等等。一些Spring AOP的底层处理也是通过实现BeanPostProcessor来执行代理包装逻辑的。

示例代码可以看```com.dyp.spring.topic.BeanProcessor```中的```HelloServiceByBeanProc; helloServiceByBeanProcImplV1; helloServiceByBeanProcImplV2、RoutingBeanPostProcessor```类```com.dyp.spring.topic.BeanProcessor.annotation ```包下的所有类

1. RoutingInjected做为一个标识，告诉Spring在初始化后调BeanPostProcessor相关方法后需要进行处理的判断标识，比如本示例是调用postProcessAfterInitialization方法
2. RoutingBeanPostProcessor类，就是当Spring在对所能操作类进行处理的逻辑处理过程。在发现成员变量有RoutingInjected标识的时候获取该成员变量的可选接口实现类，并生成代理类。
3. 而代理类存储下可选的接口实现类（VersionRoutingMethodInterceptor下beanOfSwitchOff，beanOfSwitchOn成员变理作用），然后依据RoutingSwitch注解的值在调用的时候动态选择反射调用哪个对像做服务


当然BeanPostProcessor可以做的很简单不用这么复杂，如下,只是对指定类型的类做一些操作：

```java
public class TestBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof StudentEntity) {
            StudentEntity studentEntity = new StudentEntity();
            studentEntity.setName(beanName);
            return studentEntity;
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof StudentEntity)
            System.out.println(bean);
        return bean;
    }
}
```