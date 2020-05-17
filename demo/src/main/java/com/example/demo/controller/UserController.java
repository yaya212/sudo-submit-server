package com.example.demo.controller;

import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//make this as rest controller

@RestController
@RequestMapping(path = "/user")
public class UserController {

    //autowiring user repository
    @Autowired
    UserRepository userRepository;

    //to test our service is up and running
    @GetMapping
    public String check(){
        return "Hello Friend!!!";
    }

    /**
     * this method returns list of user names
     * @return usernameList
     */
    @GetMapping(path = "/getusernames")
    public List<String> getAllUserNames(){
        return userRepository.getAllUserNames();
    }

    @GetMapping(path = "/insert")
    public void insertAllUserNames(){
        userRepository.insertAllUserNames();
    }
}
