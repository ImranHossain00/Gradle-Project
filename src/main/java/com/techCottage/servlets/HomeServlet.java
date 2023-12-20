package com.techCottage.servlets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {

    private static final Logger LOGGER
            = LoggerFactory.getLogger(HomeServlet.class);
    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp)
            throws ServletException, IOException {

        LOGGER.info("Serving Home servlet");
        req.getRequestDispatcher("/WEB-INF/home.jsp")
                .forward(req, resp);
    }
}
