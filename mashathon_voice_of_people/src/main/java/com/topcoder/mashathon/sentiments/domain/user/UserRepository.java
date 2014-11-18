/*
 * Copyright (C) 2014 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.mashathon.sentiments.domain.user;

import com.google.api.services.admin.directory.model.User;
import com.google.api.services.admin.directory.model.Users;

/**
 * Defines repository operations for Google Users. Delegates the interesting bits to Google's {@link Users} API.
 * 
 * @author TCASSEMBLER
 * @version 1.0
 * @since 1.0
 */
public interface UserRepository {
    /**
     * Returns the next page of users, using the <code>nextPageToken</code> as a starting point.
     * 
     * @param nextPageToken
     *            the next page token to start the search from
     * @return the Users resolved.
     */
    Users findNextPageOfUsers(String nextPageToken);

    /**
     * Returns the user based on user key which can be email or unique id.
     * 
     * @param userKey
     *            unique id or email of the user
     * @return the User resolved.
     */
    User findUserByKey(String userKey);

    /**
     * Checks if the user passed is the super admin of the domain or not.
     * 
     * @param user
     *            the user to check.
     * @return the boolean status whether user is admin or not.
     */
    boolean isUserSuperAdmin(String userKey);
}
