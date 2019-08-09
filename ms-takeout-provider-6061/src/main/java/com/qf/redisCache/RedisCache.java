package com.qf.redisCache;

import com.sun.corba.se.spi.ior.ObjectKey;
import org.apache.ibatis.cache.Cache;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 如果自动 new RedisCache() 里面通过
 *      @Resource
 *     private RedisTemplate<Object, Object> redisTemplate;
 *  这个方式想要实现redisTemplate的方式注入，是注入不进来的。
 *
 * 那么要怎么拿到redisTemplate，只能从容器中取。 ApplicationContext  getBean("beanName")
 *
 */
public class RedisCache implements Cache {

    // 读写所, 比Lock更加细粒度的锁
    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private String mybatisCachePrefix = "mybatis:cache";

    private RedisTemplate<Object, Object> redisTemplate;

    private String id;

    public RedisCache() {}

    public RedisCache(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }

    //在容器中获取 RedisTemplate
    private RedisTemplate<Object, Object> getRedisTemplate() {
        return ApplicationContextHolder.getBean("redisTemplate");
    }

    @Override
    public void putObject(Object key, Object value) {
        // js
        if(null == this.redisTemplate) {
           this.redisTemplate = getRedisTemplate();
        }

        this.redisTemplate.opsForValue().set(mybatisCachePrefix + key, value);
    }

    @Override
    public Object getObject(Object key) {
        if(null == this.redisTemplate) {
            this.redisTemplate = getRedisTemplate();
        }
        return this.redisTemplate.opsForValue().get(mybatisCachePrefix + key);
    }

    @Override
    public Object removeObject(Object key) {
        if(null == this.redisTemplate) {
            this.redisTemplate = getRedisTemplate();
        }
        this.redisTemplate.delete(mybatisCachePrefix + key); //删除
        return null;
    }

    @Override
    public void clear() {
        Set<Object> keySet = redisTemplate.keys(mybatisCachePrefix + "*");  //查找到redis中所有已

        if(null != keySet && keySet.size() > 0) {
            redisTemplate.delete(keySet);
        }
    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return this.readWriteLock;
    }
}
