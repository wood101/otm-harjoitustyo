package fi.helsinki.arkanoidotm.game.components;

import fi.helsinki.arkanoidotm.game.Game;
import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;
/**
 * Luokka luo pelissä liikkuvan pallo.
 */
public class Ball {
    /**
     * Oletusarvoinen säde.
     */
    public static int standardRadius = 8;
    private Game instance;
    private Dimension vector;
    private Point pos;
    private int radius;
    private boolean collided = false;
    
    /**
     * Luo uuden pallon ja asettaa sen satunnaiseen paikkaan vaakatasolla.
     * @param game Tällä hetkellä meneillään oleva peli.
     * @param radius Pallon säde.
     */
    public Ball(Game game, int radius) {
        instance = game;
        randomPos();
        this.radius = radius;
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
    
    public int getRadius() {
        return radius;
    }
        
    public void setPosition(int x, int y) {
        pos = new Point(x, y);
    }
    /**
     * Asettaa pallon satunnaiseen kohtaan vaakatasolla.
     */
    public void randomPos() {
        int x = ThreadLocalRandom.current().nextInt(5, (int) instance.getSize().getWidth() - 5);
        pos = new Point(x, (int) instance.getSize().getHeight() / 2); 
    }
    /**
     * Siirtää palloa.
     */
    public void tick() {
        checkGameFieldEdgeCollision();
        checkBoardCollision();
        pos.move(pos.x + vector.width, pos.y + vector.height);
        checkBlockCollision();
    }
    /**
     * Tarkistaa törmääkö pallo pelikentän reunoihin.
     */
    public void checkGameFieldEdgeCollision() {
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
    }
    /**
     * Tarkistaa törmääkö pallo pelaajan lautaan.
     */
    public void checkBoardCollision() {
        if (instance.getGameBoard() != null) {
            if (instance.getGameBoard().collidesWith(new Rectangle(pos.x - radius + vector.width, pos.y - radius + vector.height, radius * 2, radius * 2))) {
                vector.height = -vector.height;
            }
        }        
    }
    /**
     * Tarkistaa törmääkö pallo esteisiin.
     */
    public void checkBlockCollision() {
        boolean side = false;
        boolean top = false;
        for (Block[] xBlocks : instance.getBlocks()) {
            for (Block block : xBlocks) {
                if (block.collidesWith(new Rectangle(pos.x - radius - vector.height, pos.y - radius, radius * 2 - vector.height * 2, radius * 2))) {
                    top = true;
                    block.destroy();
                    instance.reduceNumOfBlocks();
                    collided = true;
                }
                if (block.collidesWith(new Rectangle(pos.x - radius, pos.y - radius - vector.height, radius * 2, radius * 2 - vector.height * 2))) {
                    side = true;
                    block.destroy();
                    instance.reduceNumOfBlocks();
                    collided = true;
                }
            }
        }
        if(collided){
            if (top) {
                vector.height = -vector.height;
                top = false;
            } else if (side) {
                vector.width = -vector.width;
                side = false;
            } else if (side && top) {
                vector.width = -vector.width;
                vector.height = -vector.height;
                top = false;
                side = false;
            }
            collided = false;
        }
    }
}
