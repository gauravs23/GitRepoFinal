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
public class HPResponse {

    private List<SentimentType> positive;
    private List<SentimentType> negative;
    private SentimentType aggregate;

    /** Default constructor. */
    public HPResponse() {
    }

    public SentimentType getAggregate() {
        return aggregate;
    }

    public void setAggregate(SentimentType aggregate) {
        this.aggregate = aggregate;
    }

    public List<SentimentType> getPositive() {
        return positive;
    }

    public void setPositive(List<SentimentType> positive) {
        this.positive = positive;
    }

    public List<SentimentType> getNegative() {
        return negative;
    }

    public void setNegative(List<SentimentType> negative) {
        this.negative = negative;
    }
   
    
}