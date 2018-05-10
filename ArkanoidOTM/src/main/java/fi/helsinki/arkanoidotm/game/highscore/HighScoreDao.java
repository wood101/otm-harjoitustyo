/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.arkanoidotm.game.highscore;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.MemoryDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.BatchGetValuesResponse;
import com.google.api.services.sheets.v4.model.DimensionRange;
import com.google.api.services.sheets.v4.model.ValueRange;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author janne
 */
public class HighScoreDao {
    private static final String APPLICATION_NAME = "Google Sheets Example";
    private static Sheets sheetsService;
    private static String SPREADSHEET_ID = "1QQmmAWKtWSMejc_26vOyew0qZg_niVJ9I0AAVfF9tuE";
    
    public static Credential authorize() throws IOException, GeneralSecurityException {
        InputStream in = HighScoreDao.class.getResourceAsStream("/google-sheets-client-secret.json");
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JacksonFactory.getDefaultInstance(), new InputStreamReader(in));
        
        List<String> scopes = Arrays.asList(SheetsScopes.DRIVE_FILE);
        
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance(), clientSecrets, scopes).setDataStoreFactory(new MemoryDataStoreFactory())
                .setAccessType("offline").build();
        Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
        return credential;
    }
    
    public static Sheets getSheetsService() throws IOException, GeneralSecurityException {
        Credential credential = authorize();
        return new Sheets.Builder(
          GoogleNetHttpTransport.newTrustedTransport(), 
          JacksonFactory.getDefaultInstance(), credential)
          .setApplicationName(APPLICATION_NAME)
          .build();
    }
    
    
    public static int readOldScore(int newScore) throws IOException, GeneralSecurityException{
        List<String> ranges = Arrays.asList("B10");
        sheetsService = getSheetsService();
        ValueRange result = sheetsService.spreadsheets().values().get(SPREADSHEET_ID, "B10").execute();
        return Integer.parseInt(result.getValues().get(0).get(0).toString());
    }

    public static void writeNewScore(int newScore, String user) {
        ValueRange body = new ValueRange()
          .setValues(Arrays.asList(Arrays.asList(user, newScore)));     
        try {
            sheetsService.spreadsheets().values()
                    .update(SPREADSHEET_ID, "A10", body)
                    .setValueInputOption("RAW")
                    .execute();
        } catch (IOException ex) {
        }
    }
    
    public static List<ValueRange> readHighScore(){
        List<String> ranges = new ArrayList();
        for(int i = 1; i < 11; i++) {
            ranges.add("A"+i);
            ranges.add("B"+i);
        }
        BatchGetValuesResponse readResult = null;
        try {
            readResult = sheetsService.spreadsheets().values()
                    .batchGet(SPREADSHEET_ID)
                    .setRanges(ranges)
                    .execute();
        } catch (IOException ex) {
        }
        return readResult.getValueRanges();
    }
}
