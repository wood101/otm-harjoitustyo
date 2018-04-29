package fi.helsinki.arkanoidotm.game;

import fi.helsinki.arkanoidotm.game.components.Board;
import fi.helsinki.arkanoidotm.game.components.Ball;
import fi.helsinki.arkanoidotm.game.components.Block;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class Game extends JPanel {
    private Dimension field = new Dimension(600, 800);
    public Board board;
    private Block[][] blocks;
    public int lives = 3;
    private int blocksX = 10;
    private int blocksY = 3;
    private Ball ball;
    private Thread thread;
    public int numBlocks;
    public boolean running;
    public boolean lost = false;
    public boolean won = false;
    private boolean paused = false;
    
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
                /*
                if (running) {
                    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                        pause();
                    }
                }*/
            }    
            });
        createObjects(blocksX, blocksY);
    }
    
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
    
    public void start() {
        paused = false;
        lost = false;
        won = false;
        lives = 3;
        thread = new Thread(new Runnable() {
                public void run() {
                    running = true;
                    int time = 0;
                    ball.setVector(3, 3);
                    while (running) {
                        if (!paused) {
                            time++;
                            repaint();
                            try {
                                Thread.sleep(1);
                            } catch (Exception e) {
                            }
                            if (time == 10) {
                                time = 0;
                                ball.tick();
                            }
                        }
                    }
                }
        });
        thread.start();
    }
    
    /*public void pause() {
        if (paused) {
            paused = false;
        } else {
            paused = true;
        }
    }*/
    
    public void stop() {
        running = false;
        reset();
        repaint();
    }
    
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
    }
    
    public void reduceNumBlocks() {
        numBlocks--;
        if (numBlocks <= 0) {
            won();
        }
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
    
    
    public void setSize(Dimension size) {
        super.setSize(size);
        if (!running) {
            for (int x = 0; x != blocks.length; x++) {
                for (int y = 0; y != blocks[0].length; y++) {
                    int bWidth = field.width / blocks.length;
                    int bHeight = (field.height / 8) / blocks[0].length;
                    blocks[x][y] = new Block(x * bWidth, y * bHeight, bWidth, bHeight);
                }
            }
            field = new Dimension(size.width - 200, size.height - 200);
            ball.setPosition(field.width / 2, field.height / 3);
            board.setX(field.width / 2 - Board.standardWidth / 2);
            board.setY(field.height - Board.standardHeight);
        }
    }
    
    public Dimension getSize() {
        return field;
    }
    
    public void paint(Graphics g) {
        super.paint(g);       
        g.translate((getWidth() - field.width) / 2, (getHeight() - field.height) / 2);
        g.setColor(Color.white);
        g.fillRect(0, 0, field.width, field.height);
        g.setColor(Color.black);
        g.drawRect(0, 0, field.width, field.height);
        g.drawString("Lives: " + lives, 0, -5);
        for (Block[] xBlocks : blocks) {
            for (Block block : xBlocks) {
                block.render(g);
            }
        }
        drawText(g);
        ball.render(g);
        board.render(g);
    }

    public void drawText(Graphics g) {
        if (!running && !lost && !won) {
            g.drawString("Press ENTER to start", getWidth() - field.width, getHeight() - field.height);
            g.drawString("Use the mouse to move", getWidth() - field.width, getHeight() - field.height + 15);
        } else if (!running && lost && !won) {
            g.drawString("You LOST!", getWidth() - field.width + 5, getHeight() - field.height - 15);
            g.drawString("Press ENTER to start again", getWidth() - field.width, getHeight() - field.height);
        } else if (!running && !lost && won) {
            g.drawString("You WON! :)", getWidth() - field.width + 5, getHeight() - field.height - 15);
            g.drawString("Press ENTER to start again", getWidth() - field.width, getHeight() - field.height);
        }
    }
    


    
    public void loseLife() {
        lives--;
        if (lives == 0) {
            loose(); 
        }
        ball.randomPos();
    }
    
    public void loose() {
        lost = true;
        stop();
    }
    
    public void won() {
        won = true;
        stop();
    }
}
