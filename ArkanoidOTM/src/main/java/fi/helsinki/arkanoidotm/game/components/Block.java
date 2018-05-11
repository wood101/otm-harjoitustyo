package fi.helsinki.arkanoidotm.game.components;

import fi.helsinki.arkanoidotm.game.Game;
import java.awt.Rectangle;

/**
 * Luokka on pelin este.
 */
public class Block {
    private Rectangle block;
    private boolean destroyed = false;
    private Game instance;
    
    /**
     * Konstruktori luo uuden esteen.
     * @param x Esteen sijainnin alku x-akselilla.
     * @param y Esteen sijainnin alku y-akselilla.
     * @param width Esteen pituus.
     * @param height Esteen korkeus.
     * @param game meneillään oleva peli.
     */
    public Block(int x, int y, int width, int height, Game game) {
        block = new Rectangle(x, y, width, height);
        this.instance = game;
    }
    
    public Rectangle getBlock() {
        return this.block;
    }    
    
    public boolean getDestroyed() {
        return this.destroyed;
    }
    /**
     * Tarkistaa onko pallo törmännyt esteeseen.
     * @param rectangle Törmäävä esine.
     * @return Palauttaa true, jos pallo törmäsi ja este ei ollut tuhottu.
     */
    public boolean collidesWith(Rectangle rectangle) {
        return (destroyed) ? false : block.intersects(rectangle);
    }
    /**
     * Asettaa esteen tuhotuksi.
     */
    public void destroy() {
        destroyed = true;
        instance.reduceNumOfBlocks();
    }
}
