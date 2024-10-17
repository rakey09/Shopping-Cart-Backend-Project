package com.rakey.EazyShop.service.cart;

import com.rakey.EazyShop.exceptions.ResourceNotFoundException;
import com.rakey.EazyShop.model.Cart;
import com.rakey.EazyShop.model.CartItem;
import com.rakey.EazyShop.repository.CartItemRepository;
import com.rakey.EazyShop.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
@Service
public class CartService implements ICartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;
    @Override
    public Cart getCart(Long id) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() ->new ResourceNotFoundException("Cart Not Found"));
        BigDecimal totalAmount = cart.getTotalAmount();
        cart.setTotalAmount(totalAmount);
        return cartRepository.save(cart);
    }

    @Override
    public void clearCart(Long id) {
        Cart cart = getCart(id);
        cartItemRepository.deleteAllByCartId(id);
        cart.getCartItems().clear();
        cartRepository.deleteById(id);

    }

    @Override
    public BigDecimal getTotalPrice(Long id) {

        Cart cart = getCart(id);
        return cart.getTotalAmount();

    }
}
