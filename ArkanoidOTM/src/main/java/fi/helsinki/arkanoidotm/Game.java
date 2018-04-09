package fi.helsinki.arkanoidotm;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class Game extends JPanel {
    private Dimension field = new Dimension(400, 300);
    private Board board;
    
    public Game(Frame container){
        container.addKeyListener(new KeyAdapter(){
           public void keyPressed(KeyEvent e){
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) board.move(10);
            if (e.getKeyCode() == KeyEvent.VK_LEFT) board.move(-10);
            repaint();
        }    
        });
        board = new Board(this);
    }
    
    public void setSize(Dimension size){
        super.setSize(size);
        field = new Dimension(size.width-200, size.height-200);
        board.setY(field.height-Board.standardHeight);
    }
    
    public Dimension getSize(){
        return field;
    }
    
    public void paint(Graphics g){
        super.paint(g);
        
        g.translate((getWidth()-field.width)/2, (getHeight()-field.height)/2);
        
        g.setColor(Color.white);
        g.fillRect(0, 0, field.width, field.height);
        g.setColor(Color.black);
        g.drawRect(0, 0, field.width, field.height);
        
        board.render(g);
    }
    
}
