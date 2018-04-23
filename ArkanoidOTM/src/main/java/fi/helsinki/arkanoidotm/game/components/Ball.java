package fi.helsinki.arkanoidotm.game.components;

import fi.helsinki.arkanoidotm.game.Game;
import java.awt.*;

public class Ball {
    public static int standardRadius = 8;
    private Game instance;
    private Dimension vector;
    private Point pos;
    private int radius;
    
    public Ball(Game game, int radius) {
        instance = game;
        pos = new Point((int) game.getSize().getWidth() / 2, (int) game.getSize().getHeight() / 2);
        this.radius = radius;
    }
    
    public void tick() {
        if (pos.x - radius <= 0 && vector.width < 0) {
            vector.width = -vector.width;
        }
        if (pos.x + radius >= instance.getSize().getWidth() && vector.width > 0) {
            vector.width = -vector.width;
        }
        if (pos.y - radius <= 0 && vector.height < 0) {
            vector.height = -vector.height;
        }
        if (pos.y + radius >= instance.getSize().getHeight() && vector.height > 0) {
            instance.loseLife();
        }
        
        if (instance.board != null) {
            if (instance.board.collidesWith(new Rectangle(pos.x - radius + vector.width, pos.y - radius + vector.height, radius * 2, radius * 2))) {
                vector.height = -vector.height;
            }
            
        }
        pos.move(pos.x + vector.width, pos.y + vector.height);
        
        for (Block[] xBlocks : instance.getBlocks()) {
            for (Block block : xBlocks) {
                if (block.collidesWith(new Rectangle(pos.x - radius, pos.y - radius, radius * 2, radius * 2))) {
                    block.destroy();
                    instance.reduceNumBlocks();
                    vector.height = -vector.height;
                }
            }
        }
    }
    
    public Dimension getVector() {
        return vector;
    }
    
    public void setVector(int xMove, int yMove) {
        vector = new Dimension(xMove, yMove);
    }
    
    public Point getPosition() {
        return pos;
    }
    
    public void setPosition(int x, int y) {
        pos = new Point(x, y);
    }
    
    public void render(Graphics g) {
        g.setColor(Color.black);
        g.fillOval(pos.x - radius, pos.y - radius, radius * 2, radius * 2);
    }
}