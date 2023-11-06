package eshoppers.web;

import com.imran.eshoppers.modeldto.UserDTO;
import com.imran.eshoppers.repository.UserRepositoryImpl;
import com.imran.eshoppers.service.UserService;
import com.imran.eshoppers.service.UserServiceImpl;
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

@WebServlet("/signup")
public class SignUp extends HttpServlet {
    private final static Logger LOGGER
            = LoggerFactory.getLogger(SignUp.class);

    private final UserService userService
            = new UserServiceImpl(new UserRepositoryImpl());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.info("serving signup page");

        RequestDispatcher rd
                = req.getRequestDispatcher("/WEB-INF/signup.jsp");
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var userDTO = copyParameters(req);
        var errors = ValidationUtil.getInstance().validate(userDTO);

        if (!errors.isEmpty()) {
            req.setAttribute("userDto", userDTO);
            req.setAttribute("errors", errors);
            LOGGER.info("User sent invalid data: {}", userDTO);
            RequestDispatcher rd
                    = req.getRequestDispatcher("/WEB-INF/signup.jsp");
            rd.forward(req, resp);
        } else if (userService.isNotUniqueUsername(userDTO)) {
            LOGGER.info("Username: {} is already exist", userDTO.getUsername());
            errors.put("username", "The username already exists.");
            req.setAttribute("userDto", userDTO);
            req.setAttribute("errors", errors);
            RequestDispatcher rd
                    = req.getRequestDispatcher("/WEB-INF/signup.jsp");
            rd.forward(req, resp);
        } else if (userService.isNotUniqueEmail(userDTO)) {
            LOGGER.info("Email: {} is already exist", userDTO.getEmail());
            errors.put("email", "The email already taken.");
            req.setAttribute("userDto", userDTO);
            req.setAttribute("errors", errors);
            RequestDispatcher rd
                    = req.getRequestDispatcher("/WEB-INF/signup.jsp");
            rd.forward(req, resp);
        } else {
            LOGGER.info("User is valid");
            userService.saveUser(userDTO);
            resp.sendRedirect("/login");
        }
    }

    private UserDTO copyParameters(HttpServletRequest req) {
        var userDTO = new UserDTO();
        userDTO.setFirstName(req.getParameter("firstName"));
        userDTO.setLastName(req.getParameter("lastName"));
        userDTO.setPassword(req.getParameter("password"));
        userDTO.setPasswordConfirmed(req.getParameter("passwordConfirmed"));
        userDTO.setEmail(req.getParameter("email"));
        userDTO.setUsername(req.getParameter("username"));

        return userDTO;
    }
}
