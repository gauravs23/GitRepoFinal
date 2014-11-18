/*
 * Copyright (C) 2014 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.mashathon.sentiments.domain.worker;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.google.gdata.data.spreadsheet.WorksheetFeed;
import com.google.gdata.util.ServiceException;
import com.topcoder.mashathon.sentiments.json.Json;
import com.topcoder.mashathon.sentiments.model.Response;

/**
 * Implementation of {@link AppProperties} backed by a {@link DatastoreService}.
 * 
 * @version 1.0
 * @since 1.0
 * @author TCASSEMBLER
 */
@Component
public class GoogleSpreadsheetDataManager implements DataManager {
    /** Our logger. */
    private static final Logger LOG = Logger.getLogger(GoogleSpreadsheetDataManager.class.getName());

    /** Json helper class. We reuse what Google gives us. */
    @Autowired
    private Json json;

    @Autowired
    private SpreadsheetService spreadsheetService;

    @Override
    public void saveData(Response response) {
        try {
            System.out.println("spreadsheetService : " + spreadsheetService);
            // Define the URL to request. This should never change.
            URL SPREADSHEET_FEED_URL = new URL("https://spreadsheets.google.com/feeds/spreadsheets/private/full");

            // Make a request to the API and get all spreadsheets.
            SpreadsheetFeed feed = spreadsheetService.getFeed(SPREADSHEET_FEED_URL, SpreadsheetFeed.class);
            List<SpreadsheetEntry> spreadsheets = feed.getEntries();

            if (spreadsheets.size() == 0) {
                return;
            }

            SpreadsheetEntry spreadsheet = null;

            for (int i = 0; i < spreadsheets.size(); i++) {
                SpreadsheetEntry sp = spreadsheets.get(i);
                String title = sp.getTitle().getPlainText();
                if (title != null && title.equalsIgnoreCase("voice_of_people")) {
                    spreadsheet = sp;
                }
            }
            System.out.println(spreadsheet.getTitle().getPlainText());

            // Get the first worksheet of the first spreadsheet.
            WorksheetFeed worksheetFeed = spreadsheetService.getFeed(spreadsheet.getWorksheetFeedUrl(),
                    WorksheetFeed.class);
            List<WorksheetEntry> worksheets = worksheetFeed.getEntries();
            WorksheetEntry worksheet = worksheets.get(0);

            // Fetch the list feed of the worksheet.
            URL listFeedUrl = worksheet.getListFeedUrl();

            // Create a local representation of the new row.
            ListEntry row = new ListEntry();
            row.getCustomElements().setValueLocal("userEmail", response.getUserEmail());
            row.getCustomElements().setValueLocal("feedbackQuestion", response.getQuestionText());
            row.getCustomElements().setValueLocal("feedbackResponse", response.getFullText());
            row.getCustomElements().setValueLocal("feedbackSentiment", response.getSentiment().getSentiment());
            row.getCustomElements().setValueLocal("feedbackSentimentScore",
                    String.valueOf(response.getSentiment().getScore()));

            // Send the new row to the API for insertion.
            row = spreadsheetService.insert(listFeedUrl, row);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

}
