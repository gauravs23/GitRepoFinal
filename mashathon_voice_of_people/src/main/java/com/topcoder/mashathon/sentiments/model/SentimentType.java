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
public class SentimentType {

    private String sentiment;
    private double score;
    private String topic;
    private String original_text;
    private int original_length;
    private String normalized_text;
    private int normalized_length;
    
    
    public String getSentiment() {
        return sentiment;
    }

    public void setSentiment(String sentiment) {
        this.sentiment = sentiment;
    }
    
    public double getScore() {
        return score;
    }
    
    public void setScore(double score) {
        this.score = score;
    }

    public String getOriginal_text() {
        return original_text;
    }

    public void setOriginal_text(String original_text) {
        this.original_text = original_text;
    }

    public int getOriginal_length() {
        return original_length;
    }

    public void setOriginal_length(int original_length) {
        this.original_length = original_length;
    }

    public String getNormalized_text() {
        return normalized_text;
    }

    public void setNormalized_text(String normalized_text) {
        this.normalized_text = normalized_text;
    }

    public int getNormalized_length() {
        return normalized_length;
    }

    public void setNormalized_length(int normalized_length) {
        this.normalized_length = normalized_length;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
    
}
