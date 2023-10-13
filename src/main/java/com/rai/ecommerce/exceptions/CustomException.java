package com.rai.ecommerce.exceptions;

public class CustomException extends IllegalArgumentException{
    public CustomException(String mgs){
        super(mgs);
    }
}
