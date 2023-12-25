package com.techCottage.servlets;

import com.techCottage.dto.LoginDTO;
import com.techCottage.utils.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final Logger LOGGER
            = LoggerFactory.getLogger(LoginServlet.class);
    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp)
            throws ServletException, IOException {

        LOGGER.info("serving Login page");
        req.getRequestDispatcher("/WEB-INF/login.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req,
                          HttpServletResponse resp)
            throws ServletException, IOException {
        // copying data from the client
        var dto = copy(req);
        // validating the client given data
        var errors = ValidationUtil.getInstance().validate(dto);

        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
            req.setAttribute("dto", dto);
            req.getRequestDispatcher("/WEB-INF/login.jsp")
                    .forward(req, resp);
        } else {
            LOGGER.info("Login successful. Redirecting to home page");
            resp.sendRedirect("/home");
        }
    }

    private LoginDTO copy(HttpServletRequest req) {
        var dto = new LoginDTO();
        dto.setPassword(req.getParameter("password"));
        dto.setEmail(req.getParameter("email"));

        return dto;
    }
}
