/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.arkanoidotm.game.highscore;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.BatchGetValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;
import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Google sheetin tiedonhausta vastuussa oleva luokka.
 */
public class HighScoreDao {
    private static Sheets sheetsService;
    private static String sheetId = "1QQmmAWKtWSMejc_26vOyew0qZg_niVJ9I0AAVfF9tuE";
    /**
     * Määrittelee käyttöluvat.
     * @return palauttaa käyttöluvan
     * @throws IOException ohjelman ulkopuolinen virhe
     * @throws GeneralSecurityException 
     */
    public static Credential authorize() throws IOException, GeneralSecurityException {
        File file = new File(HighScoreDao.class.getResource("/ArkanoidP12.p12").getFile());
        List<String> scopes = Arrays.asList(SheetsScopes.SPREADSHEETS);
        return new GoogleCredential.Builder()
            .setTransport(GoogleNetHttpTransport.newTrustedTransport())
            .setJsonFactory(JacksonFactory.getDefaultInstance())
            .setServiceAccountId("arkanoidhighscore@arkanoid-202811.iam.gserviceaccount.com")
            .setServiceAccountScopes(scopes)
            .setServiceAccountPrivateKeyFromP12File(file)
            .build();
    }
    /**
     * Luo Google Sheets olion.
     * @return palauttaa Google Sheets olion
     * @throws IOException ohjelman ulkopuolinen virhe
     * @throws GeneralSecurityException 
     */
    public static Sheets getSheetsService() throws IOException, GeneralSecurityException {
        Credential credential = authorize();
        return new Sheets.Builder(
          GoogleNetHttpTransport.newTrustedTransport(), 
          JacksonFactory.getDefaultInstance(), credential)
          .setApplicationName("Arkanoid")
          .build();
    }
    
    /**
     * Lukee vanhan pienimmän arvon pistetaulukosta.
     * @param line rivi johon kirjoitetaan sheetissä
     * @return palauttaa pienimmän arvon.
     * @throws IOException ohjelman ulkopuolinen virhe
     * @throws GeneralSecurityException 
     */
    public static int readOldScore(String line) throws IOException, GeneralSecurityException {
        List<String> ranges = Arrays.asList(line);
        sheetsService = getSheetsService();
        ValueRange result = sheetsService.spreadsheets().values().get(sheetId, line).execute();
        return Integer.parseInt(result.getValues().get(0).get(0).toString());
    }
    
    /**
     * Kirjoittaa pistetaulukkoon uuden piste-ennätyksen.
     * @param newScore Uudet pisteet
     * @param user Käyttäjänimi
     */
    public static void writeNewScore(int newScore, String user) {
        try {
            ValueRange body = new ValueRange()
                    .setValues(Arrays.asList(Arrays.asList(user, newScore)));
            sheetsService.spreadsheets().values()
                    .update(sheetId, "A10", body)
                    .setValueInputOption("RAW")
                    .execute();
        } catch (IOException ex) {
        }
    }
    /**
     * Lukee koko pistetaulukon.
     * @return palauttaa pistetaulukon
     */
    public static List<ValueRange> readHighScore() {
        List<String> ranges = new ArrayList();
        for (int i = 1; i < 11; i++) {
            ranges.add("A" + i);
            ranges.add("B" + i);
        }
        BatchGetValuesResponse readResult = null;
        try {
            readResult = sheetsService.spreadsheets().values()
                    .batchGet(sheetId)
                    .setRanges(ranges)
                    .execute();
        } catch (IOException ex) {
        }
        return readResult.getValueRanges();
    }
}
