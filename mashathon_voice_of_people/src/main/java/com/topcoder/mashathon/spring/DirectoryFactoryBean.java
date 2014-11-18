/*
 * Copyright (C) 2014 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.mashathon.spring;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.config.AbstractFactoryBean;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.services.admin.directory.Directory;

/**
 * {@link FactoryBean} which creates a {@link Directory} instance, configured using the injected fields, and returns it
 * to the Spring container.
 * <p/>
 * Note that the setter methods annotated with {@link Required} will throw if the values are not set after bean
 * initialization.
 * 
 *
 * <p>
 * <strong>Thread Safety:</strong> This class is thread safe.
 * </p>
 * 
 * @author TCASSEMBLER
 * @version 1.0
 *
 */
public class DirectoryFactoryBean extends AbstractFactoryBean<Directory> {
    /** The HttpTransport the Directory will use. */
    private HttpTransport httpTransport;

    /** The JsonFactory the Directory will use. */
    private JsonFactory jsonFactory;

    /** The GoogleCredential the Directory will use. */
    private GoogleCredential googleCredential;

    /** {@inheritDoc} */
    @Override
    public Class<?> getObjectType() {
        return Directory.class;
    }

    /** {@inheritDoc} */
    @Override
    protected Directory createInstance() throws Exception {
        return new Directory.Builder(httpTransport, jsonFactory, googleCredential).setApplicationName(
                "tc-asps-mgmt-assembly").build();
    }

    /**
     * Sets the HttpTransport.
     * 
     * @param httpTransport
     *            the reference to set our {@link #httpTransport} to.
     */
    @Required
    public void setHttpTransport(HttpTransport httpTransport) {
        this.httpTransport = httpTransport;
    }

    /**
     * Sets the JsonFactory.
     * 
     * @param jsonFactory
     *            the reference to set our {@link #jsonFactory} to.
     */
    @Required
    public void setJsonFactory(JsonFactory jsonFactory) {
        this.jsonFactory = jsonFactory;
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
