package com.techCottage.servlets;

import com.techCottage.dto.SignUpDTO;
import com.techCottage.repositories.UserRepoImpl;
import com.techCottage.services.UserService;
import com.techCottage.services.UserServiceImpl;
import com.techCottage.utils.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/signup")
public class SignupServlet extends HttpServlet {

    private static final Logger LOGGER
            = LoggerFactory.getLogger(SignupServlet.class);
    private UserService userService
            = new UserServiceImpl(new UserRepoImpl());
    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/signup.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req,
                          HttpServletResponse resp)
            throws ServletException, IOException {
        var dto = copy(req);
        var errors = ValidationUtil.getInstance().validate(dto);

        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
            req.setAttribute("dto", dto);
            req.getRequestDispatcher("/WEB-INF/signup.jsp")
                    .forward(req, resp);
        } else if (userService.emailUnique(dto)) {
            errors.put("email", "Email is already taken try another");
            req.setAttribute("errors", errors);
            req.setAttribute("dto", dto);
            req.getRequestDispatcher("/WEB-INF/signup.jsp")
                    .forward(req, resp);
        } else {
            LOGGER.info("Sign up is successful redirecting to login page");
            userService.saveUser(dto);
            resp.sendRedirect("/login");
        }
    }

    private SignUpDTO copy(HttpServletRequest req) {
        var dto = new SignUpDTO();
        dto.setFirstName(req.getParameter("firstName"));
        dto.setLastName(req.getParameter("lastName"));
        dto.setEmail(req.getParameter("email"));
        dto.setPassword(req.getParameter("password"));
        dto.setConfirmPassword(req.getParameter("confirmPassword"));

        return dto;
    }
}
