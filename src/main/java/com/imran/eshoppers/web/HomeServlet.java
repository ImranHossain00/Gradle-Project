package com.imran.eshoppers.web;


import com.imran.eshoppers.modeldto.ProductDTO;
import com.imran.eshoppers.repository.CartItemRepositoryImpl;
import com.imran.eshoppers.repository.CartRepositoryImpl;
import com.imran.eshoppers.repository.JdbcProductRepoImpl;
import com.imran.eshoppers.repository.ProductRepositoryImpl;
import com.imran.eshoppers.service.CartService;
import com.imran.eshoppers.service.CartServiceImpl;
import com.imran.eshoppers.service.ProductService;
import com.imran.eshoppers.service.ProductServiceImpl;
import com.imran.eshoppers.util.SecurityContext;
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

@WebServlet("/home")
public class HomeServlet extends HttpServlet {

    private final static Logger LOGGER
            = LoggerFactory.getLogger(HomeServlet.class);
    private final ProductService productService
            = new ProductServiceImpl(new ProductRepositoryImpl());

    private final CartService cartService
            = new CartServiceImpl(new CartRepositoryImpl(),
                                  new JdbcProductRepoImpl(),
                                  new CartItemRepositoryImpl());

    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp)
            throws ServletException, IOException {
        LOGGER.info("serving home page");

        final String attribute
                = req.getParameter("orderSuccess");
        if (attribute != null && Boolean.parseBoolean(attribute)) {
            req.setAttribute("message", "<strong>Congratulation!</strong> You're order has been placed successfully. ");
        }

        List<ProductDTO> allProducts
                =  productService.findAllProductSortByName();

        LOGGER.info("Total product found {}", allProducts.size());

//        Cart cart1
//                = cartService.
//                getCartByUser(SecurityContext.getCurrentUser(req));
        if (SecurityContext.isAuthenticated(req)) {
            var curUser
                    = SecurityContext.getCurrentUser(req);
            var cart
                    = cartService.getCartByUser(curUser);
            req.setAttribute("cart", cart);
        }

        req.setAttribute("products", allProducts);

        RequestDispatcher rd
                = req.getRequestDispatcher("/WEB-INF/home.jsp");

        rd.forward(req, resp);
    }
}
