package com.rakey.EazyShop.service.cart;

import com.rakey.EazyShop.dto.CartDto;
import com.rakey.EazyShop.model.Cart;
import com.rakey.EazyShop.model.User;

import java.math.BigDecimal;

public interface ICartService {

    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);

    Cart initializationCart(User user);

    Cart getCartByUserId(Long userId);


}
