package com.example.staybooking.service;

import com.example.staybooking.exception.UserAlreadyExistException;
import com.example.staybooking.model.Authority;
import com.example.staybooking.model.User;
import com.example.staybooking.model.UserMode;
import com.example.staybooking.repository.AuthorityRepository;
import com.example.staybooking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

//Service?
@Service
public class SignUpService {

    private UserRepository userRepository;
    private AuthorityRepository authorityRepository;
    private PasswordEncoder passwordEncoder;
    @Autowired
    public SignUpService(UserRepository userRepository, AuthorityRepository authorityRepository,
                         PasswordEncoder passwordEncoder){
        this.userRepository=userRepository;
        this.authorityRepository=authorityRepository;
        this.passwordEncoder=passwordEncoder;
    }
    /*
    * isolation: 让operation 一个个execute
    * */
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void signUp(User user, UserMode userMode) {
        if(userRepository.existsById(user.getUsername())){
            throw new UserAlreadyExistException("User already exists");
        }
        //user already exists return
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        userRepository.save(user);
        authorityRepository.save(new Authority(user.getUsername(), userMode.name()));
    }
}
