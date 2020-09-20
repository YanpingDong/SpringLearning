package com.example.springshiro.config;

import com.example.springshiro.CustomRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;


@Configuration
public class ShiroConfig {

    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        /*org.apache.shiro.web.filter.mgt.DefaultFilter中定义了所有默认的anon authc的过滤器
         ShiroFilterFactoryBean.getObject-->ShiroFilterFactoryBean.createInstance-->ShiroFilterFactoryBean.createFilterChainManager-->DefaultFilterChainManager manager = new DefaultFilterChainManager()-->addDefaultFilters()
        -->{
        for (DefaultFilter defaultFilter : DefaultFilter.values()) {
            addFilter(defaultFilter.name(), defaultFilter.newInstance(), init, false);
        }

        DefaultFilter {
            anon(AnonymousFilter.class),
            authc(FormAuthenticationFilter.class),
            authcBasic(BasicHttpAuthenticationFilter.class),
            logout(LogoutFilter.class),
            noSessionCreation(NoSessionCreationFilter.class),
            perms(PermissionsAuthorizationFilter.class),
            port(PortFilter.class),
            rest(HttpMethodPermissionFilter.class),
            roles(RolesAuthorizationFilter.class),
            ssl(SslFilter.class),
            user(UserFilter.class);}
    }*/
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);


        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        // <!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/fonts/**", "anon");
        filterChainDefinitionMap.put("/img/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/html/**", "anon");
        filterChainDefinitionMap.put("/getVerifyCode", "anon");
        filterChainDefinitionMap.put("/favicon.ico", "anon");
        //配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
        filterChainDefinitionMap.put("/logout", "logout");
        filterChainDefinitionMap.put("/user/login","anon");
        //Swagger2的所有请求都不需要拦截
        filterChainDefinitionMap.put("/swagger/**","anon");
        filterChainDefinitionMap.put("/v2/api-docs","anon");
        filterChainDefinitionMap.put("/swagger-ui.html","anon");
        filterChainDefinitionMap.put("/swagger-resources/**","anon");
        filterChainDefinitionMap.put("/webjars/**","anon");
        filterChainDefinitionMap.put("/favicon.ico","anon");
        filterChainDefinitionMap.put("/captcha.jpg","anon");
        filterChainDefinitionMap.put("/csrf","anon");
        //<!-- 过滤链定义，从上向下顺序执行，一般将/**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
        //<!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
        filterChainDefinitionMap.put("/**", "authc");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        shiroFilterFactoryBean.setSuccessUrl("/index");
        return shiroFilterFactoryBean;
    }


    /**
     *
     * @param customRealm 因为realm里一般都涉及DAO的实体，而DAO实体又由Spring管理，所以Realm也交给Spring进行注入
     * @return
     */
    @Bean
    public SecurityManager securityManager(CustomRealm customRealm, HashedCredentialsMatcher credentialsMatcher) {
        DefaultWebSecurityManager defaultSecurityManager = new DefaultWebSecurityManager();
        customRealm.setCredentialsMatcher(credentialsMatcher);
        defaultSecurityManager.setRealm(customRealm);
        return defaultSecurityManager;
    }



    //===============the follow configuration enable shiro annotation

    /**
     *
     * @return
     * LLifecycleBeanPostProcessor implements DestructionAwareBeanPostProcessor
     * DestructionAwareBeanPostProcessor extends BeanPostProcessor
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * * * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证 * * * 配置以下两个bean(DefaultAdvisorAutoProxyCreator(可选)和AuthorizationAttributeSourceAdvisor)即可实现此功能 * * @return
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }


    //================================use encryption==============================================
    @Bean(name = "credentialsMatcher")
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        // 散列算法:这里使用MD5算法;
        hashedCredentialsMatcher.setHashAlgorithmName(CustomRealm.algorithmName);
        // 散列的次数，比如散列两次，相当于 md5(md5(""));
        hashedCredentialsMatcher.setHashIterations(CustomRealm.hashIterations);
        // storedCredentialsHexEncoded默认是true，此时用的是密码加密用的是Hex编码；false时用Base64编码
        hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);
        return hashedCredentialsMatcher;
    }

    /**
     * 解决： 无权限页面不跳转 shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorized") 无效
     * shiro的源代码ShiroFilterFactoryBean.Java定义的filter必须满足filter instanceof AuthorizationFilter，
     * 只有perms，roles，ssl，rest，port才是属于AuthorizationFilter，而anon，authcBasic，auchc，user是AuthenticationFilter，
     * 所以unauthorizedUrl设置后页面不跳转 Shiro注解模式下，登录失败与没有权限都是通过抛出异常。
     * 并且默认并没有去处理或者捕获这些异常。在SpringMVC下需要配置捕获相应异常来通知用户信息
     * @return
     */
    @Bean(name = "simpleMappingExceptionResolver")
    public SimpleMappingExceptionResolver
    createSimpleMappingExceptionResolver() {
        SimpleMappingExceptionResolver r = new SimpleMappingExceptionResolver();
        Properties mappings = new Properties();

        mappings.setProperty("UnauthorizedException", "/sys/403");
        r.setExceptionMappings(mappings);  // None by default
        //r.setDefaultErrorView("error");    // No default
        r.setExceptionAttribute("exception");     // Default is "exception"
        //r.setWarnLogCategory("example.MvcLogger");     // No default
        return r;
    }

}