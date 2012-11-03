package localization.grid.aStar;

import console.LoggerProvider;
import localization.grid.GridWorld;
import localization.grid.PositionInGrid;
import localization.maze.Direction;
import localization.maze.DirectionalPoint;
import localization.maze.MazePoint;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static main.RobotConstants.ROBOT_LENGTH;

public class AStarTest {
    @Before
    public void setUp(){
        LoggerProvider.initiateLogger(true, true);
    }

    @Test
    public void testPositivePositive(){
        MazePoint goal = new MazePoint(5 * ROBOT_LENGTH, 5 * ROBOT_LENGTH);
        AStarGridWorld gridWorld = new AStarGridWorld(goal);

        PositionInGrid position = new PositionInGrid();
        position.setCurrentPosition(new DirectionalPoint(Direction.FRONT, 2, 0));

        gridWorld.getGrid()[3][1] = GridWorld.WALL;
        gridWorld.getGrid()[3][2] = GridWorld.WALL;
        gridWorld.getGrid()[3][3] = GridWorld.WALL;
        gridWorld.getGrid()[3][4] = GridWorld.WALL;

        int[][] grid = gridWorld.getGrid();
        System.out.print(gridWorld.printGrid());

        gridWorld.actualize(position);
        List<DirectionalPoint> route = gridWorld.getPossibleRoutes();


        int i = 0;
        for (DirectionalPoint point: route){
            grid[point.getY()][point.getX()] = i++;
        }

        System.out.println(route);
        System.out.println(gridWorld.printGrid(grid, new DirectionalPoint(Direction.FRONT, -1, -1)));
    }

    @Test(expected = RuntimeException.class)
    public void testCompleteWall(){
        MazePoint goal = new MazePoint(5 * ROBOT_LENGTH, 5 * ROBOT_LENGTH);
        AStarGridWorld gridWorld = new AStarGridWorld(goal);

        PositionInGrid position = new PositionInGrid();
        position.setCurrentPosition(new DirectionalPoint(Direction.FRONT, 2, 0));

        gridWorld.getGrid()[3][0] = GridWorld.WALL;
        gridWorld.getGrid()[3][1] = GridWorld.WALL;
        gridWorld.getGrid()[3][2] = GridWorld.WALL;
        gridWorld.getGrid()[3][3] = GridWorld.WALL;
        gridWorld.getGrid()[3][4] = GridWorld.WALL;

        int[][] grid = gridWorld.getGrid();
        System.out.print(gridWorld.printGrid());

        gridWorld.actualize(position);
        List<DirectionalPoint> route = gridWorld.getPossibleRoutes();


        int i = 0;
        for (DirectionalPoint point: route){
            grid[point.getY()][point.getX()] = i++;
        }

        System.out.println(route);
        System.out.println(gridWorld.printGrid(grid, new DirectionalPoint(Direction.FRONT, -1, -1)));
    }

    @Test
    public void testBigWall(){
        MazePoint goal = new MazePoint(5 * ROBOT_LENGTH, 5 * ROBOT_LENGTH);
        AStarGridWorld gridWorld = new AStarGridWorld(goal);

        PositionInGrid position = new PositionInGrid();
        position.setCurrentPosition(new DirectionalPoint(Direction.FRONT, 2, 0));

        gridWorld.getGrid()[3][1] = GridWorld.WALL;
        gridWorld.getGrid()[3][2] = GridWorld.WALL;
        gridWorld.getGrid()[3][3] = GridWorld.WALL;
        gridWorld.getGrid()[2][3] = GridWorld.WALL;
        gridWorld.getGrid()[1][3] = GridWorld.WALL;

        int[][] grid = gridWorld.getGrid();
        System.out.print(gridWorld.printGrid());

        gridWorld.actualize(position);
        List<DirectionalPoint> route = gridWorld.getPossibleRoutes();


        int i = 0;
        for (DirectionalPoint point: route){
            grid[point.getY()][point.getX()] = i++;
        }

        System.out.println(route);
        System.out.println(gridWorld.printGrid(grid, new DirectionalPoint(Direction.FRONT, -1, -1)));
    }

    @Test
    public void testBigWall2(){
        MazePoint goal = new MazePoint(5 * ROBOT_LENGTH, 5 * ROBOT_LENGTH);
        AStarGridWorld gridWorld = new AStarGridWorld(goal);

        PositionInGrid position = new PositionInGrid();
        position.setCurrentPosition(new DirectionalPoint(Direction.FRONT, 0, 1));

        gridWorld.getGrid()[3][1] = GridWorld.WALL;
        gridWorld.getGrid()[3][2] = GridWorld.WALL;
        gridWorld.getGrid()[3][3] = GridWorld.WALL;
        gridWorld.getGrid()[2][3] = GridWorld.WALL;
        gridWorld.getGrid()[1][3] = GridWorld.WALL;

        int[][] grid = gridWorld.getGrid();
        System.out.print(gridWorld.printGrid());

        gridWorld.actualize(position);
        List<DirectionalPoint> route = gridWorld.getPossibleRoutes();


        int i = 0;
        for (DirectionalPoint point: route){
            grid[point.getY()][point.getX()] = i++;
        }

        System.out.println(route);
        System.out.println(gridWorld.printGrid(grid, new DirectionalPoint(Direction.FRONT, -1, -1)));
    }
}
