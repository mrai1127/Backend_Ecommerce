package com.rai.ecommerce.controller;

import com.rai.ecommerce.common.ApiResponse;
import com.rai.ecommerce.dto.cart.AddToCartDto;
import com.rai.ecommerce.dto.cart.CartDto;
import com.rai.ecommerce.model.Product;
import com.rai.ecommerce.model.User;
import com.rai.ecommerce.service.AuthenticationService;
import com.rai.ecommerce.service.CartService;
import com.rai.ecommerce.service.ProductService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private AuthenticationService authenticationService;

    //post cart api
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addToCart(@RequestBody AddToCartDto addToCartDto,
                                                 @RequestParam("token") String token){

        //authenticate the token
        authenticationService.authenticate(token);

        //find the user
        User user = authenticationService.getUser(token);

       cartService.addToCart(addToCartDto, user);

       return  new ResponseEntity<>(new ApiResponse(true, "Item has been added to cart"), HttpStatus.CREATED);

    }

    //get all cart items for user
    @GetMapping("/")
    public ResponseEntity<CartDto> getCartItems(@RequestParam("token") String token){

        //authenticate the token
        authenticationService.authenticate(token);

        //find the user
        User user = authenticationService.getUser(token);

        //get cart items
        CartDto cartDto = cartService.listCartItems(user);

        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }

    //delete cart item for user

    @DeleteMapping("/delete/{cartItemId}")
    public ResponseEntity<ApiResponse> deleteCartItem(@PathVariable("cartItemId") Integer itemId,
                                                      @RequestParam("token")String token){
        //authenticate the token
        authenticationService.authenticate(token);

        //find the user
        User user = authenticationService.getUser(token);

        cartService.deleteCartItem(itemId, user);
        return new ResponseEntity<>(new ApiResponse(true, "Deleted from the cart"), HttpStatus.OK);
    }
}
