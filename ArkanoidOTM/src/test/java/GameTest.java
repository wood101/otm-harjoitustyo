import fi.helsinki.arkanoidotm.game.components.Ball;
import fi.helsinki.arkanoidotm.game.components.Board;
import fi.helsinki.arkanoidotm.game.Game;
import fi.helsinki.arkanoidotm.game.components.Block;
import javax.swing.JFrame;
import org.junit.*;
import static org.junit.Assert.*;

public class GameTest {
    Board board;
    Ball ball;
    Block block;
    Game game;
    JFrame frame;
    
        @Before
    public void setUp() {
        frame = new JFrame("Arkanoid");
        frame.setSize(800, 600);
        game = new Game(frame);
        game.setSize(frame.getSize());
        frame.add(game);
    }
    
    @Test
    public void testBoardMovement() {
        board = new Board(game);
        board.setX(-1000);
        assertTrue(board.getBoard().x == 1);
        
    }
    
    @Test
    public void testLives() {
        game.setLives(1);
        game.loseLife();
        assertTrue(game.getLost());
    }
    
    @Test
    public void testWin() {
        game.setNumBlocks(1);
        game.reduceNumOfBlocks();
        assertTrue(game.getWon());
    }
}
