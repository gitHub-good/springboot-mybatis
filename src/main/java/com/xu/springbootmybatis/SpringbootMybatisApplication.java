package com.xu.springbootmybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * 开启缓存步骤：
 *      1.开启基于注解的缓存@EnableCaching
 *      2.标注缓存注解即可
         * @Cacheable
         * @CacheEvict
         * @CachePut
 */

@EnableCaching/*开启缓存注解*/
@MapperScan(value = "com.xu.springbootmybatis.mapper")/*指定mapper路径*/
@SpringBootApplication
public class SpringbootMybatisApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootMybatisApplication.class, args);
    }
}
