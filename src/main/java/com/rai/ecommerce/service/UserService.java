package com.rai.ecommerce.service;

import com.rai.ecommerce.dto.ResponseDto;
import com.rai.ecommerce.dto.user.SignInDto;
import com.rai.ecommerce.dto.user.SignInResponseDto;
import com.rai.ecommerce.dto.user.SignupDto;
import com.rai.ecommerce.exceptions.AuthenticationFailException;
import com.rai.ecommerce.exceptions.CustomException;
import com.rai.ecommerce.model.AuthenticationToken;
import com.rai.ecommerce.model.User;
import com.rai.ecommerce.repository.UserRepository;
import org.aspectj.bridge.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationService authenticationService;

    @Transactional
    public ResponseDto signUp(SignupDto signupDto) {
        //first check if user is already present or not
        if (Objects.nonNull(userRepository.findByEmail(signupDto.getEmail()))) {
            //we have an user
            throw new CustomException("user already exist");
        }

        //hash the password
        String encryptedPassword = signupDto.getPassword();
        try {
            encryptedPassword = hashPassword(signupDto.getPassword());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        // save the user
        User user = new User(signupDto.getFirstName(), signupDto.getLastName(),
                signupDto.getEmail(), encryptedPassword);
        userRepository.save(user);

//        userRepository.findByEmail(signupDto.getEmail());

        final AuthenticationToken authenticationToken = new AuthenticationToken(user);
        authenticationService.saveConfirmationToken(authenticationToken);


        // and create the token

        ResponseDto responseDto = new ResponseDto("success", "User has been created successfully");
        return responseDto;
    }

    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        String hash = DatatypeConverter
                .printHexBinary(digest).toUpperCase();
        return hash;
    }

    public SignInResponseDto signIn(SignInDto signInDto) {
        //first we have to find the user by email

        User user = userRepository.findByEmail(signInDto.getEmail());
        if (Objects.isNull(user)) {
            throw new AuthenticationFailException("user is not valid");
        }

        //hash the password
        try {
            if (!user.getPassword().equals(hashPassword(signInDto.getPassword()))){
                throw new AuthenticationFailException("wrong password");
            }
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        //compare the password in database

        //if password gets match retrieve the token
        AuthenticationToken token = authenticationService.getToken(user);

        if(Objects.isNull(token)){
            throw new CustomException("token is not present");
        }
        return new SignInResponseDto("success", token.getToken());

        //return the response
    }
}
