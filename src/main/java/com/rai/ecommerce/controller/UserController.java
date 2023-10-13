package com.rai.ecommerce.controller;


import com.rai.ecommerce.dto.ResponseDto;
import com.rai.ecommerce.dto.user.SignInDto;
import com.rai.ecommerce.dto.user.SignInResponseDto;
import com.rai.ecommerce.dto.user.SignupDto;
import com.rai.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    //two apis

    @Autowired
    UserService userService;

    //signup
    @PostMapping("/signup")
    public ResponseDto signup(@RequestBody SignupDto signupDto){
        return userService.signUp(signupDto);
    }

    //signin
    @PostMapping("/signin")
    public SignInResponseDto signIn(@RequestBody SignInDto signInDto){
        return userService.signIn(signInDto);
    }
}
