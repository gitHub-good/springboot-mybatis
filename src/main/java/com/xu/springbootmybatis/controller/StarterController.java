package com.xu.springbootmybatis.controller;

import com.xu.starter.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 自定义starter启动依赖
 */
@RestController
public class StarterController {

    @Autowired
    HelloService helloService;

    @GetMapping("/hello")
    public String helloStarter(){
        return helloService.sayHellAtguigu("张三");
    }
}
