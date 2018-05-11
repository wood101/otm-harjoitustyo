package fi.helsinki.arkanoidotm.game.components;

import fi.helsinki.arkanoidotm.game.Game;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
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
    private boolean side = false;
    private boolean top = false;
    
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
    /**
     * Asettaa pallon suunnan ja nopeuden.
     * @param xMove x-akselin liike
     * @param yMove y-akselin liike
     */
    public void setVector(int xMove, int yMove) {
        vector = new Dimension(xMove, yMove);
    }
    
    public Point getPosition() {
        return pos;
    }
    
    public int getRadius() {
        return radius;
    }
    /**
     * Asettaa pallon haluttuun kohtaan.
     * @param x piste x-akselilla
     * @param y piste y-akselilla
     */    
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
        checkBlockCollision();
        pos.move(pos.x + vector.width, pos.y + vector.height);
    }
    /**
     * Tarkistaa törmääkö pallo pelikentän reunoihin.
     */
    public void checkGameFieldEdgeCollision() {
        if (pos.x - radius <= 0 && vector.width < 0 || pos.x + radius >= instance.getSize().getWidth() && vector.width > 0) {
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
        if (instance.getGameBoard().collidesWith(new Rectangle(pos.x - radius + vector.width, pos.y - radius + vector.height, radius * 2, radius * 2))) {
            vector.height = -vector.height;
        }    
    }
    /**
     * Tarkistaa törmääkö pallo palikoihin.
     */
    public void checkBlockCollision() {
        for (Block[] xBlocks : instance.getBlocks()) {
            for (Block block : xBlocks) {
                if (block.collidesWith(new Rectangle(pos.x - radius / 2, pos.y - radius, radius, radius * 2))) {
                    top = true;
                    block.destroy();
                }
                if (block.collidesWith(new Rectangle(pos.x - radius, pos.y - radius / 2, radius * 2, radius))) {
                    side = true;
                    block.destroy();
                }
            }
        }
        changeVectorOnBlockCollision();
    }
    
    /**
     * Vaihtaa pallon suuntaa osumakulmasta riippuen ja vähentää pelin palikoiden määrää.
     */
    public void changeVectorOnBlockCollision() {
        if (side | top) {
            if (side) {
                vector.width = -vector.width;
                side = false;
            }
            if (top) {
                vector.height = -vector.height;
                top = false;
            }
        }
    }
}