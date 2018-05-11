import fi.helsinki.arkanoidotm.game.components.Ball;
import fi.helsinki.arkanoidotm.game.components.Board;
import fi.helsinki.arkanoidotm.game.Game;
import fi.helsinki.arkanoidotm.game.components.Block;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
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
    public void testGetters() {
        assertTrue(game.getGame() == game);
        assertTrue(game.getGameField().width == 600);
        assertTrue(game.getGameBall().getRadius() == ball.standardRadius);
        assertFalse(game.getWon());
    }
    
    @Test
    public void testBoardMovement() {
        board = new Board(game);
        board.setX(-1000);
        assertTrue(board.getBoard().x == 1);
        board.setX(1000);
        assertTrue(board.getBoard().x == (int) game.getSize().getWidth() - board.getBoard().width);
    }
    
    @Test
    public void testStart() {
        game.setLives(5);
        game.getGameScore().scoreGainForBlock();
        game.start();
        assertTrue(game.getLives() == 3);
        assertTrue(game.getGameScore().getScore() == 0);
    }
    
    @Test
    public void testLives() {
        game.setLives(1);
        game.loseLife();
        assertTrue(game.getLost());
    }
    /*
    @Test
    public void testKeyControls() throws AWTException {
        game.stop();
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_ENTER);
        assertTrue(game.getRunning());
    }
    
    @Test
    public void testMouseControls() throws AWTException {
        game.start();
        board = new Board(game);
        int position = board.getBoard().x;
        Robot robot = new Robot();
        robot.mouseMove(5, 5);
        assertTrue
        (board.getBoard().x != position);
    }
    */
    @Test
    public void testWinCondition() {
        game.start();
        game.setNumBlocks(1);
        game.getGameScore().scoreLossForLosingLife();
        game.reduceNumOfBlocks();
        assertFalse(game.getRunning());
    }
}
