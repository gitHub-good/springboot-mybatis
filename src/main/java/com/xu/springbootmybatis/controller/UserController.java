package com.xu.springbootmybatis.controller;

import com.xu.springbootmybatis.entity.User;
import com.xu.springbootmybatis.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;/*查询数据库信息*/

    @GetMapping("/user/{id}")
    public Optional<User> getUser(@PathVariable("id") Integer id){
        Optional<User> user = userRepository.findById(id);
        return user;
    }

    @GetMapping("/user")
    public User insertUser(User user){
        User save = userRepository.save(user);
        return save;
    }

}
