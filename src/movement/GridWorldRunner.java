package movement;

import console.LoggerProvider;
import lejos.nxt.LCD;
import localization.grid.GridWorld;
import localization.grid.PositionInGrid;
import localization.grid.aStar.AStarGridWorld;
import localization.maze.Direction;
import localization.maze.DirectionalPoint;
import localization.maze.MazePoint;

import java.util.ArrayList;

/**
 * Date: 8/19/12
 */
public class GridWorldRunner {
    private GridWorld gridWorld;
    private RunnerWithSenses movement;
    private ArrayList<Direction> history;

    public GridWorldRunner(MazePoint goal, RunnerWithSenses runner) {
        gridWorld = new AStarGridWorld(goal);
        movement = runner;
        history = new ArrayList<Direction>();
    }
    public void run(){
        try{
            runDangerous();
        } catch (Throwable e){
            LoggerProvider.sendMessage("Ooops: " + e.toString());
        }
    }

    public void runDangerous(){
        PositionInGrid position = new PositionInGrid();
        position.setCurrentPosition(new DirectionalPoint(Direction.FRONT, 0, 0));
        movement.getSense(position);

        Direction direction = gridWorld.actualize(position);

        logState(position, direction);

        while (direction != null){
            history.add(direction);
            position = movement.move(direction, position);
            direction = gridWorld.actualize(position);

            logState(position, direction);

            if (isGoal(position)){
                LCD.drawString("DONE!!!", 0, 3);
                LoggerProvider.sendMessage("Done");
                break;
            }

            if (direction == null){
                LCD.drawString("Failed :(", 0, 3);
                LoggerProvider.sendMessage("Failed");
                break;
            }

            printGrid();
        }

        LoggerProvider.sendMessage(history.toString());
    }

    private void logState(PositionInGrid position, Direction direction) {
        LoggerProvider.sendMessage("I'm here: " + position);
        LoggerProvider.sendMessage("That's what I think I'm in:\n" + printGrid());
        LoggerProvider.sendMessage("Next action:\n" + direction);
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
