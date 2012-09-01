package movement;


import console.LoggerProvider;
import localization.grid.PositionInGrid;
import lejos.nxt.Button;
import localization.maze.Direction;
import localization.maze.DirectionalPoint;

import java.util.ArrayList;

public class MovementTest {
    public static void test(){
        System.out.println("Press any key");
        Button.waitForAnyPress();
        MovementInMaze movementInMaze = new MovementInMaze();

        ArrayList<Direction> directions = new ArrayList<Direction>();
        DirectionalPoint point = new DirectionalPoint(Direction.FRONT, 0, 0);
        PositionInGrid positionInGrid = new PositionInGrid();
        positionInGrid.setCurrentPosition(point);

        directions.add(Direction.FRONT);
        directions.add(Direction.RIGHT);
        directions.add(Direction.LEFT);
        directions.add(Direction.BACK);

        LoggerProvider.sendMessage("Plan is" + directions);
        for (Direction direction: directions){
            positionInGrid = movementInMaze.move(direction, positionInGrid);
        }

        System.out.println(positionInGrid.getCurrentPosition().toString());
        Button.waitForAnyPress();
    }
}
