package fi.helsinki.arkanoidotm.osat;

import javax.swing.*;
import java.awt.*;

public class Board {
    public static int standardWidth = 80;
    public static int standardHeight = 10;
    private Rectangle board;
    private Game instance;
    
    public Board(Game game) {
        instance = game;
        board = new Rectangle(((int) game.getSize().getWidth() - Board.standardWidth) / 2, ((int) game.getSize().getHeight() - Board.standardHeight) / 2, Board.standardWidth, Board.standardHeight);
    }
    
    public void move(int speed) {
        board.x += speed;
        if (board.x < 1) {
            board.x = 1;
        }
        if (board.x > instance.getSize().getWidth() - this.board.width) {
            board.x = (int) instance.getSize().getWidth() - this.board.width;
        }
    }
    
    public boolean collidesWith(Rectangle object) {
        return board.intersects(object);
    }
    
    public void setBoard(Rectangle board) {
        this.board = board;
    }
    
    public Rectangle getBoard() {
        return this.board;
    }
    
    public void setX(int x) {
        board.x = x;
    }
    
    public void setY(int y) {
        board.y = y;
    }
    
    public void render(Graphics g) {
        g.setColor(new Color(200, 200, 200));
        g.fillRect(board.x, board.y, board.width, board.height);
    }
}
