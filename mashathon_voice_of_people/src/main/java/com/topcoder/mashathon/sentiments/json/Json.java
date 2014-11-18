/*
 * Copyright (C) 2014 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.mashathon.sentiments.json;

import java.io.IOException;
import java.io.StringWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonGenerator;

/**
 * Small helper class that leverages the Google {@link JsonFactory} to help us serialize and deserialize JSON objects.
 * 
 * @version 1.0
 * @since 1.0
 * @author TCASSEMBLER
 */
@Component
public class Json {
    /** The Google Json Factory. */
    @Autowired
    private JsonFactory jsonFactory;

    /**
     * Serializes the given object to JSON.
     * 
     * @param obj
     *            the object to serialize
     * @return JSON String representing the object
     * @throws IllegalStateException
     *             if an exception occurs during serialization
     */
    public String serialize(final Object obj) {
        StringWriter sw = new StringWriter();
        JsonGenerator jsonGenerator = null;

        try {
            jsonGenerator = jsonFactory.createJsonGenerator(sw);
            jsonGenerator.serialize(obj);
        } catch (IOException e) {
            throw new IllegalStateException("Exception while serializing an object to JSON", e);
        } finally {
            if (jsonGenerator != null) {
                try {
                    jsonGenerator.close();
                } catch (IOException e) {
                    throw new IllegalStateException("Exception while serializing an object to JSON", e);
                }
            }
        }

        return sw.toString();
    }

    /**
     * Deserializes the given JSON string into an object of type T
     * 
     * @param <T>
     *            the type of the object that should be deserialized to
     * @param json
     *            the json string
     * @param clazz
     *            the clazz to deserialize into
     * @return the deserialized object.
     * @throws IllegalStateException
     *             if an exception occurs during deserialization
     */
    public <T> T deserialize(String json, Class<T> clazz) {
        try {
            return jsonFactory.fromString(json, clazz);
        } catch (IOException e) {
            throw new IllegalStateException("Exception while deserializing an object from JSON", e);
        }
    }

    /**
     * Sets the {@link #jsonFactory}
     * 
     * @param jsonFactory
     *            value to set to
     */
    public void setJsonFactory(JsonFactory jsonFactory) {
        this.jsonFactory = jsonFactory;
    }
}
