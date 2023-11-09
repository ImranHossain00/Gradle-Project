package com.imran.eshoppers.web;

import com.imran.eshoppers.domain.Cart;
import com.imran.eshoppers.domain.User;
import com.imran.eshoppers.exception.CartItemNotFoundException;
import com.imran.eshoppers.exception.ProductNotFoundException;
import com.imran.eshoppers.repository.JdbcCartItemRepoImpl;
import com.imran.eshoppers.repository.JdbcCartRepoImpl;
import com.imran.eshoppers.repository.JdbcProductRepoImpl;
import com.imran.eshoppers.service.CartService;
import com.imran.eshoppers.service.CartServiceImpl;
import com.imran.eshoppers.util.SecurityContext;
import com.imran.eshoppers.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/add-to-cart")
public class CartServlet extends HttpServlet {
    private static final Logger LOGGER
            = LoggerFactory.getLogger(CartServlet.class);

    private final CartService cartService
            = new CartServiceImpl(new JdbcCartRepoImpl(),
                                  new JdbcProductRepoImpl(),
                                  new JdbcCartItemRepoImpl());

    @Override
    protected void doPost(HttpServletRequest req,
                          HttpServletResponse resp)
            throws ServletException, IOException {

        var productId = req.getParameter("productId");
        var action = req.getParameter("action");
        var cart = getCart(req);

        if (StringUtil.isNotEmpty(action)) {
            try {
                processCart(productId, action, cart);
            } catch (CartItemNotFoundException | ProductNotFoundException e) {
                throw new RuntimeException(e);
            }

            resp.sendRedirect("/checkout");
            return;
        }

        LOGGER.info("Received request to add product with id {}", productId);

        try {
            cartService.addProductToCart(productId, cart);
        } catch (ProductNotFoundException e) {
            throw new RuntimeException(e);
        }

        resp.sendRedirect("/home");
    }

    private void processCart(String productId,
                             String action,
                             Cart cart)
            throws CartItemNotFoundException,
                   ProductNotFoundException {

        switch (Action.valueOf(action.toUpperCase())) {
            case ADD -> {
                LOGGER.info("Received request to add product with id: {} to cart", productId);
                try {
                    cartService.addProductToCart(productId, cart);
                } catch (ProductNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
            case REMOVE -> {
                LOGGER.info("Received request to remove product with id: {} to cart", productId);
                cartService.removeProductToCart(productId, cart);
            }
        }
    }

    private Cart getCart(HttpServletRequest req) {
        final User curUser
                = SecurityContext.getCurrentUser(req);

        return cartService.getCartByUser(curUser);
    }

    public enum Action
    {
        ADD,
        REMOVE
    }
}
