package eshoppers.web;

import com.imran.eshoppers.util.SecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/logout")
public class Logout extends HttpServlet {
    private static final Logger LOGGER
            = LoggerFactory.getLogger(Logout.class);

    @Override
    protected void doPost(HttpServletRequest req,
                          HttpServletResponse resp)
            throws ServletException, IOException {
        LOGGER.info("Logging out");
        SecurityContext.logout(req);
        resp.sendRedirect("/login?logout=true");
    }
}
