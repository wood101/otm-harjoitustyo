
import fi.helsinki.arkanoidotm.game.Game;
import fi.helsinki.arkanoidotm.game.components.Ball;
import fi.helsinki.arkanoidotm.game.components.Block;
import fi.helsinki.arkanoidotm.game.components.Board;
import javax.swing.JFrame;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class BallTest {
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
    public void testBallBounceRight() {
        ball = new Ball(game, Ball.standardRadius);
        int x = 500;
        ball.setPosition(game.getSize().width / 2, 0);
        ball.setVector(x, 0);
        ball.tick();
        ball.tick();      
        assertTrue(x == -ball.getVector().width);
    }
    
    @Test
    public void testBallBounceLeft() {
        ball = new Ball(game, Ball.standardRadius);
        int x = -500;
        ball.setPosition(game.getSize().width / 2, 0);
        ball.setVector(x, 0);
        ball.tick();
        ball.tick();      
        assertTrue(x == -ball.getVector().width);
    }
    
    @Test
    public void testBallBounceUp() {
        ball = new Ball(game, Ball.standardRadius);
        int x = -500;
        ball.setVector(0, x);
        ball.tick();
        ball.tick();      
        assertTrue(x == -ball.getVector().height);
    }
    
    @Test
    public void testBallBounceDown() {
        ball = new Ball(game, Ball.standardRadius);
        game.lives = 3;
        int x = 500;
        ball.setVector(0, x);
        ball.tick();
        ball.tick();      
        assertTrue(game.lives == 2);
    }
    
    @Test
    public void testBallBounceFromBoard() {
        ball = new Ball(game, Ball.standardRadius);
        board = new Board(game);
        ball.setPosition(0, 0);
        board.setX(ball.getPosition().x);
        int x = 10;
        ball.setVector(0, x);
        ball.tick();
        ball.tick();      
        assertTrue(x == -ball.getVector().height);
    }    
}
