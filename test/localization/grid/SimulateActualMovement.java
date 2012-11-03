package localization.grid;

import console.LoggerProvider;
import localization.grid.aStar.AStarGridWorld;
import main.RobotConstants;
import localization.maze.Direction;
import localization.maze.DirectionalPoint;
import localization.maze.MazePoint;
import movement.FakeRunner;
import org.junit.Test;

public class SimulateActualMovement {
    @Test
    public void testUnreachableSpace(){
        LoggerProvider.initiateLogger(true, true);
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
        FakeRunner runner = new FakeRunner(gridWorld.getGrid());
        positionInGrid = runner.move(direction, positionInGrid);
        direction = gridWorld.actualize(positionInGrid);
    }

    @Test
    public void testAStarSpace(){
        LoggerProvider.initiateLogger(true, false);
        MazePoint goal = new MazePoint(1 * RobotConstants.ROBOT_LENGTH, 4 * RobotConstants.ROBOT_LENGTH);
        GridWorld gridWorld = new AStarGridWorld(goal);

        System.out.print(gridWorld.printGrid());

        DirectionalPoint mazePoint = new DirectionalPoint(Direction.FRONT, 0, 0);
        PositionInGrid positionInGrid = new PositionInGrid();
        positionInGrid.setCurrentPosition(mazePoint);
        positionInGrid.setLeftMeasure(145);
        positionInGrid.setRightMeasure(44);
        positionInGrid.setFrontMeasure(38);
        Direction direction = gridWorld.actualize(positionInGrid);

        System.out.println(direction);

        gridWorld.printGrid();
        FakeRunner runner = new FakeRunner(gridWorld.getGrid());

        positionInGrid = runner.move(direction, positionInGrid);
        positionInGrid.setLeftMeasure(10);
        positionInGrid.setRightMeasure(10);
        positionInGrid.setFrontMeasure(100);

        direction = gridWorld.actualize(positionInGrid);

        System.out.println(direction);
    }
}
