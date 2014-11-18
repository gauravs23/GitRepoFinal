/*
 * Copyright (C) 2014 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.mashathon.sentiments.controller;

import java.util.logging.Logger;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * <p>
 * Base Spring MVC Interceptor abstract class for intercepting the URLs. This class extends the Spring provided
 * HandlerInterceptorAdapter interface.
 * </p>
 * 
 * @author TCSASSEMBLER
 * @version 1.0
 */
public abstract class BaseInterceptor extends HandlerInterceptorAdapter {
    
    /**
     * Logger instance.
     */
    private final Logger logger = Logger.getLogger(getClass().getName());
    
    /**
     * Empty Constructor.
     */
    protected BaseInterceptor() {
        // Does nothing
    }
    
    /**
     * Method to return the logger instance.
     * @return the Logger instance.
     */
    protected Logger getLogger() {
        return logger;
    }

}
