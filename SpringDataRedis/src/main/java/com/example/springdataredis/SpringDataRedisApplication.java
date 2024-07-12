package com.example.springdataredis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * 1、每一个需要缓存的数据我们都来指定要放到那个名字的缓存。【缓存的分区(按照业务类型分)】
 * 2、 @Cacheable({"category"})
 *      代表当前方法的结果需要缓存，如果缓存中有，方法不用调用。
 *      如果缓存中没有，会调用方法，最后将方法的结果放入缓存
 * 3、默认行为
 *      1）、如果缓存中有，方法不用调用。
 *      2）、key默认自动生成；缓存的名字::SimpleKey [](自主生成的key值)
 *      3）、缓存的value的值。默认使用jdk序列化机制，将序列化后的数据存到redis
 *      4）、默认ttl时间 -1；
 *
 *    自定义：
 *      1）、指定生成的缓存使用的key：  key属性指定，接受一个SpEL
 *             SpEL的详细https://docs.spring.io/spring/docs/5.1.12.RELEASE/spring-framework-reference/integration.html#cache-spel-context
 *      2）、指定缓存的数据的存活时间： 配置文件中修改ttl
 *      3）、将数据保存为json格式:
 *              自定义RedisCacheConfiguration即可
 * 4、Spring-Cache的不足；
 *      1）、读模式：
 *          缓存穿透：查询一个null数据。解决：缓存空数据；ache-null-values=true
 *          缓存击穿：大量并发进来同时查询一个正好过期的数据。解决：加锁；？默认是无加锁的;sync = true（加锁，解决击穿）
 *          缓存雪崩：大量的key同时过期。解决：加随机时间。加上过期时间。：spring.cache.redis.time-to-live=3600000
 *      2）、写模式：（缓存与数据库一致）
 *          1）、读写加锁。
 *          2）、引入Canal，感知到MySQL的更新去更新数据库
 *          3）、读多写多，直接去数据库查询就行
 *    总结：
 *      常规数据（读多写少，即时性，一致性要求不高的数据）；完全可以使用Spring-Cache；写模式（只要缓存的数据有过期时间就足够了）
 *      特殊数据：特殊设计
 *
 *   原理：
 *      CacheManager(RedisCacheManager)->Cache(RedisCache)->Cache负责缓存的读写
 *
 */
@SpringBootApplication
public class SpringDataRedisApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringDataRedisApplication.class, args);
    }

}
