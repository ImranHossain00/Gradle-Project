package com.imran.eshoppers.repository;

import com.imran.eshoppers.domain.CartItem;

public interface CartItemRepository {
    CartItem update(CartItem cartItem);

    CartItem save(CartItem cartItem);

    void remove(CartItem cartItem);
}
