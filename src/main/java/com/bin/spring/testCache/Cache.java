package com.bin.spring.testCache;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import java.util.LinkedList;
import java.util.List;


public class Cache {
    // 注意，返回结果如何是void，那么就没有数据会被放入缓存
    @Cacheable(cacheNames = "myCache")  // 此注解会将查询结果放入缓存中，再次调用时，会先从缓存中查找，能找到则不会再调用方法。
    public List<Integer> selectAllIds(){
        return new LinkedList<Integer>();
    }

    @CachePut   // 此注解也会将查询结果放入缓存中，与上面注解不同的是，它始终会放入缓存中，而不会检查缓存中是不是存在结果。
    public List<Double> selectAll(){
        return new LinkedList<Double>();
    }

    @CacheEvict(cacheNames = "myCache") // 此注解会清除一个条目，默认情况下具体的会被删除的数据与方法的参数有关，被删除的key和参数相同
    public void deleteAll(){
        return ;
    }

    // 以上注解中有一些共同的属性，
    // value:缓存的名字
    // key:在K-V缓存中，键的名字，可以使用SpringEL表达式。默认的缓存key基于方法的参数来确定
    // 此外还有condition这个根据SpringEL表达式结果的值来决定是否放入缓存
    @Cacheable(value = "myCache",key ="myComputeKey")
    public void test(){
    }
}
