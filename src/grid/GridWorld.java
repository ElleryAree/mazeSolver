package grid;

import console.ConsoleLogger;
import lejos.nxt.Button;
import main.RobotConstants;
import maze.Direction;
import maze.DirectionalPoint;
import maze.MazePoint;

import java.util.ArrayList;
/*
Grid[6][5]

Width ->
[][][][][] l
[][][][][] e
[][][][][] n |
[][][][][] g v
[][][][][] t
[][][][][] h
*/

/**
 * User: elleryaree
 * Date: 8/17/12
 */
public abstract class GridWorld {
    public static final int WALL = -1;
    public static final int EMPTY = -2;
    public static final int GOAL = 0;

    private int[][] grid;
    private DirectionalPoint robotLocation;
    private MazePoint goal;

    public GridWorld(MazePoint goal) {
        int length = getDistanceInCells(goal.getY());
        int width = (int) Math.ceil(Math.abs(goal.getX()) / RobotConstants.ROBOT_LENGTH);
        this.goal = new MazePoint(goal.getX() > 0 ? width - 1 : 0, goal.getY() > 0 ? length - 1 : 0);

        grid = initGrid(length, width);

        robotLocation = new DirectionalPoint(Direction.FRONT,
                goal.getX() <= 0 ? width - 1 : 0,
                goal.getY() <= 0 ? length - 1 : 0);
    }

    private int[][] initGrid(int length, int width) {
        int[][] grid = new int[length][width];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (i != goal.getY() || j != goal.getX())
                    grid[i][j] = EMPTY;
            }
        }
        return grid;
    }

    public DirectionalPoint getRobotLocation() {
        return robotLocation;
    }

    public MazePoint getGoal() {
        return goal;
    }

    public Direction actualize(PositionInGrid position) {
        PositionInGrid rotatedPosition = rotateMeasurements(position);
        increaseGridAndUpdateWalls(rotatedPosition);

        ArrayList<Direction> way = findWay();
        return way.isEmpty()? null : way.get(0);
    }

    protected abstract ArrayList<Direction> findWay();

    protected void increaseGridAndUpdateWalls(PositionInGrid position) {
        int x = getDistanceInCells(position.getCurrentPosition().getX());
        int y = getDistanceInCells(position.getCurrentPosition().getY());

        int coordinateOfTheWallOnTheLeft = x - (position.getLeftMeasurement() < 255 ? getDistanceInCells(position.getLeftMeasurement()) : x);
        int coordinateOfTheWallOnTheRight = x + (position.getRightMeasurement() < 255 ? getDistanceInCells(position.getRightMeasurement()) : 0);
        int coordinateOfTheWallInFront = y + (position.getFrontMeasurement() < 255 ? getDistanceInCells(position.getFrontMeasurement()) : 0);
        int coordinateOfTheWallOnTheBack = y - (position.getBackMeasure() < 255 ? getDistanceInCells(position.getBackMeasure()) : y);

        int plusToX = coordinateOfTheWallOnTheLeft < 0 ? -1 * coordinateOfTheWallOnTheLeft : 0;
        int plusToY = coordinateOfTheWallOnTheBack < 0 ? -1 * coordinateOfTheWallOnTheBack : 0;

        int newWidth = plusToX + Math.max(grid[0].length, coordinateOfTheWallOnTheRight + 1);
        int newLength = plusToY + Math.max(grid.length, coordinateOfTheWallInFront + 1);

        goal.setY(goal.getY() + plusToY);
        goal.setX(goal.getX() + plusToX);

        position.getCurrentPosition().setY((position.getCurrentPosition().getY()) + (plusToY * RobotConstants.ROBOT_LENGTH));
        position.getCurrentPosition().setX(position.getCurrentPosition().getX()
                + (plusToX * RobotConstants.ROBOT_LENGTH));
        robotLocation = position.getCurrentPosition();

        int[][] newGrid = initGrid(newLength, newWidth);
        for (int i=0; i<grid.length; i++){
            for (int j=0; j<grid[i].length; j++){
                if (grid[i][j] == GOAL || grid[i][j] == WALL){
                    newGrid[i + plusToY][j + plusToX] = grid[i][j];
                }
            }
        }

        grid = newGrid;


        if (position.getLeftMeasurement() < 255)
            grid[y + plusToY][coordinateOfTheWallOnTheLeft + plusToX] = WALL;
        if (position.getRightMeasurement() < 255)
            grid[y+ plusToY][coordinateOfTheWallOnTheRight + plusToX] = WALL;
        if (position.getFrontMeasurement() < 255)
            grid[coordinateOfTheWallInFront + plusToY][x + plusToX] = WALL;
        if (position.getBackMeasure() < 255)
            grid[coordinateOfTheWallOnTheBack + plusToY][x + plusToX] = WALL;
    }

    public int[][] getGrid() {
        return grid;
    }

    public static PositionInGrid rotateMeasurements(PositionInGrid position){
        return rotateMeasurements(position, position.getCurrentPosition().getDirection());
    }

    public static PositionInGrid rotateMeasurements(PositionInGrid position, Direction direction) {
        PositionInGrid newPosition = new PositionInGrid();
        newPosition.setCurrentPosition(position.getCurrentPosition());

        switch (direction) {
            case LEFT:
                newPosition.setLeftMeasure(position.getFrontMeasurement());
                newPosition.setRightMeasure(position.getBackMeasure());
                newPosition.setFrontMeasure(position.getRightMeasurement());
                newPosition.setRearMeasure(position.getLeftMeasurement());
                break;
            case RIGHT:
                newPosition.setLeftMeasure(position.getBackMeasure());
                newPosition.setRightMeasure(position.getFrontMeasurement());
                newPosition.setFrontMeasure(position.getLeftMeasurement());
                newPosition.setRearMeasure(position.getRightMeasurement());
                break;
            case BACK:
                newPosition.setLeftMeasure(position.getRightMeasurement());
                newPosition.setRightMeasure(position.getLeftMeasurement());
                newPosition.setFrontMeasure(position.getBackMeasure());
                newPosition.setRearMeasure(position.getFrontMeasurement());
                break;
            case FRONT:
                newPosition = position;
        }
        return newPosition;
    }

    public static int getDistanceInCells(double distance) {
        return (int) Math.ceil(Math.abs(distance) / RobotConstants.ROBOT_LENGTH);
    }

    public String printGrid(){
        String result = "";
        for (int i=grid.length - 1; i>=0; i--){
            for (int j=0; j<grid[i].length; j++){
                if (j == getDistanceInCells(robotLocation.getX()) && i == getDistanceInCells(robotLocation.getY())){
                    switch (robotLocation.getDirection()){
                        case FRONT:
                            result += "^ ";
                            break;
                        case BACK:
                            result += "v ";
                            break;
                        case RIGHT:
                            result += "> ";
                            break;
                        case LEFT:
                            result += "< ";
                    }
                } else if(grid[i][j] == -1){
                    result += "W ";
                } else if (grid[i][j] == -2){
                    result += "- ";
                } else {
                    result += grid[i][j] + " ";
                }
            }
            result += '\n';
        }

        return result;
    }
}
