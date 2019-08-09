package com.qf.sys.shiroconfig;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfiguation {

    // 配置shiro的过滤器
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        shiroFilterFactoryBean.setSecurityManager(securityManager);

        shiroFilterFactoryBean.setLoginUrl("/no_login");

        Map<String,String> map = new LinkedHashMap<>();
        map.put("/login", "anon");
        map.put("/**", "authc");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);

        return shiroFilterFactoryBean;
    }


    @Bean
    public SecurityManager securityManager(CustomerRealm customerRealm, CacheManager cacheManager,
                                           CustomerSessionManager customerSessionManager) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();

        defaultWebSecurityManager.setSessionManager(customerSessionManager);
        defaultWebSecurityManager.setRealm(customerRealm);
        defaultWebSecurityManager.setCacheManager(cacheManager);  // 设置缓存

        return defaultWebSecurityManager;
    }

    @Bean
    public CustomerSessionManager customerSessionManager(RedisSessionDAO redisSessionDAO) {
        CustomerSessionManager customerSessionManager = new CustomerSessionManager();
        customerSessionManager.setSessionDAO(redisSessionDAO);
        return customerSessionManager;
    }

    @Bean
    public RedisSessionDAO redisSessionDAO(RedisManager redisManager) {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager);
        return redisSessionDAO;
    }

    //定义realm
    @Bean
    public CustomerRealm customerRealm(HashedCredentialsMatcher hashedCredentialsMatcher) {
        CustomerRealm customerRealm = new CustomerRealm();
        customerRealm.setCredentialsMatcher(hashedCredentialsMatcher);  //设置密码的比较器
        return customerRealm;
    }

    // 密码校验器
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        hashedCredentialsMatcher.setHashIterations(2);
        return hashedCredentialsMatcher;
    }

    // RedisManager是用于连接redis
    @Bean
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost("192.168.223.137:6379");
        redisManager.setPassword("123");
        return redisManager;
    }

    // 要想使用shiro的缓存，那么必须要实现 CacheManager这个接口，该接口是 Shiro提供的
    // 第三方 crazycake实现了该接口，
    @Bean
    public CacheManager cacheManager(RedisManager redisManager) {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager);
        return redisCacheManager;
    }

    /**
     *  该类的作用是，实现注解的方式来设置权限
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    /**
     * 实现注解的方式来配置权限
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

}
