/*
 * Copyright (C) 2014 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.mashathon.spring;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.config.AbstractFactoryBean;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.gdata.client.spreadsheet.SpreadsheetService;


public class SpreadsheetFactoryBean extends AbstractFactoryBean<SpreadsheetService> {

    /** The GoogleCredential the Directory will use. */
    private GoogleCredential googleCredential;

    /** {@inheritDoc} */
    @Override
    public Class<?> getObjectType() {
        return SpreadsheetService.class;
    }

    /** {@inheritDoc} */
    @Override
    protected SpreadsheetService createInstance() throws Exception {
        SpreadsheetService service = new SpreadsheetService("mashathon-voice-of-people");
        service.setOAuth2Credentials(googleCredential);
        return service;
    }

    /**
     * Sets the GoogleCredential;
     * 
     * @param googleCredential
     *            the reference to set our {@link #googleCredential} to.
     */
    @Required
    public void setGoogleCredential(GoogleCredential googleCredential) {
        this.googleCredential = googleCredential;
    }
}
