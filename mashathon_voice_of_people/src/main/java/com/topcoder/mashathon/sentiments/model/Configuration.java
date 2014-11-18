/*
 * Copyright (C) 2014 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.mashathon.sentiments.model;

import java.util.List;

/**
 * Class that encapsulates the current configuration of the application.
 * 
 * @version 1.0
 * @since 1.0
 * @author TCASSEMBLER
 */
public class Configuration {

    private String questionText;
    private String questionStatus;

    private List<Response> responsesList;
    private int totalResponses;
    private int totalPostiveResponses;
    private int totalNegativeResponses;
    private int totalNeutralResponses;
    
    /** Default constructor. */
    public Configuration() {
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getQuestionStatus() {
        return questionStatus;
    }

    public void setQuestionStatus(String questionStatus) {
        this.questionStatus = questionStatus;
    }

    public List<Response> getResponsesList() {
        return responsesList;
    }

    public void setResponsesList(List<Response> responsesList) {
        this.responsesList = responsesList;
    }

    public int getTotalResponses() {
        return totalResponses;
    }

    public void setTotalResponses(int totalResponses) {
        this.totalResponses = totalResponses;
    }

    public int getTotalPostiveResponses() {
        return totalPostiveResponses;
    }

    public void setTotalPostiveResponses(int totalPostiveResponses) {
        this.totalPostiveResponses = totalPostiveResponses;
    }

    public int getTotalNegativeResponses() {
        return totalNegativeResponses;
    }

    public void setTotalNegativeResponses(int totalNegativeResponses) {
        this.totalNegativeResponses = totalNegativeResponses;
    }

    public int getTotalNeutralResponses() {
        return totalNeutralResponses;
    }

    public void setTotalNeutralResponses(int totalNeutralResponses) {
        this.totalNeutralResponses = totalNeutralResponses;
    }
   
}