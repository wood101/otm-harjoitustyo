package fi.helsinki.arkanoidotm.game.components;

import fi.helsinki.arkanoidotm.game.Game;
import java.awt.*;

public class Block {
    private Rectangle block;
    private boolean destroyed = false;
    
    public Block(int x, int y, int width, int height) {
        block = new Rectangle(x, y, width, height);
    }
    
    public Rectangle getBlock() {
        return this.block;
    }    
    
    public boolean getDestroyed() {
        return this.destroyed;
    }

    public boolean collidesWith(Rectangle rectangle) {
        return (destroyed) ? false : block.intersects(rectangle);
    }
    
    public void destroy() {
        destroyed = true;
    }
}
