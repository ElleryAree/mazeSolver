package movement;

import console.ConsoleLogger;
import grid.DynamicGridWorld;
import grid.GridWorld;
import grid.PositionInGrid;
import lejos.nxt.Button;
import lejos.nxt.LCD;
import maze.Direction;
import maze.DirectionalPoint;
import maze.MazePoint;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Date: 8/19/12
 */
public class GridWorldRunner {
    private GridWorld gridWorld;
    private RunnerWithSenses movement;
    private ArrayList<Direction> history;

    public GridWorldRunner(MazePoint goal, RunnerWithSenses runner) {
        gridWorld = new DynamicGridWorld(goal);
        movement = runner;
        history = new ArrayList<Direction>();
    }
    public void run(){
        try{
            runDangerouse();
        } catch (Throwable e){
            ConsoleLogger.getLogger().message("Ooops: " + e.getMessage());
        }
    }

    public void runDangerouse(){
        PositionInGrid position = new PositionInGrid();
        position.setCurrentPosition(new DirectionalPoint(Direction.FRONT, 0, 0));
        movement.getSense(position);
        ConsoleLogger.getLogger().message("I was set here: " + position);

        Direction direction = gridWorld.actualize(position);


        while (direction != null){
            ConsoleLogger.getLogger().message("I'm here: " + position);
            ConsoleLogger.getLogger().message("That's what I think I'm in:\n" + printGrid());
            ConsoleLogger.getLogger().message("Next action:\n" + direction);

            history.add(direction);
            position = movement.move(direction, position);
            direction = gridWorld.actualize(position);
            if (isGoal(position)){
                LCD.drawString("DONE!!!", 0, 3);
                ConsoleLogger.getLogger().message("Done");
                break;
            }

            if (direction == null){
                LCD.drawString("Failed :(", 0, 3);
                ConsoleLogger.getLogger().message("Failed");
                break;
            }

            printGrid();
        }

        ConsoleLogger.getLogger().message(history.toString());
    }

    private boolean isGoal(PositionInGrid position) {
        int y = GridWorld.getDistanceInCells(position.getCurrentPosition().getY());
        int x = GridWorld.getDistanceInCells(position.getCurrentPosition().getX());
        return gridWorld.getGrid()[y][x] == GridWorld.GOAL;
    }

    public String printGrid(){
        return gridWorld.printGrid();
    }
}
