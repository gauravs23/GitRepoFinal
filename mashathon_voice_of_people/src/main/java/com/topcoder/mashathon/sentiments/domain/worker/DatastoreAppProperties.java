/*
 * Copyright (C) 2014 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.mashathon.sentiments.domain.worker;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Text;
import com.topcoder.mashathon.sentiments.json.Json;
import com.topcoder.mashathon.sentiments.model.Response;
import com.topcoder.mashathon.sentiments.model.SentimentType;

/**
 * Implementation of {@link AppProperties} backed by a {@link DatastoreService}.
 * 
 * @version 1.0
 * @since 1.0
 * @author TCASSEMBLER
 */
@Component
public class DatastoreAppProperties implements AppProperties {
    /** Our logger. */
    private static final Logger LOG = Logger.getLogger(DatastoreAppProperties.class.getName());

    /** The kind of entity our properties objects are stored as in the Datastore */
    private static final String DATASTORE_KIND = "settings";

    /** The (single) property key we use to store our properties in the datastore */
    private static final String PROPERTY_KEY = "key";
    
    /** The kind of entity our properties objects are stored as in the Datastore */
    private static final String DATASTORE_KIND_RESPONSES = "responses";

    /** The (single) property key we use to store our properties in the datastore */
    private static final String RESPONSES_PROPERTY_KEY = "responsesKey";

    private static final String QUESTION_TEXT = "questionText";

    private static final String QUESTION_STATUS = "questionStatus";
    
    /** The datastore service instance. */
    private DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    /** Json helper class. We reuse what Google gives us. */
    @Autowired
    private Json json;

    /**
     * Gets the given row from the datastore.
     * 
     * @param rowId
     *            the rowId to fetch
     * @return the value of the row's field PROPERTY_KEY, assumed to be a String
     * @throws EntityNotFoundException
     *             if the rowId does not exist
     */
    private String doGet(String rowId) throws EntityNotFoundException {
        Key egsKey = KeyFactory.createKey(DATASTORE_KIND, rowId);

        Entity egsEntity = datastore.get(egsKey);

        // schema changed from String to Text type. Transparently handle that here.
        Object propertyValue = egsEntity.getProperty(PROPERTY_KEY);

        if (propertyValue instanceof String) {
            return (String) propertyValue;
        }

        Text text = (Text) propertyValue;

        return text.getValue();
    }

    /**
     * Puts the <code>value</code> in the <code>rowId</code>. Stores it in the property named {@link #PROPERTY_KEY}.
     * 
     * @param rowId
     *            the rowId to store in
     * @param value
     *            the value to store
     */
    private void doPut(String rowId, String value) {
        Entity entity = new Entity(DATASTORE_KIND, rowId);
        entity.setProperty(PROPERTY_KEY, new Text(value));
        
        datastore.put(entity);
    }
    
    /**
     * Gets the given row from the datastore.
     * 
     * @param rowId
     *            the rowId to fetch
     * @return the value of the row's field PROPERTY_KEY, assumed to be a String
     * @throws EntityNotFoundException
     *             if the rowId does not exist
     */
    private List<Response> doGetResponses() throws EntityNotFoundException {
        
        Query q = new Query(DATASTORE_KIND_RESPONSES);

        // Use PreparedQuery interface to retrieve results
        PreparedQuery pq = datastore.prepare(q);
        List<Response> responsesList = new ArrayList<Response>();
        for (Entity result : pq.asIterable()) {
            String fullText = (String)result.getProperty("fullText");
            String sentiment = (String)result.getProperty("sentiment");
            double score = (double)result.getProperty("score");
            Response response = new Response();
            response.setFullText(fullText);
            SentimentType sentimentType = new SentimentType();
            sentimentType.setSentiment(sentiment);
            sentimentType.setScore(score);
            response.setSentiment(sentimentType);
            responsesList.add(response);
        }
        return responsesList;
    }

    
    /**
     * Puts the <code>value</code> in the <code>rowId</code>. Stores it in the property named {@link #PROPERTY_KEY}.
     * 
     * @param rowId
     *            the rowId to store in
     * @param value
     *            the value to store
     */
    private void doPutResponse(Response response) {
        Entity entity = new Entity(DATASTORE_KIND_RESPONSES);
        
        String fullText = response.getFullText();
        entity.setProperty("fullText", fullText);
        if(response.getSentiment() != null) {
            String sentiment = response.getSentiment().getSentiment();
            double score = response.getSentiment().getScore();
            entity.setProperty("sentiment", sentiment);
            entity.setProperty("score", score);
        }
        
        datastore.put(entity);
    }

    @Override
    public String getQuestionText() {
        try {
            return doGet(QUESTION_TEXT);
        } catch (EntityNotFoundException e) {
            return "";
        }
    }

    @Override
    public String getQuestionStatus() {
        try {
            return doGet(QUESTION_STATUS);
        } catch (EntityNotFoundException e) {
            return "";
        }
    }

    @Override
    public void setQuestionText(String questionText) {
        doPut(QUESTION_TEXT, questionText);
    }

    @Override
    public void setQuestionStatus(String questionStatus) {
        doPut(QUESTION_STATUS, questionStatus);
    }

    @Override
    public List<Response> getResponseList() {
        try {
            return doGetResponses();
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void saveResponse(Response response) {
        doPutResponse(response);
    }
}
