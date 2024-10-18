package com.rakey.EazyShop.service.cart;

import com.rakey.EazyShop.model.CartItem;

public interface ICartItemService {

    void addItemToCart(Long cartId,Long productId, int quantity);
    void removeItemFormCart(Long cartId,Long productId);
    void UpdateItemQuantity(Long cartId,Long productId, int quantity);

    CartItem getCartItem(Long cartId, Long productId);
}
