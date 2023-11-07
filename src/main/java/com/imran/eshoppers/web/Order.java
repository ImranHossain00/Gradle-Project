package com.imran.eshoppers.web;

import com.imran.eshoppers.exception.CartItemNotFoundException;
import com.imran.eshoppers.modeldto.ShippingAddressDTO;
import com.imran.eshoppers.repository.*;
import com.imran.eshoppers.service.CartService;
import com.imran.eshoppers.service.CartServiceImpl;
import com.imran.eshoppers.service.OrderService;
import com.imran.eshoppers.service.OrderServiceImpl;
import com.imran.eshoppers.util.SecurityContext;
import com.imran.eshoppers.util.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/order")
public class Order extends HttpServlet {
    private static final Logger LOGGER
            = LoggerFactory.getLogger(Order.class);

    private CartService cartService
            = new CartServiceImpl(new CartRepositoryImpl(),
                                  new JdbcProductRepoImpl(),
                                  new JdbcCartItemRepoImpl());
    private OrderService orderService
            = new OrderServiceImpl(new OrderRepositoryImpl(),
                                   new ShippingAddressRepositoryImpl(),
                                   new CartRepositoryImpl());

    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp)
            throws ServletException, IOException {

        addCartToUi(req);
        req.setAttribute("countries", getCountries());

        RequestDispatcher rd
                = req.getRequestDispatcher("/WEB-INF/order.jsp");
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req,
                          HttpServletResponse resp)
            throws ServletException, IOException {

        LOGGER.info("Handle order request form");

        var shippingAddress
                = copyParametersTo(req);

        LOGGER.info("Shipping address information {}", shippingAddress);

        var errors
                = ValidationUtil.
                getInstance().
                validate(shippingAddress);

        if (!errors.isEmpty()) {
            req.setAttribute("countries", getCountries());
            req.setAttribute("errors", errors);
            req.setAttribute("shippingAddress", shippingAddress);
            addCartToUi(req);

            req.
                    getRequestDispatcher("/WEB-INF/order.jsp").
                    forward(req, resp);
        } else {
            try {
                orderService.processOrder(shippingAddress, SecurityContext.getCurrentUser(req));
            } catch (CartItemNotFoundException e) {
                throw new RuntimeException(e);
            }
            resp.sendRedirect("/home?orderSuccess=true");
        }
    }

    private ShippingAddressDTO copyParametersTo(HttpServletRequest req) {
        var shippingAddress = new ShippingAddressDTO();

        shippingAddress.setAddress(req.getParameter("address"));
        shippingAddress.setAddress2(req.getParameter("address2"));
        shippingAddress.setState(req.getParameter("state"));
        shippingAddress.setZip(req.getParameter("zip"));
        shippingAddress.setCountry(req.getParameter("country"));
        shippingAddress.setMobileNumber(req.getParameter("mobileNumber"));

        return shippingAddress;
    }

    private void addCartToUi(HttpServletRequest req) {
        if (SecurityContext.isAuthenticated(req)) {
            var curUser
                    = SecurityContext.getCurrentUser(req);
            var cart
                    = cartService.getCartByUser(curUser);
            req.setAttribute("cart", cart);
        }
    }

    private List<String> getCountries() {
        return List.of("Bangladesh", "Switzerland", "Japan", "USA", "Canada");
    }


}
