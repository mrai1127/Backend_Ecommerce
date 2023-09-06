package com.rai.ecommerce.service;

import com.rai.ecommerce.dto.ProductDto;
import com.rai.ecommerce.model.User;
import com.rai.ecommerce.model.WishList;
import com.rai.ecommerce.repository.WishListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WishListService {

    @Autowired
    WishListRepository wishListRepository;

    @Autowired
    ProductService productService;

    public void createWishList(WishList wishList) {
        wishListRepository.save(wishList);
    }

    public List<ProductDto> getWishListForUser(User user) {
       final List<WishList> wishLists = wishListRepository.findAllByUserOrderByCreatedDateDesc(user);
       List<ProductDto> productsDtos = new ArrayList<>();
       for(WishList wishList : wishLists){
           productsDtos.add(productService.getProductDto(wishList.getProduct()));
       }
       return productsDtos;
    }
}
