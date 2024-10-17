package com.rakey.EazyShop.service.cart;

import com.rakey.EazyShop.model.Cart;

import java.math.BigDecimal;

public interface ICartService {

    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);
}
