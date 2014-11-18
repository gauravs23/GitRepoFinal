/*
 * Copyright (C) 2014 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.mashathon.sentiments.handler;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.topcoder.mashathon.sentiments.domain.worker.AppProperties;
import com.topcoder.mashathon.sentiments.domain.worker.DataManager;
import com.topcoder.mashathon.sentiments.domain.worker.ResponseManager;
import com.topcoder.mashathon.sentiments.model.Configuration;

/**
 * JAX RS resource for the user interface.
 * 
 * @version 1.0
 * @since 1.0
 * @author TCASSEMBLER
 */
@Path("/settings")
@Produces(MediaType.APPLICATION_JSON)
@Component
public class SentimentsHandler {
    /**
     * Our app properties -- we pass most of these back to the interface for user configuration.
     */
    @Autowired
    private AppProperties appProperties;
    
    @Autowired
    private ResponseManager responseManager;
    
    @Autowired
    private DataManager dataManager;
    
    /**
     * @return the current {@link Configuration}
     */
    @GET
    @Path("/configuration")
    public Configuration getConfiguration() {
        Configuration config = new Configuration();
        config.setQuestionStatus(appProperties.getQuestionStatus());
        config.setQuestionText(appProperties.getQuestionText());
        return config;
    }

    /**
     * Persists configuration POSTed back to us.
     * 
     * @param configuration
     *            the configuration to persist
     * @return a simple "OK" response
     */
    @POST
    @Path("/save-question")
    public Response setConfiguration(Configuration configuration) {
        if (configuration == null) {
            return Response.status(Status.BAD_REQUEST).build();
        }

        String currentQuestionText = appProperties.getQuestionText();
        String currentQuestionStatus = appProperties.getQuestionStatus();

        System.out.println("currentQuestionText : " + currentQuestionText);
        System.out.println("currentQuestionStatus : " + currentQuestionStatus);
        
        String newQuestionText = configuration.getQuestionText();
        String newQuestionStatus = configuration.getQuestionStatus();
        
        if(newQuestionText != null && !newQuestionText.isEmpty()) {
            appProperties.setQuestionText(newQuestionText);
        }
        
        if(newQuestionStatus != null && !newQuestionStatus.isEmpty()) {
            appProperties.setQuestionStatus(newQuestionStatus);
        }
        return Response.ok("success").build();
    }
    
    /**
     * Persists configuration POSTed back to us.
     * 
     * @param configuration
     *            the configuration to persist
     * @return a simple "OK" response
     */
    @POST
    @Path("/save-response")
    public Response saveResponse(com.topcoder.mashathon.sentiments.model.Response response) {
        if (response == null) {
            return Response.status(Status.BAD_REQUEST).build();
        }
        
        String responseText = response.getFullText();

        if(responseText != null && !responseText.isEmpty()) {
            responseManager.calculateSentiment(response);
            System.out.println("response : "+response);
            appProperties.saveResponse(response);
            dataManager.saveData(response);
            return Response.ok("success").build();
        } else {
            return Response.ok("Error in saving the response. The user provided no input for the response.").build();
        }
    }
    
    /**
     * @return the current {@link Configuration}
     */
    @GET
    @Path("/get-responses")
    public List<com.topcoder.mashathon.sentiments.model.Response> getAllResponses() {
        return appProperties.getResponseList();
    }
    
}
