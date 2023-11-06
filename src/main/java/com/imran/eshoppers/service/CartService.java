package eshoppers.service;

import com.imran.eshoppers.domain.Cart;
import com.imran.eshoppers.domain.User;
import com.imran.eshoppers.exception.CartItemNotFoundException;
import com.imran.eshoppers.exception.ProductNotFoundException;

public interface CartService {

    Cart getCartByUser(User curUser);

    public void addProductToCart(String productId, Cart cart) throws ProductNotFoundException;

    void removeProductToCart(String productId, Cart cart) throws ProductNotFoundException, CartItemNotFoundException;
}
