package com.rakey.EazyShop.controller;

import com.rakey.EazyShop.exceptions.ResourceNotFoundException;
import com.rakey.EazyShop.response.ApiResponse;
import com.rakey.EazyShop.service.cart.ICartItemService;
import com.rakey.EazyShop.service.cart.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("${api.prefix}/cartItems")
public class CartItemController {

    @Autowired
    private ICartItemService cartItemService;

    @Autowired
    private ICartService cartService;

    @PostMapping("/item/add")
    public ResponseEntity<ApiResponse> addItemToCart(
            @RequestParam(required = false) Long cartId,
            @RequestParam Long productId,
            @RequestParam Integer quantity
    ){
        try {
            if (cartId == null){
                cartId = cartService.initializationCart();
            }
            cartItemService.addItemToCart(cartId,productId,quantity );
            return ResponseEntity.ok(new ApiResponse("Add Item to cart",null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/cart/{cartId}/item/{productId}/remove")
    public ResponseEntity<ApiResponse> removeItemFromCart(
            @PathVariable Long cartId,
            @PathVariable Long productId
    ){
        try {
            cartItemService.removeItemFormCart(cartId,productId);
            return ResponseEntity.ok(new ApiResponse("Remove Item From cart",null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/cart/{cartId}/item/{productId}/update")
    public ResponseEntity<ApiResponse> updateItemQuantity(
            @PathVariable Long cartId,
            @PathVariable Long productId,
            @RequestParam Integer quantity
    ){
        try {
            cartItemService.UpdateItemQuantity(cartId,productId,quantity);
            return ResponseEntity.ok(new ApiResponse("Updated Successfully",null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }

    }
}
