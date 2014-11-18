/*
 * Copyright (C) 2014 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.mashathon.sentiments.domain.worker;

import java.util.List;

import com.topcoder.mashathon.sentiments.model.Response;


/**
 * Defines the properties (configuration) interface for this application.
 * 
 * @version 1.0
 * @since 1.0
 * @author TCASSEMBLER
 */
public interface AppProperties {

    static final String DEFAULT_QUESTION_TEXT = "";
    
    static final String DEFAULT_QUESTION_STATUS = "nil";
    
    String getQuestionText();

    String getQuestionStatus();
    
    void setQuestionText(String questionText);
    
    void setQuestionStatus(String questionStatus);
    
    List<Response> getResponseList();
    
    void saveResponse(Response response);
}
