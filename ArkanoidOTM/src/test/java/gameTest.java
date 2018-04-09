import fi.helsinki.arkanoidotm.Board;
import fi.helsinki.arkanoidotm.Game;
import javax.swing.JFrame;
import org.junit.*;
import static org.junit.Assert.*;

public class gameTest {
    Board board;
    Game game;
    JFrame frame;
    
        @Before
    public void setUp() {
        frame = new JFrame("Arkanoid");
        frame.setSize(800, 600);
        game = new Game(frame);
        game.setSize(frame.getSize());
        frame.add(game);
        game = new Game(frame);
    }
    
    @Test
    public void testBoardMovement() {
        board = new Board(game);
        board.move(-1000);
        assertTrue(board.getBoard().x == 1);
        
    }
    
    
}
