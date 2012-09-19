package localization.grid.aStar;

import localization.grid.GridWorld;
import localization.grid.PositionInGrid;
import localization.maze.Direction;
import localization.maze.DirectionalPoint;
import localization.maze.MazePoint;
import org.junit.Test;

import java.util.List;

import static main.RobotConstants.ROBOT_LENGTH;

public class AStarTest {
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
        List<AStarGridPoint> route = gridWorld.getRoute();


        for (AStarGridPoint point: route){
            grid[point.getPoint().getY()][point.getPoint().getX()] = point.getStep();
        }

        System.out.println(route);
        System.out.println(gridWorld.printGrid(grid, position.getCurrentPosition()));
    }
}
