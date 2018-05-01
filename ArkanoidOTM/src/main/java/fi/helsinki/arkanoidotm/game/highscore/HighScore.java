package fi.helsinki.arkanoidotm.game.highscore;
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
}
