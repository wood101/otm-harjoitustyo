
import fi.helsinki.arkanoidotm.game.Game;
import fi.helsinki.arkanoidotm.game.highscore.HighScore;
import java.awt.Frame;
import java.io.IOException;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class HighScoreTest {
    
    @Test
    public void testWriteHighScore() throws IOException {
        HighScore score = new HighScore();
        score.writeScoreIfHighScore(100000, "test");
        assertTrue(score.getHighScoreBoard().get(9).get(1).equals("100000"));
    }
}