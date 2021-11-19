package com.example.staybooking.service;

import com.example.staybooking.exception.UserNotExistException;
import com.example.staybooking.model.Authority;
import com.example.staybooking.model.Token;
import com.example.staybooking.model.User;
import com.example.staybooking.model.UserMode;
import com.example.staybooking.repository.AuthorityRepository;
import com.example.staybooking.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private AuthenticationManager authenticationManager;
    private AuthorityRepository authorityRepository;
    private JwtUtil jwtUtil;

    @Autowired
    public AuthenticationService(AuthenticationManager authenticationManager,
            AuthorityRepository authorityRepository,
            JwtUtil jwtUtil){
        this.authenticationManager=authenticationManager;
        this.authorityRepository=authorityRepository;
        this.jwtUtil=jwtUtil;
    }

    public Token authenticate(User user, UserMode userMode) throws UserNotExistException {
        /*
        * 1. check whether the username and password is in DB
        * 2. check whether the authority is matched
        * 3. generate Token
        * */
        //1. check whether the username and password is in DB
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
        }catch(AuthenticationException exception) {
            throw new UserNotExistException("User doesn't exist");
        }
        //2. check whether the authority is matched
        Authority authority=authorityRepository.getById(user.getUsername());
        if(!authority.getAuthority().equals(userMode.name())) {
            throw new UserNotExistException("User's authority is not matched");
        }
        //3. generate Token
        return new Token(jwtUtil.genToken(user.getUsername()));
    }

}
