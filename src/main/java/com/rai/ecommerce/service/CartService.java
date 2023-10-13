package com.rai.ecommerce.service;

import com.rai.ecommerce.dto.cart.AddToCartDto;
import com.rai.ecommerce.dto.cart.CartDto;
import com.rai.ecommerce.dto.cart.CartItemDto;
import com.rai.ecommerce.exceptions.CustomException;
import com.rai.ecommerce.model.Cart;
import com.rai.ecommerce.model.Product;
import com.rai.ecommerce.model.User;
import com.rai.ecommerce.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    ProductService productService;

    @Autowired
    CartRepository cartRepository;

    public void addToCart(AddToCartDto addToCartDto, User user) {

        //validate if the product id is valid
        Product product = productService.findById(addToCartDto.getProductId());

        Cart cart = new Cart();
        cart.setProduct(product);
        cart.setUser(user);
        cart.setQuantity(addToCartDto.getQuantity());
        cart.setCreatedDate(new Date());
        cartRepository.save(cart);


        // save the cart

        //
    }

    public CartDto listCartItems(User user) {
        List<Cart> cartList = cartRepository.findAllByUserOrderByCreatedDateDesc(user);
        //changing from Cart to CartDto
        List<CartItemDto> cartItems = new ArrayList<>();
        double totalCost = 0;
        for(Cart cart : cartList){
                CartItemDto cartItemDto = new CartItemDto(cart);
                totalCost += cartItemDto.getQuantity() * cart.getProduct().getPrice();
                cartItems.add(cartItemDto);
        }
        CartDto cartDto = new CartDto();
        cartDto.setTotalCost(totalCost);
        cartDto.setCartItems(cartItems);
        return cartDto;
    }

    public void deleteCartItem(Integer cartItemId, User user) {
        // check the item id belongs to the user

        Optional<Cart> optionalCart = cartRepository.findById(cartItemId);

        if(optionalCart.equals(Optional.empty())){
            throw new CustomException("cart item id is invalid " + cartItemId);
        }
        Cart cart = optionalCart.get();

        if(cart.getUser() != user){
            throw new CustomException("cart item does not belong to user : " + cartItemId);
        }
        cartRepository.delete(cart);
    }
}
