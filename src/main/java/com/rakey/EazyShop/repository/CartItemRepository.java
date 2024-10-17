package com.rakey.EazyShop.repository;

import com.rakey.EazyShop.model.Cart;
import com.rakey.EazyShop.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem ,Long> {
    void deleteAllByCartId(Long id);
}
