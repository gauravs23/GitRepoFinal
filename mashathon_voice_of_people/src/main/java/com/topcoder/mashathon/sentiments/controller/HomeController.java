/*
 * Copyright (C) 2014 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.mashathon.sentiments.controller;

import javax.annotation.PostConstruct;
import javax.naming.ConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;
import com.topcoder.mashathon.sentiments.domain.user.UserRepository;

/**
 * <p>
 * Spring MVC Controller for testing the Services.
 * </p>
 * 
 * @author TCSASSEMBLER
 * @version 1.0
 */
@Controller
public class HomeController {

    private static final String ALLOWED_DOMAIN = "appirio-dev3.com";
    
    /**
     * User Repository instance.
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * <p>
     * Empty constructor.
     * </p>
     */
    public HomeController() {
        // does nothing
    }

    /**
     * <p>
     * Checks if all required fields are initialized properly.
     * </p>
     * 
     * @throws ConfigurationException
     *             if any required field is not initialized properly.
     */
    @PostConstruct
    protected void checkConfiguration() {

    }

    
    /**
     * <p>
     * This method processes the request to display index page.
     * </p>
     * 
     * @param httpServletResponse
     *            the servlet response.
     */
    @RequestMapping(value = { "/", "/home" }, method = RequestMethod.GET)
    public String index(ModelMap model) {
        User currentUser = UserServiceFactory.getUserService().getCurrentUser();
        if (currentUser != null) {
            String currentUserEmail = currentUser.getEmail();
            if(currentUserEmail.toLowerCase().contains(ALLOWED_DOMAIN)) {
                if (userRepository.isUserSuperAdmin(currentUserEmail)) {
                    model.addAttribute("superAdmin", "true");
                }
                model.addAttribute("currentUserEmail", currentUser.getEmail());
                return "home";
            } else {
                return "forbidden";
            }
        } else {
            return "login";
        }
    }

}
