package fi.helsinki.arkanoidotm.game.components;

import fi.helsinki.arkanoidotm.game.Game;
import java.awt.*;

public class Block {
    private Rectangle block;
    private boolean destroyed = false;
    
    public Block(int x, int y, int width, int height) {
        block = new Rectangle(x, y, width, height);
    }
    
    public void render(Graphics g) {
        if (!destroyed) {
            g.setColor(Color.RED);
            g.fillRect(block.x, block.y, block.width, block.height);
            g.setColor(Color.BLACK);
            g.drawRect(block.x, block.y, block.width, block.height);
        }
    }

    public boolean collidesWith(Rectangle rectangle) {
        return (destroyed) ? false : block.intersects(rectangle);
    }
    
    public void destroy() {
        destroyed = true;
    }
}
