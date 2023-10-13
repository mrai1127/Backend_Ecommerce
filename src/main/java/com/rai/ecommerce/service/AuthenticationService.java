package com.rai.ecommerce.service;

import com.rai.ecommerce.exceptions.AuthenticationFailException;
import com.rai.ecommerce.model.AuthenticationToken;
import com.rai.ecommerce.model.User;
import com.rai.ecommerce.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthenticationService {

    @Autowired
    TokenRepository tokenRepository;


    public void saveConfirmationToken(AuthenticationToken authenticationToken) {
        tokenRepository.save(authenticationToken);
    }

    public AuthenticationToken getToken(User user) {
        return tokenRepository.findByUser(user);
    }

    public User getUser(String token){
      final AuthenticationToken authenticationToken = tokenRepository.findByToken(token);
      if(Objects.isNull(authenticationToken)){
          return null;
      }
      //authenticationToken is not null
        return authenticationToken.getUser();
    }

    public void authenticate(String token) throws AuthenticationFailException{
        if(Objects.isNull(token)){
            //throw an exception
            throw new AuthenticationFailException("token is not present");
        }
        if(Objects.isNull(getUser(token))){
            throw new AuthenticationFailException("token not valid");
        }
    }
}
