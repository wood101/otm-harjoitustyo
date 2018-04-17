package fi.helsinki.arkanoidotm.peli;

import fi.helsinki.arkanoidotm.peli.osat.Board;
import fi.helsinki.arkanoidotm.peli.osat.Ball;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class Game extends JPanel {
    private Dimension field = new Dimension(400, 300);
    public Board board;
    public int lives = 3;
    private Ball ball;
    private Thread thread;
    private boolean running;
    public boolean lost = false;
    private boolean paused = false;
    
    public Game(Frame container) {
        
        container.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (!running) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        start();
                    }
                }    
                if (!paused && running) {
                    if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                        board.move(20);
                    }                   
                    if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                        board.move(-20);
                    }
                    repaint();
                }
                /*if (running) {
                    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                        pause();
                    }
                }*/
            }    
            });
        board = new Board(this);
        ball = new Ball(this, Ball.standardRadius);
    }
    
    public void start() {
        paused = false;
        lost = false;
        lives = 3;
        thread = new Thread(new Runnable() {
                public void run() {
                    running = true;
                    ball.setVector(5, 10);

                    while (running && !paused) {
                        ball.tick();
                        repaint();
                        try {
                            Thread.sleep(40);
                        } catch (Exception e) {
                        }
                    }
                }
        });
        thread.start();
    }
    
    public void pause() {
        if (paused) {
            paused = false;
        } else {
            paused = true;
        }
    }
    
    public void stop() {
        running = false;
    }
    
    public void setSize(Dimension size) {
        super.setSize(size);
        if (!running) {
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
        
        if (!running) {
            g.drawString("Press ENTER to start", getWidth() - field.width, getHeight() - field.height);
        }
        
        ball.render(g);
        board.render(g);
    }

    public void loseLife() {
        lives--;
        if (lives == 0) {
            loose(); 
        }
        ball.setPosition(field.width / 2, field.height / 3);
    }
    
    public void loose() {
        lost = true;
        ball.setPosition(field.width / 2, field.height / 3);
        board.setX(field.width / 2 - Board.standardWidth / 2);
        board.setY(field.height - Board.standardHeight);
        stop();
    }
}
