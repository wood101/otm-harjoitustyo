package fi.helsinki.arkanoidotm.game.components;

import fi.helsinki.arkanoidotm.game.Game;
import java.awt.Rectangle;
/**
 * Luokka pelaajan liikuteltavasta laudasta.
 */
public class Board {
    /**
     * Oletusarvoinen leveys laudalle.
     */
    public static int standardWidth = 80;
    /**
     * Oletusarvoinen korkeus laudalle.
     */
    public static int standardHeight = 10;
    private Rectangle board;
    private Game instance;
    /**
     * Konstruktori luo uuden laudan.
     * @param game Käytetään referenssinä laudan kokoon.
     */
    public Board(Game game) {
        instance = game;
        board = new Rectangle(((int) game.getSize().getWidth() - Board.standardWidth) / 2, ((int) game.getSize().getHeight() - Board.standardHeight) / 2, Board.standardWidth, Board.standardHeight);
    }
    /**
     * Tarkistaa törmäsikö pallo lautaan.
     * @param object Törmäävä esine.
     * @return Palauttaa true, jos pallo törmäsi.
     */
    public boolean collidesWith(Rectangle object) {
        return board.intersects(object);
    }
    
    public Rectangle getBoard() {
        return this.board;
    }
    
    /**
     * Asettaa laudan tiettyyn kohtaan vaakatasolla, jos se on pelikentän sisällä.
     * @param x Kohta, johon lauta halutaan.
     */
    public void setX(int x) {
        board.x = x;
        if (board.x < 1) {
            board.x = 1;
        }
        if (board.x > instance.getSize().getWidth() - this.board.width) {
            board.x = (int) instance.getSize().getWidth() - this.board.width;
        }
    }
    
    public void setY(int y) {
        board.y = y;
    }
}
