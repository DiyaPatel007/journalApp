package com.eDiya.journalApp.controller;

import com.eDiya.journalApp.entity.User;
import com.eDiya.journalApp.repository.UserRepository;
import com.eDiya.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {
    @Autowired
    private UserService userService;

    @GetMapping("health-check")
    public String healthCheck(){
        return "ok";
    }

    @PostMapping("/create-user")
    public void CreateUser(@RequestBody User user){
        userService.saveNewUser(user);
    }

}
