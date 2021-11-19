package com.example.staybooking.controller;

import com.example.staybooking.model.Token;
import com.example.staybooking.model.User;
import com.example.staybooking.model.UserMode;
import com.example.staybooking.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/authenticate/guest")
    public Token authenticateGuest(@RequestBody User user){
        return authenticationService.authenticate(user, UserMode.ROL_GUEST);
    }
    @PostMapping("/authenticate/host")
    public Token authenticateHost(@RequestBody User user){
        return authenticationService.authenticate(user, UserMode.ROL_HOST);
    }
}
