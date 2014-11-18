/*
 * Copyright (C) 2014 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.mashathon.sentiments.model;

/**
 * Class that encapsulates the current configuration of the application.
 * 
 * @version 1.0
 * @since 1.0
 * @author TCASSEMBLER
 */
public class Response {

    private String fullText;
    private SentimentType sentiment;
    private String questionText;
    private String userEmail;
    
    /** Default constructor. */
    public Response() {
    }
    
    public String getFullText() {
        return fullText;
    }

    public void setFullText(String fullText) {
        this.fullText = fullText;
    }

    public SentimentType getSentiment() {
        return sentiment;
    }

    public void setSentiment(SentimentType sentiment) {
        this.sentiment = sentiment;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
    
}