package movement;


import console.LoggerProvider;
import dataTransmitter.GridDataTransmitter;
import lejos.nxt.Button;
import localization.grid.PositionInGrid;
import localization.maze.Direction;
import localization.maze.DirectionalPoint;

import java.util.ArrayList;
import java.util.List;

public class MovementTest {
    public static void test(){
        System.out.println("Press any key");
        Button.waitForAnyPress();
        MovementInMaze movementInMaze = new MovementInMaze();

        ArrayList<Direction> directions = new ArrayList<Direction>();
        DirectionalPoint point = new DirectionalPoint(Direction.FRONT, 0, 0);
        PositionInGrid positionInGrid = new PositionInGrid();
        positionInGrid.setCurrentPosition(point);

        generateGrandRoute(directions);


        LoggerProvider.sendMessage("Plan is" + directions);
        for (Direction direction: directions){
            movementInMaze.move(direction, positionInGrid);
        }

        System.out.println(positionInGrid.getCurrentPosition().toString());
        GridDataTransmitter.sendFinishedMessage("Done!");
        Button.waitForAnyPress();
    }

    protected static void generateGrandRoute(List<Direction> directions) {
        directions.add(Direction.FRONT);
        directions.add(Direction.FRONT);
        directions.add(Direction.RIGHT);
        directions.add(Direction.FRONT);
        directions.add(Direction.LEFT);
        directions.add(Direction.FRONT);
        directions.add(Direction.BACK);

        directions.add(Direction.BACK);
        directions.add(Direction.RIGHT);
        directions.add(Direction.BACK);
        directions.add(Direction.LEFT);

        directions.add(Direction.LEFT);
        directions.add(Direction.RIGHT);

        directions.add(Direction.RIGHT);
        directions.add(Direction.LEFT);
        directions.add(Direction.BACK);
        directions.add(Direction.FRONT);
    }
}
