package grid;

import console.ConsoleLogger;
import junit.framework.Assert;
import main.RobotConstants;
import maze.Direction;
import maze.DirectionalPoint;
import maze.MazePoint;
import movement.FakeRunner;
import org.junit.Test;

import java.util.Arrays;

public class SimulateActualMovement {
    @Test
    public void testUnreachableSpace(){
        ConsoleLogger.initLogger(true);
        MazePoint goal = new MazePoint(1 * RobotConstants.ROBOT_LENGTH, 4 * RobotConstants.ROBOT_LENGTH);
        DynamicGridWorld gridWorld = new DynamicGridWorld(goal);

        gridWorld.updateGrid();

        DirectionalPoint mazePoint = new DirectionalPoint(Direction.FRONT, 0, 0);
        PositionInGrid positionInGrid = new PositionInGrid();
        positionInGrid.setCurrentPosition(mazePoint);
        positionInGrid.setLeftMeasure(145);
        positionInGrid.setRightMeasure(44);
        positionInGrid.setFrontMeasure(38);
        Direction direction = gridWorld.actualize(positionInGrid);

        gridWorld.printGrid();
        FakeRunner runner = new FakeRunner(new int[][]{{1,2}, {1, 2}});
        positionInGrid = runner.move(direction, positionInGrid);
        direction = gridWorld.actualize(positionInGrid);
    }
}
