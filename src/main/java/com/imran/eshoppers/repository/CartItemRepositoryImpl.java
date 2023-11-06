package com.imran.eshoppers.repository;

import com.imran.eshoppers.domain.CartItem;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class CartItemRepositoryImpl implements CartItemRepository {

    // Here CopyOnWriteArraySet implements to Set
    // which is a thread safe implementation. Ref page no. 176
    private static final Set<CartItem> CART_ITEMS
            = new CopyOnWriteArraySet<>();
    @Override
    public CartItem save(CartItem cartItem) {
        CART_ITEMS.add(cartItem);
        return cartItem;
    }

    @Override
    public void remove(CartItem cartItem) {
        CART_ITEMS.remove(cartItem);
    }

    @Override
    public CartItem update(CartItem cartItem) {
        CART_ITEMS.add(cartItem);
        return cartItem;
    }
}
