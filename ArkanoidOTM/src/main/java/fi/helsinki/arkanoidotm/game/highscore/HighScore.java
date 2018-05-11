package fi.helsinki.arkanoidotm.game.highscore;

import com.google.api.services.sheets.v4.model.ValueRange;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

/**
 * Luokka pisteistä.
 */
public class HighScore {
    private int score;
    /**
     * Konstruktori asettaa pisteet nollaan.
     */
    public HighScore() {
        score = 0;
    }
    
    public int getScore() {
        return this.score;
    }
    /**
     * Asettaa pisteet nollaan.
     */
    public void reset() {
        this.score = 0;
    }
    /**
     * Pienentää pisteiden määrää yhdellä.
     */
    public void scoreLossForTime() {
        this.score--;
    }
    /**
     * Pienentää pisteiden määrää 5000.
     */
    public void scoreLossForLosingLife() {
        this.score -= 5000;
    }
    /**
     * Antaa tuhat pistettä.
     */
    public void scoreGainForBlock() {
        this.score += 1000;
    }
    /**
     * Kutsuu Google taulukkoon kirjoitusta, jos pisteet ovat riittävän suuret.
     * @param newScore Käyttäjän pisteet
     * @param user Käyttäjänimi
     */
    public void writeScoreIfHighScore(int newScore, String user) {
        int old = 0;
        try {
            old = HighScoreDao.readOldScore("B10");
        } catch (IOException ex) {
        } catch (GeneralSecurityException ex) {
        }
        if (newScore >= old) {
            HighScoreDao.writeNewScore(newScore, user);   
        }
    }
    /**
     * Muodostaa pistetaulukon.
     * @return palauttaa pistetaulukon
     */
    public ArrayList<ArrayList> getHighScoreBoard() {
        ArrayList<ArrayList> highScore = new ArrayList();
        List<ValueRange> scores = HighScoreDao.readHighScore();
        int j = 0;
        for (int i = 0; i < 10; i++) {
            highScore.add(new ArrayList<>());
            highScore.get(i).add(scores.get(j).getValues().get(0).get(0).toString());
            j++;
            highScore.get(i).add(scores.get(j).getValues().get(0).get(0).toString());
            j++;
        }
        return highScore;
    }
}
