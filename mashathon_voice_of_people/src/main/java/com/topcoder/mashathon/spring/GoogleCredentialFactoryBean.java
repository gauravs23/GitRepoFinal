/*
 * Copyright (C) 2014 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.mashathon.spring;

import java.io.File;
import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.AbstractFactoryBean;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.services.admin.directory.DirectoryScopes;
import com.google.common.base.Preconditions;
import com.google.gdata.client.spreadsheet.SpreadsheetService;

/**
 * {@link FactoryBean} which creates a {@link GoogleCredential} instance, configured using the injected fields, and
 * returns it to the Spring container.
 * 
 * <p>
 * <strong>Thread Safety:</strong> This class is thread safe.
 * </p>
 * 
 * @author TCASSEMBLER
 * @version 1.0
 */
public class GoogleCredentialFactoryBean extends AbstractFactoryBean<GoogleCredential> {
    /** The client id of the service account to use. */
    private String serviceAccountId;

    /** The user we will be making requests to the API on behalf of. */
    private String serviceAccountUser;

    /** The file that contains the p12 private key associated with the service account id. */
    private File privateKeyFile;

    /** The HttpTransport the {@link GoogleCredential} will use. */
    private HttpTransport httpTransport;

    /** The JsonFactory the {@link GoogleCredential} will use. */
    private JsonFactory jsonFactory;

    /**
     * Validates that our dependencies have been set to valid references, immediately after this bean is initialized.
     * 
     * @throws NullPointerException
     *             if any mandatory fields have not been initialized to a non-null reference.
     * @throws IllegalStateException
     *             if any mandatory fields have been initialized with invalid values/references.
     */
    @PostConstruct
    public void throwIfInvalid() {
        // null check
        Preconditions.checkNotNull(serviceAccountUser);
        Preconditions.checkNotNull(httpTransport);
        Preconditions.checkNotNull(jsonFactory);

        // validity checks
        Preconditions.checkState(privateKeyFile.isFile());
        Preconditions.checkState(!serviceAccountId.trim().isEmpty());
        Preconditions.checkState(!serviceAccountUser.trim().isEmpty());
    }

    /** {@inheritDoc} */
    @Override
    public Class<?> getObjectType() {
        return GoogleCredential.class;
    }

    /** {@inheritDoc} */
    @Override
    protected GoogleCredential createInstance() throws Exception {
        GoogleCredential.Builder credBuilder = new GoogleCredential.Builder();
        credBuilder.setJsonFactory(jsonFactory);
        credBuilder.setTransport(httpTransport);
        credBuilder.setServiceAccountId(serviceAccountId);

        credBuilder.setServiceAccountPrivateKeyFromP12File(privateKeyFile);
        credBuilder.setServiceAccountScopes(Arrays.asList(DirectoryScopes.ADMIN_DIRECTORY_USER, DirectoryScopes.ADMIN_DIRECTORY_USER_SECURITY, "https://spreadsheets.google.com/feeds"));
        credBuilder.setServiceAccountUser(serviceAccountUser);

        return credBuilder.build();
    }

    /**
     * Sets the ServiceAccountUser.
     * 
     * @param serviceAccountUser
     *            the reference to set our {@link #serviceAccountUser} to.
     */
    public void setServiceAccountUser(String serviceAccountUser) {
        this.serviceAccountUser = serviceAccountUser;
    }

    /**
     * Sets the JsonFactory.
     * 
     * @param jsonFactory
     *            the reference to set our {@link #jsonFactory} to.
     */
    public void setJsonFactory(JsonFactory jsonFactory) {
        this.jsonFactory = jsonFactory;
    }

    /**
     * Sets the HttpTransport.
     * 
     * @param httpTransport
     *            the reference to set our {@link #httpTransport} to.
     */
    public void setHttpTransport(HttpTransport httpTransport) {
        this.httpTransport = httpTransport;
    }

    /**
     * Sets the serviceAccountId
     * 
     * @param serviceAccountId
     *            the reference to set our {@link #serviceAccountId} to.
     */
    public void setServiceAccountId(String serviceAccountId) {
        this.serviceAccountId = serviceAccountId;
    }

    /**
     * Sets the private key file
     * 
     * @param privateKeyFile
     *            the reference to set our {@link #privateKeyFile} to.
     */
    public void setPrivateKeyFile(File privateKeyFile) {
        this.privateKeyFile = privateKeyFile;
    }
}
