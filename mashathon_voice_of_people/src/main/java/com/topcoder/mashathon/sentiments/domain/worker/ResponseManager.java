/*
 * Copyright (C) 2014 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.mashathon.sentiments.domain.worker;

import com.topcoder.mashathon.sentiments.model.Response;


/**
 * Defines the properties (configuration) interface for this application.
 * 
 * @version 1.0
 * @since 1.0
 * @author TCASSEMBLER
 */
public interface ResponseManager {

    static final String DEFAULT_QUESTION_TEXT = "";
    
    static final String DEFAULT_QUESTION_STATUS = "nil";
    
    void calculateSentiment(Response response);

}
