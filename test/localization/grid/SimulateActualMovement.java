package localization.grid;

import console.LoggerProvider;
import localization.grid.aStar.AStarGridWorld;
import localization.maze.Direction;
import localization.maze.DirectionalPoint;
import localization.maze.MazePoint;
import movement.FakeRunner;
import org.junit.Test;

public class SimulateActualMovement {
    @Test
    public void testUnreachableSpace(){
        LoggerProvider.initProvider(true);
        LoggerProvider.getProvider().callback(true);
        MazePoint goal = new MazePoint(1, 4);
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
        runner.move(direction, positionInGrid);
        direction = gridWorld.actualize(positionInGrid);
    }

    @Test
    public void testAStarSpace(){
        LoggerProvider.initProvider(true);
        LoggerProvider.getProvider().callback(false);
        MazePoint goal = new MazePoint(1, 4);
        GridWorld gridWorld = new AStarGridWorld(goal);

        System.out.print(gridWorld.printGrid());

        DirectionalPoint mazePoint = new DirectionalPoint(Direction.FRONT, 0, 0);
        PositionInGrid positionInGrid = new PositionInGrid();
        positionInGrid.setCurrentPosition(mazePoint);
        positionInGrid.setLeftMeasure(3);
        positionInGrid.setRightMeasure(1);
        positionInGrid.setFrontMeasure(1);
        Direction direction = gridWorld.actualize(positionInGrid);

        System.out.println(direction);

        System.out.println(gridWorld.printGrid());
        FakeRunner runner = new FakeRunner(gridWorld.getGrid());

        runner.move(direction, positionInGrid);

        direction = gridWorld.actualize(positionInGrid);

        System.out.println(direction);
    }
}
