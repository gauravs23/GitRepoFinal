/*
 * Copyright (C) 2014 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.mashathon.sentiments.domain.user;

import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.services.admin.directory.Directory.Users;
import com.google.api.services.admin.directory.Directory.Users.List;
import com.google.api.services.admin.directory.model.User;

/**
 * Implementation of the {@link UserRepository} backed by a Google {@link Users} instance.
 * 
 * @author TCASSEMBLER
 * @version 1.0
 * @since 1.0
 * 
 */
@Repository
public class GoogleUserRepository implements UserRepository {
    /** The customer we pass to Google to tell it to fetch everything in our domain. */
    private static final String MY_CUSTOMER = "my_customer";

    /** Our logger. */
    private static final Logger LOG = Logger.getLogger(GoogleUserRepository.class.getName());

    /** Google Directory Users service to which we delegate. */
    @Autowired
    private Users usersService;

    /**
     * {@inheritDoc}
     * 
     * @throws IllegalStateException
     *             if an exception occurred during user resolution
     */
    @Override
    public com.google.api.services.admin.directory.model.Users findNextPageOfUsers(String nextPageToken) {
        Random randomGenerator = new Random();
        for (int n = 0; n < 5; ++n) {
            try {
                List userListCommand;
                userListCommand = usersService.list();
                userListCommand.setCustomer(MY_CUSTOMER);
                userListCommand.setPageToken(nextPageToken);
                return userListCommand.execute();
            } catch (GoogleJsonResponseException e) {
                GoogleJsonError error = e.getDetails();
                LOG.warning("Error code: " + error.getCode()+",  Error message: " + error.getMessage());
                // More error information can be retrieved with error.getErrors().
                if (error.getCode() == 403
                        && (error.getErrors().get(0).getReason().equals("quotaExceeded") || error.getErrors()
                                .get(0).getReason().equals("userRateLimitExceeded"))) {
                    long retrySecs = (1 << n) * 1000 + randomGenerator.nextInt(1001);
                    LOG.warning("Google API returned rate related exception. Retrying the request in " + retrySecs);
                    // Apply exponential backoff.
                    try {
                        Thread.sleep(retrySecs);
                    } catch (InterruptedException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                } else {
                    LOG.log(Level.FINE, "An exception occurred while fetching the next page of users");
                    // Other error, re-throw.
                    throw new IllegalStateException("An exception occurred while fetching the next page of users");
                }
            } catch (IOException e) {
                throw new IllegalStateException("An exception occurred while fetching the next page of users");
            }
        }
        throw new IllegalStateException("An exception occurred while fetching the next page of users");
    }

    /**
     * Sets the {@link #usersService}.
     * 
     * @param usersService
     *            the reference to set to.
     */
    public void setUsersService(Users usersService) {
        this.usersService = usersService;
    }

    /**
     * Returns the user based on user key which can be email or unique id.
     * 
     * @param userKey
     *            unique id or email of the user
     * @return the User resolved.
     */
    @Override
    public User findUserByKey(String userKey) {
        Random randomGenerator = new Random();
        for (int n = 0; n < 5; ++n) {
            try {
                return usersService.get(userKey).execute();
            } catch (GoogleJsonResponseException e) {
                GoogleJsonError error = e.getDetails();
                LOG.warning("Error code: " + error.getCode()+",  Error message: " + error.getMessage());
                // More error information can be retrieved with error.getErrors().
                if (error.getCode() == 403
                        && (error.getErrors().get(0).getReason().equals("quotaExceeded") || error.getErrors()
                                .get(0).getReason().equals("userRateLimitExceeded"))) {
                    long retrySecs = (1 << n) * 1000 + randomGenerator.nextInt(1001);
                    LOG.warning("Google API returned rate related exception. Retrying the request in " + retrySecs);
                    // Apply exponential backoff.
                    try {
                        Thread.sleep(retrySecs);
                    } catch (InterruptedException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                } else {
                    LOG.log(Level.FINE, "Could not find the user with user key : " + userKey, e);
                    // Other error, re-throw.
                    throw new IllegalStateException(
                            "Could not find the user with user key : " + userKey, e);
                }
            } catch (IOException e) {
                throw new IllegalStateException("An exception occurred while finding with user key: "
                        + userKey, e);
            }
        }
        throw new IllegalStateException("Exception occured even after trying: " + userKey);
    }

    /**
     * Checks if the user passed is the super admin of the domain or not.
     * 
     * @param user
     *            the user to check.
     * @return the boolean status whether user is admin or not.
     */
    @Override
    public boolean isUserSuperAdmin(String userKey) {
        try {
            // TODO Auto-generated method stub
            User user = findUserByKey(userKey);
            if (user != null) {
                return user.getIsAdmin();
            }
        } catch (Exception e) {
            LOG.fine(e.getMessage());
            return false;
        }
        return false;
    }
}
