import fi.helsinki.arkanoidotm.osat.Ball;
import fi.helsinki.arkanoidotm.osat.Board;
import fi.helsinki.arkanoidotm.osat.Game;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JFrame;
import org.junit.*;
import static org.junit.Assert.*;

public class gameTest {
    Board board;
    Ball ball;
    Game game;
    JFrame frame;
    Graphics g;
    KeyAdapter ka;
    
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
        board.move(-1000);
        assertTrue(board.getBoard().x == 1);
        
    }
    
    @Test
    public void testBallMovement() {
        ball = new Ball(game, Ball.standardRadius);
        int x = 500;
        ball.setVector(x, 0);
        ball.tick();
        ball.tick();      
        assertTrue(x == -ball.getVector().width);
    }
    
    
    @Test
    public void testLives() {
        game.lives = 1;
        game.loseLife();
        assertTrue(game.lost);
    }
}
