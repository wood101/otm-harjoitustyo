package fi.helsinki.arkanoidotm.game;

import fi.helsinki.arkanoidotm.game.components.Board;
import fi.helsinki.arkanoidotm.game.components.Ball;
import fi.helsinki.arkanoidotm.game.components.Block;
import fi.helsinki.arkanoidotm.graphics.GameGraphics;
import fi.helsinki.arkanoidotm.game.highscore.HighScore;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

/**
 * Luokka luo pelin.
 */
public class Game extends JPanel {
    private Dimension field = new Dimension(600, 800);
    private Board board;
    private Block[][] blocks;
    private final int blocksX = 10;
    private final int blocksY = 3;
    private Ball ball;
    private HighScore score = new HighScore();
    private GameGraphics gg = new GameGraphics();
    private Thread thread;
    private int numBlocks;
    private int lives = 3;
    private int time = 0;
    private boolean running;
    private boolean lost = false;
    private boolean won = false;
    
    /**
    * Game luokan konstruktori, joka luo komponentit peliin ja mahdollistaa kontrollit.
     * @param container JFrame container, jonka sisällä peli pöyrii
    */
    public Game(Frame container) {
        this.setFocusable(true);
        gg.createControls(this);
        createObjects(blocksX, blocksY);
    }
    
    public Game getGame() {
        return this;
    }
    
    public Dimension getGameField() {
        return this.field;
    }
    
    public Ball getGameBall() {
        return this.ball;
    }
    
    public Board getGameBoard() {
        return this.board;
    }
    
    public Block[][] getBlocks() {
        return this.blocks;
    }

    public void setBlocks(Block[][] blocks) {
        this.blocks = blocks;
    }
    
    public HighScore getGameScore() {
        return this.score;
    }
    
    public void setNumBlocks(int x) {
        this.numBlocks = x;
    }
    
    public Dimension getSize() {
        return field;
    }
    
    public void setLives(int lives) {
        this.lives = lives;
    }
    
    public int getLives() {
        return this.lives;
    }
    
    public boolean getWon() {
        return this.won;
    }
    
    public boolean getRunning() {
        return this.running;
    }
    
    public boolean getLost() {
        return this.lost;
    }
    /**
     * Asettaa pelikentän koon ja resetoi sen komponentit.
     * @param size Haluttu uusi koko.
     */
    public void setSize(Dimension size) {
        super.setSize(size);
        field = new Dimension(size.width - 200, size.height - 200);
        reset();
    }
    
    /** 
    * Luo peliin uudet komponentit.
    * @param blocksX Vaakatason esteiden määrä
    * @param blocksY Pystytason esteiden määrä
    */
    public void createObjects(int blocksX, int blocksY) {
        numBlocks = blocksX * blocksY;
        createBlocks(8);
        board = new Board(this);
        ball = new Ball(this, Ball.standardRadius);
    }
    
    /**
     * Tekee pelin palikat.
     * @param distTop Palikat suhteessa pelikentän huippuun.
     */
    public void createBlocks(int distTop) {
        blocks = new Block[blocksX][blocksY];
        for (int x = 0; x != blocks.length; x++) {
            for (int y = 0; y != blocks[0].length; y++) {
                int bWidth = field.width / blocksX;
                int bHeight = (field.height / distTop) / blocksY;
                blocks[x][y] = new Block(x * bWidth, y * bHeight, bWidth, bHeight, this);
            }
        }
    }
    
    /**
     * Asettaa pelin alkuarvot ja laittaa pelin liikkumaan.
     */
    public void start() {
        running = true;
        resetStats();
        thread = new Thread(new Runnable() {
                public void run() {
                    while (running) {
                        repaint();
                        try {
                            Thread.sleep(1);
                        } catch (Exception e) {
                        }
                        moveBall(1);
                    }
                }
        });
        thread.start();
    }
    
    /**
     * Move ball at specific thread ticks.
     * @param x How often to move ball.
     */
    public void moveBall(int x) {
        time += x;
        if (time == 10) {
            time = 0;
            score.scoreLossForTime();
            ball.tick();
        }
    }
    
    /**
     * Resetoi arvot uutta peliä varten.
     */
    public void resetStats() {
        ball.setVector(4, 4);
        lost = false;
        won = false;
        lives = 3;
        score.reset();
    }
    
    /**
    * Pysäyttää pelin.
    */
    public void stop() {
        running = false;
    }
    /**
     * Asettaa komponenti oikeille paikoilleen uutta peliä varten.
     */
    public void reset() {
        ball.randomPos();
        ball.setVector(4, 4);
        board.setX(field.width / 2 - Board.standardWidth / 2);
        board.setY(field.height - Board.standardHeight);
        numBlocks = blocksX * blocksY;
        createBlocks(4);
        repaint();
    }
    /**
     * Vähentää jäljellä olevien laatikoiden määrää,
     * jos kaikki laatikot on tuhottu, niin pelin voittaa.
     */
    public void reduceNumOfBlocks() {
        numBlocks--;
        score.scoreGainForBlock();
        if (numBlocks <= 0) {
            inputHighScore();
        }
    }
    /**
     * Piirtää käyttöliittymän.
     * @param g Käyttöliittymän mahdollistavat grafiikat
     */
    public void paint(Graphics g) {
        super.paint(g);
        gg.renderGame(g, this);
    }
    /**
     * Vähentää elämien määrää,
     * jos elämät loppuu niin häviät pelin.
     * @see fi.helsinki.arkanoidotm.game.components.Ball#randomPos() 
     */
    public void loseLife() {
        lives--;
        if (lives == 0) {
            loose(); 
        }
        ball.randomPos();
        repaint();
        score.scoreLossForLosingLife();
        try {
            thread.sleep(1000);
        } catch (Exception e) {
        }
    }
    /**
     * Häviää pelin ja resetoi sen uutta peliä varten.
     */
    public void loose() {
        lost = true;
        stop();
        reset();
    }
    /**
     * Kutsuu käyttäjänimensyöttö tekstilaatikkoa ja pysäyttää pelin.
     */
    public void inputHighScore() {
        if (score.getScore() > 0) {
            gg.renderUsernameBox(this);
        }        
        stop();    
    }
    
    /**
     * Kutsuu HighScoreen kirjoitusta.
     * Voittaa pelin ja resetoi sen uutta peliä varten.
     * @param user käyttäjänimi
     */
    public void won(String user) {
        score.writeScoreIfHighScore(score.getScore(), user);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
        }
        won = true;
        repaint();
        reset();
    }
}
