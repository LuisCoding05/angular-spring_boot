package com.springluis.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springluis.backend.model.dto.UserDto;
import com.springluis.backend.services.service.UserService;

import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/user")
public class UserListController {

    @Autowired
    private UserService userService;

    @GetMapping("/findAll")
    public List<UserDto> getAllUsers() {

        List<UserDto> users = userService.findAll();
        return users;
    }
    

}
