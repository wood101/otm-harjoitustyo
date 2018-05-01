package fi.helsinki.arkanoidotm.game;

import fi.helsinki.arkanoidotm.game.components.Board;
import fi.helsinki.arkanoidotm.game.components.Ball;
import fi.helsinki.arkanoidotm.game.components.Block;
import fi.helsinki.arkanoidotm.graphics.GameGraphics;
import fi.helsinki.arkanoidotm.game.highscore.HighScore;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

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
    public int lives = 3;
    public boolean running;
    public boolean lost = false;
    public boolean won = false;
    
    /**
    * Game luokan konstruktori, joka luo komponentit peliin ja mahdollistaa kontrollit.
     * @param container JFrame container, jonka sisällä peli pöyrii
    */
    public Game(Frame container) {
        this.setFocusable(true);
        this.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {
                if (running) {
                    board.setX(e.getX() - board.getBoard().width);
                    repaint();
                }
            }
        });
        this.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (!running) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        start();
                    }
                }    
            }    
            });
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
    
    public int getGameScore() {
        return this.score.getScore();
    }
    
    public void setNumBlocks(int x) {
        this.numBlocks = x;
    }
    
    public Dimension getSize() {
        return field;
    }
    /**
     * Asettaa pelikentän koon ja resetoi sen komponentit.
     * @param size Haluttu uusi koko.
     */
    public void setSize(Dimension size) {
        super.setSize(size);
        if (!running) {
            field = new Dimension(size.width - 200, size.height - 200);
            reset();
        }
    }
    /** 
    * Luo peliin uudet komponentit.
    * @param blocksX Vaakatason esteiden määrä
    * @param blocksY Pystytason esteiden määrä
    */
    public void createObjects(int blocksX, int blocksY) {
        numBlocks = blocksX * blocksY;
        blocks = new Block[blocksX][blocksY];
        for (int x = 0; x != blocks.length; x++) {
            for (int y = 0; y != blocks[0].length; y++) {
                int bWidth = field.width / blocksX;
                int bHeight = (field.height / 8) / blocksY;
                blocks[x][y] = new Block(x * bWidth, y * bHeight, bWidth, bHeight);
            }
        }
        board = new Board(this);
        ball = new Ball(this, Ball.standardRadius);
    }
    
    /**
     * Asettaa pelin alkuarvot ja laittaa pelin liikkumaan.
     */
    public void start() {
        lost = false;
        won = false;
        lives = 3;
        score.reset();
        thread = new Thread(new Runnable() {
                public void run() {
                    running = true;
                    int time = 0;
                    ball.setVector(4, 4);
                    while (running) {
                        time++;
                        repaint();
                        try {
                            Thread.sleep(1);
                        } catch (Exception e) {
                        }
                        if (time == 10) {
                            time = 0;
                            score.scoreLossForTime();
                            ball.tick();
                        }
                    }
                }
        });
        thread.start();
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
        ball.setPosition(field.width / 2, field.height / 3);
        board.setX(field.width / 2 - Board.standardWidth / 2);
        board.setY(field.height - Board.standardHeight);
        numBlocks = blocksX * blocksY;
        blocks = new Block[blocksX][blocksY];
        for (int x = 0; x != blocks.length; x++) {
            for (int y = 0; y != blocks[0].length; y++) {
                int bWidth = field.width / blocksX;
                int bHeight = (field.height / 4) / blocksY;
                blocks[x][y] = new Block(x * bWidth, y * bHeight, bWidth, bHeight);
            }
        }
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
            won();
        }
    }
    /**
     * Piirtää käyttöliittymän
     * @param g Käyttöliittymän mahdollistavat grafiikat
     */
    public void paint(Graphics g) {
        super.paint(g);
        gg.renderGame(g, this);
        if (won) {
            gg.renderHighScore(g, score, getSize());
        }
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
     * Voittaa pelin ja resetoi sen uutta peliä varten.
     */
    public void won() {
        won = true;
        repaint();
        stop();
        reset();
    }
}
