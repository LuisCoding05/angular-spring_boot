package com.springluis.backend.controller.auth;

// import com.springluis.backend.services.implement.CustomUserDetailsService;

// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

    // @Autowired
    // private final CustomUserDetailsService customUserDetailsService;

    // LoginController(CustomUserDetailsService customUserDetailsService) {
    //     this.customUserDetailsService = customUserDetailsService;
    // }

    @PostMapping("/login")
    public String postMethodName(@RequestBody String entity) {
        //TODO: process POST request
        
        return entity;
    }
    

}
