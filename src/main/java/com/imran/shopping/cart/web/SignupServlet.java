package com.imran.shopping.cart.web;

import com.imran.shopping.cart.dto.user.UserDTO;
import com.imran.shopping.cart.repository.user.UserRepositoryImpl;
import com.imran.shopping.cart.service.user.UserService;
import com.imran.shopping.cart.service.user.UserSeviceImpl;
import com.imran.shopping.cart.util.ValidationUtil;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
        UserDTO userDTO = copyParameters(req);
        var errors = ValidationUtil.getInstance().validate(userDTO);
        if (errors.isEmpty()) {
            LOGGER.info(
                    "user is valid, creating a new user with: {}",
                    userDTO
            );
            userService.saveUser(userDTO);
            resp.sendRedirect("/home");
        } else {
            req.setAttribute("errors", errors);
            LOGGER.info("User sent invalid data: {}", userDTO);
            RequestDispatcher rd
                    = req.getRequestDispatcher("/WEB-INF/signup.jsp");
            rd.forward(req, resp);
        }
    }

    private UserDTO copyParameters(HttpServletRequest req) {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(req.getParameter("email"));
        userDTO.setFirstName(req.getParameter("firstName"));
        userDTO.setLastName(req.getParameter("lastName"));
        userDTO.setPassword(req.getParameter("password"));
        userDTO.setPasswordConfirmed(req.getParameter("passwordConfirmed"));
        userDTO.setUsername(req.getParameter("username"));
        return userDTO;
    }
}
