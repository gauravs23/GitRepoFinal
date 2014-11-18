/*
 * Copyright (C) 2014 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.mashathon.sentiments.domain.worker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.api.client.util.ArrayMap;
import com.topcoder.mashathon.sentiments.json.Json;
import com.topcoder.mashathon.sentiments.model.Response;
import com.topcoder.mashathon.sentiments.model.SentimentType;


@Component
public class HPResponseManager implements ResponseManager {
    /** Our logger. */
    private static final Logger LOG = Logger.getLogger(HPResponseManager.class.getName());

    private static final String HP_API_KEY = "2f8bc6dc-fb09-4a56-968c-7216381f1885";
    
    private static final String LANGUAGE = "eng";
    
    /** Json helper class. We reuse what Google gives us. */
    @Autowired
    private Json json;

    @Override
    public void calculateSentiment(Response response) {
        
        String text = response.getFullText();
        
        String url = null;
        try {
            url = "https://api.idolondemand.com/1/api/sync/analyzesentiment/v1?text="+URLEncoder.encode(text, "UTF-8")+"&language=eng&apikey="+URLEncoder.encode(HP_API_KEY, "UTF-8");
        } catch (UnsupportedEncodingException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        try {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
 
        // optional default is GET
        con.setRequestMethod("GET");
        
        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to HP API URL : " + url);
        System.out.println("Response Code : " + responseCode);
 
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer sb = new StringBuffer();
 
        while ((inputLine = in.readLine()) != null) {
            sb.append(inputLine);
        }
        in.close();
 
        //print result
        System.out.println("HP API RESPONSE : "+sb.toString());
        
        HashMap responseMap = json.deserialize(sb.toString(), HashMap.class);
        System.out.println("responseMap : "+responseMap);

        System.out.println("aggregate : "+responseMap.get("aggregate"));
        ArrayMap map = (ArrayMap)responseMap.get("aggregate");
        SentimentType sentiment = new SentimentType();
        sentiment.setSentiment((String)map.get("sentiment"));
        sentiment.setScore(((BigDecimal)(map.get("score"))).doubleValue());
        response.setSentiment(sentiment);
        System.out.println("response at the end : "+response);
        } catch(IOException e) {
             LOG.fine("Error in getting the sentiment from HP API.");
        }
    }
}
