/*
 * Copyright (C) 2014 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.mashathon.sentiments.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

/**
 * <p>
 * Authentication Interceptor class which extends BaseInterceptor class. This class implements methods to check whether
 * the user is authenticated or not.
 * </p>
 * <p>
 * If the user is authenticated, the request is sent forward to the chain, and if user is not authenticated, it
 * redirects the user to login page.
 * </p>
 * 
 * @author TCSASSEMBLER
 * @version 1.0
 */
@Component
public class AuthenticationInterceptor extends BaseInterceptor {

    /**
     * Empty Constructor.
     */
    public AuthenticationInterceptor() {
        // Empty Constructor
    }

    /**
     * This method checks whether the user is authenticated or not and return true in case of authenticated user and
     * redirection to Login Page in case of unauthenticated user.
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws IOException {
        
        String doLogin = request.getParameter("dologin");
        System.out.println("doLogin : "+doLogin);
        UserService userService = UserServiceFactory.getUserService();
        if (userService.isUserLoggedIn()) {
            return true;
        } else {
            if(doLogin != null && doLogin.equalsIgnoreCase("true")) {
                // redirect to login page
                response.sendRedirect(userService.createLoginURL(request.getRequestURI()));
                return false;
            } else {
                return true;
            }
        }
    }
}
