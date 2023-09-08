package com.rai.ecommerce.exceptions;

public class ProductNotFoundException extends IllegalArgumentException {
    public ProductNotFoundException(String mgs) {
        super(mgs);
    }
}
