package grid;

import junit.framework.Assert;
import maze.Direction;
import maze.DirectionalPoint;
import maze.MazePoint;
import org.junit.Test;

/**
 * Date: 8/17/12
 */
public class MeasurementsRotationTest {
    @Test
    public void testFront(){
        PositionInGrid positionInGrid = new PositionInGrid();

        DirectionalPoint currentPosition = new DirectionalPoint();
        currentPosition.setDirection(Direction.FRONT);

        positionInGrid.setCurrentPosition(currentPosition);
        positionInGrid.setLeftMeasure(10);
        positionInGrid.setRightMeasure(15);
        positionInGrid.setFrontMeasure(20);
        positionInGrid.setRearMeasure(25);

        GridWorld world = new DynamicGridWorld(new MazePoint(40, 40));

        assertDirections(world.rotateMeasurements(positionInGrid), 10, 15, 20, 25);
    }

    @Test
    public void testLeft(){
        PositionInGrid positionInGrid = new PositionInGrid();

        DirectionalPoint currentPosition = new DirectionalPoint();
        currentPosition.setDirection(Direction.LEFT);

        positionInGrid.setCurrentPosition(currentPosition);
        int leftMeasure = 10;
        positionInGrid.setLeftMeasure(leftMeasure);
        int rightMeasure = 15;
        positionInGrid.setRightMeasure(rightMeasure);
        int frontMeasure = 20;
        positionInGrid.setFrontMeasure(frontMeasure);
        int backMeasure = 25;
        positionInGrid.setRearMeasure(backMeasure);

        GridWorld world = new DynamicGridWorld(new MazePoint(40, 40));
        world.rotateMeasurements(positionInGrid);

        assertDirections(world.rotateMeasurements(positionInGrid), frontMeasure, backMeasure, rightMeasure, leftMeasure);
    }

    @Test
    public void testRight(){
        PositionInGrid positionInGrid = new PositionInGrid();

        DirectionalPoint currentPosition = new DirectionalPoint();
        currentPosition.setDirection(Direction.RIGHT);

        positionInGrid.setCurrentPosition(currentPosition);
        int leftMeasure = 10;
        positionInGrid.setLeftMeasure(leftMeasure);
        int rightMeasure = 15;
        positionInGrid.setRightMeasure(rightMeasure);
        int frontMeasure = 20;
        positionInGrid.setFrontMeasure(frontMeasure);
        int backMeasure = 25;
        positionInGrid.setRearMeasure(backMeasure);

        GridWorld world = new DynamicGridWorld(new MazePoint(40, 40));
        world.rotateMeasurements(positionInGrid);

        assertDirections(world.rotateMeasurements(positionInGrid), backMeasure, frontMeasure, leftMeasure, rightMeasure);
    }

    @Test
    public void testBack(){
        PositionInGrid positionInGrid = new PositionInGrid();

        DirectionalPoint currentPosition = new DirectionalPoint();
        currentPosition.setDirection(Direction.BACK);

        positionInGrid.setCurrentPosition(currentPosition);
        int leftMeasure = 10;
        positionInGrid.setLeftMeasure(leftMeasure);
        int rightMeasure = 15;
        positionInGrid.setRightMeasure(rightMeasure);
        int frontMeasure = 20;
        positionInGrid.setFrontMeasure(frontMeasure);
        int backMeasure = 25;
        positionInGrid.setRearMeasure(backMeasure);

        GridWorld world = new DynamicGridWorld(new MazePoint(40, 40));
        world.rotateMeasurements(positionInGrid);

        assertDirections(world.rotateMeasurements(positionInGrid), rightMeasure, leftMeasure, backMeasure, frontMeasure);
    }

    /**
     * @param positionInGrid rotated position
     * @param left expected value for left measurement
     * @param right expected value for right measurement
     * @param front expected value for front measurement
     * @param back expected value for back measurement
     */
    private void assertDirections(PositionInGrid positionInGrid, int left, int right, int front, int back) {
        Assert.assertEquals("Left", left, positionInGrid.getLeftMeasurement());
        Assert.assertEquals("Right", right, positionInGrid.getRightMeasurement());
        Assert.assertEquals("Front", front, positionInGrid.getFrontMeasurement());
        Assert.assertEquals("back", back, positionInGrid.getBackMeasure());
    }
}
