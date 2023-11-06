package com.imran.eshoppers.util;

import com.imran.eshoppers.domain.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SecurityContext {
    public static final String AUTHENTICATION_KEY = "auth.Key";
    public static void login(HttpServletRequest request,
                             User user) {
        HttpSession oldSession = request.getSession(false);
        if (oldSession != null)
            oldSession.invalidate();
        HttpSession session = request.getSession(true);
        session.setAttribute(AUTHENTICATION_KEY, user);
    }

    public static void logout(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        session.removeAttribute(AUTHENTICATION_KEY);
    }

    // if isAuthenticated method say its true that a user is logged in
    // then it will give us the user ,or we can get the user.
    public static User getCurrentUser(HttpServletRequest request) {
        HttpSession session = request.getSession(true);

        return (User) session.getAttribute(AUTHENTICATION_KEY);
    }

    // by this method we can identify is a user logged in or not.
    public static boolean isAuthenticated(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        return session.getAttribute(AUTHENTICATION_KEY) != null;
    }
}
