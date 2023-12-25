package com.techCottage.servlets;

import com.techCottage.dto.ProductDTO;
import com.techCottage.repositories.DummyProductRepoImpl;
import com.techCottage.services.ProductService;
import com.techCottage.services.ProductServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

// This servlet class is responsible for handling requests from the client side using home.jsp
// and sends the proper responses
@WebServlet("/home")
public class HomeServlet extends HttpServlet {


    private static final Logger LOGGER
            = LoggerFactory.getLogger(HomeServlet.class);
    private static ProductService productService
            = new ProductServiceImpl(new DummyProductRepoImpl());
    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp)
            throws ServletException, IOException {

        LOGGER.debug("Serving Home servlet");

        List<ProductDTO> products
                = productService.findAllProductSortedByName();
        req.setAttribute("products", products);
        req.getRequestDispatcher("/WEB-INF/home.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req,
                          HttpServletResponse resp)
            throws ServletException, IOException {

        req.getRequestDispatcher("/WEB-INF/home.jsp")
                .forward(req, resp);
    }
}
