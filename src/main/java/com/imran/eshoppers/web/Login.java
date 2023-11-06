package com.imran.eshoppers.web;

import com.imran.eshoppers.exception.UserNotFoundExcetion;
import com.imran.eshoppers.modeldto.LoginDTO;
import com.imran.eshoppers.repository.UserRepositoryImpl;
import com.imran.eshoppers.service.UserService;
import com.imran.eshoppers.service.UserServiceImpl;
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


@WebServlet("/login")
public class Login extends HttpServlet {
    private static final Logger LOGGER
            = LoggerFactory.getLogger(Login.class);

    private final UserService userService
            = new UserServiceImpl(new UserRepositoryImpl());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.info("serving login page");
        String logout = req.getParameter("logout");

        if (logout != null && Boolean.parseBoolean(logout)) {
            req.setAttribute("message", "you have been seccessfully logged out.");
        }
        RequestDispatcher rd
                = req.getRequestDispatcher("/WEB-INF/login.jsp");
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req,
                          HttpServletResponse resp)
            throws ServletException, IOException {
        var loginDTO
                = new LoginDTO(req.getParameter("username"),
                req.getParameter("password"));

        LOGGER.info("Received login Data: {}", loginDTO);

        var errors
                = ValidationUtil.getInstance().validate(loginDTO);

        if (!errors.isEmpty()) {
            LOGGER.info("Failed to login");
            req.setAttribute("errors", errors);
            RequestDispatcher rd
                    = req.getRequestDispatcher("/WEB-INF/login.jsp");
            rd.forward(req, resp);
        }

        try {
            login(loginDTO, req);

            LOGGER.info("Login Successful, redirecting to home page");
            resp.sendRedirect("/home");
        } catch (UserNotFoundExcetion e) {
            LOGGER.error("incorrect username/password", e);
            errors.put("username", "Incorrect username/password");
            req.setAttribute("errors", errors);
            RequestDispatcher rd
                    = req.getRequestDispatcher("/WEB-INF/login.jsp");
            rd.forward(req, resp);
        }
    }

    private void login(LoginDTO loginDTO, HttpServletRequest req)
            throws UserNotFoundExcetion {
        var user = userService.verifyUser(loginDTO);
        SecurityContext.login(req, user);
    }
}
