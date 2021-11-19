package com.example.staybooking.controller;

import com.example.staybooking.model.User;
import com.example.staybooking.model.UserMode;
import com.example.staybooking.service.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@RestController ?
@RestController
public class SignupController {
    @Autowired
    private SignUpService signUpService;
    //@PostMapping ?
    @PostMapping("register/guest")
    public void guestSignUp(@RequestBody User user){
        signUpService.signUp(user, UserMode.ROL_GUEST);
    }
    @PostMapping("register/host")
    public void hsotSignUp(@RequestBody User user){
        signUpService.signUp(user, UserMode.ROL_HOST);
    }
}
