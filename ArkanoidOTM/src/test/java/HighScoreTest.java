
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import com.google.api.services.sheets.v4.model.SpreadsheetProperties;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;
import fi.helsinki.arkanoidotm.game.highscore.HighScoreDao;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;

public class HighScoreTest {
    
    private static Sheets sheetsService;
    private static String SPREADSHEET_ID = "1QQmmAWKtWSMejc_26vOyew0qZg_niVJ9I0AAVfF9tuE";
 
   /* @BeforeClass
    public static void setup() throws GeneralSecurityException, IOException {
        sheetsService = HighScoreDao.getSheetsService();
    }
    
    /*@Test
    public void spreadSheetTest() throws IOException {
        Spreadsheet spreadSheet = new Spreadsheet().setProperties(
          new SpreadsheetProperties().setTitle("HighScore"));
        Spreadsheet result = sheetsService
          .spreadsheets()
          .create(spreadSheet).execute();

        assertTrue(result.getSpreadsheetId() != null);   
    }
    
    @Test
    public void whenWriteSheet_thenReadSheetOk() throws IOException {
        ValueRange body = new ValueRange()
          .setValues(Arrays.asList(
            Arrays.asList("ASD", 5000), 
            Arrays.asList("FDD", 5000), 
            Arrays.asList("SSD", 9000), 
            Arrays.asList("AAA", 5400), 
            Arrays.asList("ABC", 5700), 
            Arrays.asList("ASL", 5000),
            Arrays.asList("AJD", 8000), 
            Arrays.asList("BSD", 15000), 
            Arrays.asList("ADD", 4000), 
            Arrays.asList("ASD", 600)));
        UpdateValuesResponse result = sheetsService.spreadsheets().values()
          .update(SPREADSHEET_ID, "A1", body)
          .setValueInputOption("RAW")
          .execute();
}*/
}