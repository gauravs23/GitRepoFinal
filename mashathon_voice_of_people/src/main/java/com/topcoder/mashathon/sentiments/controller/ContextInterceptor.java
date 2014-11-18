/*
 * Copyright (C) 2014 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.mashathon.sentiments.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.google.appengine.api.users.UserServiceFactory;

/**
 * <p>
 * ContextInterceptor class for storing context specific data like logout url. This class extends BaseInterceptor class.
 * </p>
 * 
 * @author TCSASSEMBLER
 * @version 1.0
 */
public class ContextInterceptor extends BaseInterceptor {

    /**
     * Empty Constructor.
     */
    public ContextInterceptor() {
        // Empty Constructor
    }

    /**
     * This method stores the Logout URL in the context variable to be available for reading in JSP.
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) {
        // Generate logout URL
        String requestURI = request.getRequestURI();
        String logOutURL = UserServiceFactory.getUserService().createLogoutURL(requestURI);
        System.out.println("modelAndView : " + modelAndView);
        if (modelAndView != null) {
            modelAndView.addObject("logoutURL", logOutURL);
        }
    }
}
