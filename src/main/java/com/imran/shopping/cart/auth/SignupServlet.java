package com.imran.shopping.cart.auth;

import com.imran.shopping.cart.repository.user.UserRepositoryImpl;
import com.imran.shopping.cart.service.user.UserService;
import com.imran.shopping.cart.service.user.UserSeviceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/signup")
public class SignupServlet extends HttpServlet {
    private final static Logger LOGGER
            = LoggerFactory.getLogger(SignupServlet.class);
    private UserService userService
            = new UserSeviceImpl(new UserRepositoryImpl());

    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp)
            throws ServletException, IOException {
        LOGGER.info("serving signup page");

        RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/signup.jsp");
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req,
                          HttpServletResponse resp)
            throws ServletException, IOException {

    }
}
