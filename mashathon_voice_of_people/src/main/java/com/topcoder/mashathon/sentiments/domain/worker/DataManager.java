/*
 * Copyright (C) 2014 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.mashathon.sentiments.domain.worker;

import com.topcoder.mashathon.sentiments.model.Response;


public interface DataManager {

    void saveData(Response response);
    
}
