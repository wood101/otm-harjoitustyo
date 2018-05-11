
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
        this.frame = new JFrame("Arkanoid");
        frame.setSize(800, 600);
        this.game = new Game(frame);
        game.setSize(frame.getSize());
        frame.add(game);
    }
    
    @Test
    public void testRadius() {
        ball = new Ball(game, Ball.standardRadius);
        assertTrue(ball.getRadius() == ball.standardRadius);
    }
    
    @Test
    public void testBallBounceRight() {
        ball = new Ball(game, Ball.standardRadius);
        int x = 500;
        ball.setPosition(game.getSize().width / 2, game.getSize().height / 2);
        ball.setVector(x, 0);
        ball.tick();
        ball.tick();      
        assertTrue(x == -ball.getVector().width);
    }
    
    @Test
    public void testBallBounceLeft() {
        ball = new Ball(game, Ball.standardRadius);
        int x = -500;
        ball.setPosition(game.getSize().width / 2, game.getSize().height / 2);
        ball.setVector(x, 0);
        ball.tick();
        ball.tick();      
        assertTrue(x == -ball.getVector().width);
    }
    
    @Test
    public void testBallBounceSideOfBlock() {
        ball = new Ball(game, Ball.standardRadius);
        Block[][] blocks = new Block[1][1]; 
        blocks[0][0] = new Block(150, 150, 30, 30);
        game.setBlocks(blocks);
        int x = -3;
        ball.setPosition(185, 160);
        ball.setVector(x, 0);
        ball.tick();
        ball.tick();
        assertTrue(x == -ball.getVector().width);
    }
    
    @Test
    public void testBallBounceTopOfBlock() {
        ball = new Ball(game, Ball.standardRadius);
        int x = -2;
        ball.setVector(0, x);
        ball.setPosition(5, 1);
        ball.tick();     
        ball.tick();
        assertTrue(x == -ball.getVector().height);
    }    
    
    @Test
    public void testBallBounceUp() {
        ball = new Ball(game, Ball.standardRadius);
        Block[][] blocks = new Block[1][1]; 
        blocks[0][0] = new Block(150, 150, 30, 30);
        game.setBlocks(blocks);
        int x = -2;
        ball.setVector(0, x);
        ball.setPosition(2, 1);
        ball.tick();     
        ball.tick();
        assertTrue(x == -ball.getVector().height);
    }
    
    @Test
    public void testBallHitFloor() {
        ball = new Ball(game, Ball.standardRadius);
        game.setLives(3);
        int x = 500;
        ball.setVector(0, x);
        ball.tick();
        ball.tick();      
        assertTrue(game.getLives() == 2);
    }
    
    @Test
    public void testBallBounceFromBoard() {
        ball = new Ball(game, Ball.standardRadius);
        int x = 5;
        ball.setVector(0, x);
        game.getGameBoard().setX(ball.getPosition().x);
        game.getGameBoard().setY(ball.getPosition().y + 3);
        ball.tick(); 
        assertTrue(x == -ball.getVector().height);
    }    
}
