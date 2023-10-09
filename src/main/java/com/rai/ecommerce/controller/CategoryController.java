package com.rai.ecommerce.controller;


import com.rai.ecommerce.common.ApiResponse;
import com.rai.ecommerce.exceptions.CustomException;
import com.rai.ecommerce.model.Category;
import com.rai.ecommerce.model.User;
import com.rai.ecommerce.service.AuthenticationService;
import com.rai.ecommerce.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createCategory(@RequestBody Category category){
        categoryService.createCategory(category);
        return new ResponseEntity<>(new ApiResponse(true, "New Category has been created"), HttpStatus.CREATED);
    }

    @GetMapping("/")
    public List<Category> listCategory(){
        return categoryService.listCategory();
    }

    @PostMapping("/update/{categoryId}")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable("categoryId") int categoryId, @RequestBody Category category){
        System.out.println("category id is : " + categoryId);
        if(!categoryService.findById(categoryId)){
            return new ResponseEntity<>(new ApiResponse(false, "category has been not been found"), HttpStatus.NOT_FOUND);
        }
        categoryService.editCategory(categoryId, category);
       return new ResponseEntity<>(new ApiResponse(true, "category has been updated"), HttpStatus.OK);
    }
//    @DeleteMapping("/delete/{categoryId}")
//    public ResponseEntity<ApiResponse> deleteCartItem(@PathVariable("categoryId") Integer categoryId){
//        try {
//            categoryService.deleteCategoryId(categoryId);
//            return new ResponseEntity<>(new ApiResponse(true, "category has been deleted successfully"), HttpStatus.OK);
//        }catch ( CustomException e) {
//            return new ResponseEntity <>( new ApiResponse(true, "Category not found"), HttpStatus.NOT_FOUND);
//        } catch (Exception e) {
//           return new ResponseEntity <>( new ApiResponse(true, "An error occurred"), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
}
