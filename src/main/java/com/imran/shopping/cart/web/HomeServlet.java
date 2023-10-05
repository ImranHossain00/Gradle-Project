package com.imran.shopping.cart.web;

import com.imran.shopping.cart.dto.product.ProductDTO;
import com.imran.shopping.cart.repository.product.DummyProductRepositoryImpl;
import com.imran.shopping.cart.service.product.ProductService;
import com.imran.shopping.cart.service.product.ProductServiceImpl;
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

    private final ProductService productService = new ProductServiceImpl(new DummyProductRepositoryImpl());
    private final static Logger LOGGER = LoggerFactory.getLogger(HomeServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<ProductDTO> allProducts =  productService.findAllProductSortByName();
        req.setAttribute("products", allProducts);
        RequestDispatcher rd
                = req.getRequestDispatcher("/WEB-INF/home.jsp");
        rd.forward(req, resp);
    }
}
