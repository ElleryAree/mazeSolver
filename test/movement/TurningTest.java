package movement;

import junit.framework.Assert;
import localization.maze.Direction;
import org.junit.Test;

public class TurningTest {
    @Test
    public void testFrontFront(){
        assertDirection(Direction.FRONT, Direction.FRONT, Direction.FRONT);
    }

    @Test
    public void testFrontRight(){
        assertDirection(Direction.FRONT, Direction.RIGHT, Direction.RIGHT);
    }

    @Test
    public void testFrontLeft(){
        assertDirection(Direction.FRONT, Direction.LEFT, Direction.LEFT);
    }

    @Test
    public void testFrontBack(){
        assertDirection(Direction.FRONT, Direction.BACK, Direction.BACK);
    }


    @Test
    public void testLeftFront(){
        assertDirection(Direction.LEFT, Direction.FRONT, Direction.RIGHT);
    }

    @Test
    public void testLeftRight(){
        assertDirection(Direction.LEFT, Direction.RIGHT, Direction.BACK);
    }

    @Test
    public void testLeftLeft(){
        assertDirection(Direction.LEFT, Direction.LEFT, Direction.FRONT);
    }

    @Test
    public void testLeftBack(){
        assertDirection(Direction.LEFT, Direction.BACK, Direction.LEFT);
    }

    @Test
    public void testRightFront(){
        assertDirection(Direction.RIGHT, Direction.FRONT, Direction.LEFT);
    }

    @Test
    public void testRightRight(){
        assertDirection(Direction.RIGHT, Direction.RIGHT, Direction.FRONT);
    }

    @Test
    public void testRightLeft(){
        assertDirection(Direction.RIGHT, Direction.LEFT, Direction.BACK);
    }

    @Test
    public void testRighBack(){
        assertDirection(Direction.RIGHT, Direction.BACK, Direction.RIGHT);
    }

    @Test
    public void testBacktFront(){
        assertDirection(Direction.BACK, Direction.FRONT, Direction.BACK);
    }

    @Test
    public void testBackRight(){
        assertDirection(Direction.BACK, Direction.RIGHT, Direction.LEFT);
    }

    @Test
    public void testBackLeft(){
        assertDirection(Direction.BACK, Direction.LEFT, Direction.RIGHT);
    }

    @Test
    public void testBackBack(){
        assertDirection(Direction.BACK, Direction.BACK, Direction.FRONT);
    }

    private void assertDirection(Direction currentDirection, Direction newDirection, Direction expectedDirection){
        changeDirection(currentDirection.getDegrees(), newDirection.getDegrees(), expectedDirection.getDegrees());

        Assert.assertEquals(currentDirection.name() + " + " + newDirection.name(),
                expectedDirection, changeDirection(currentDirection, newDirection));
    }

    private Direction changeDirection(Direction currentDirection, Direction newDirection){
        return MovementInMaze.convertDirection(currentDirection, newDirection);
    }

    private void changeDirection(int currentDirection, int newDirection, int expectedDirection){
        String unmodifiedFormula = currentDirection + " + " + newDirection + " = " + expectedDirection;
        int computedDirection = newDirection - currentDirection;
        if (computedDirection > 180){
            computedDirection = 180 - computedDirection;
        }
        if (computedDirection <= -180){
            computedDirection = 360 + computedDirection;
        }

        String formula = computedDirection + " = " + expectedDirection;
        String error = "error: " + (computedDirection - expectedDirection);
        Assert.assertEquals(unmodifiedFormula + '\n' + formula + '\n' + error, expectedDirection, computedDirection);
    }
}
