package com.bin.spring.testCache;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.support.CompositeCacheManager;
import org.springframework.cache.support.NoOpCacheManager;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;

@EnableCaching  // 开启Spring的缓存
@Configuration
public class CacheConfig {
    @Bean
    public CacheManager concurrentMapCacheManager(){
        return new ConcurrentMapCacheManager();     // 设置一个CHM的缓存
    }

//    @Bean
//    public CacheManager redisCacheManager(RedisTemplate redisTemplate){
//        return new RedisCacheManager(redisTemplate);  // 版本与书中不一致，所以这里会报错。而在SpringBoot中也可以配置Redis做缓存
//    }
//
//    @Bean
//    public CacheManager simpleCacheManager(){
//        return new SimpleCacheManager();
//    }
//
//    @Bean
//    public CacheManager NoOpCacheManager(){
//        return new NoOpCacheManager();
//    }
//
//    @Bean
//    public CacheManager compositeCacheManager(){
//        return new CompositeCacheManager();
//    }

}
