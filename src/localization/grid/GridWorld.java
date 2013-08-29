package localization.grid;

import dataTransmitter.GridDataTransmitter;
import localization.maze.Direction;
import localization.maze.DirectionalPoint;
import localization.maze.MazePoint;
import main.RobotConstants;

import java.util.ArrayList;
import java.util.List;
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
    public static final int MAX_SENSOR_MEASUREMENT = 255;
    public static final int MAX_WALL_DISTANCE_IN_CELLS = 3;
//    public static final int MAX_WALL_DISTANCE = RobotConstants.ROBOT_LENGTH * MAX_WALL_DISTANCE_IN_CELLS;

    private int[][] grid;
    private DirectionalPoint robotLocation;
    private MazePoint goal;
    private int initialValue;

    public GridWorld(MazePoint goal, int initialValue) {
        int length = Math.abs(goal.getY());
        int width = Math.abs(goal.getX());
        this.goal = new MazePoint(goal.getX() > 0 ? width - 1 : 0, goal.getY() > 0 ? length - 1 : 0);
        this.initialValue = initialValue;

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
                    grid[i][j] = initialValue;
            }
        }
        return grid;
    }

    public DirectionalPoint getRobotLocation() {
        return robotLocation;
    }

    public Direction actualize(PositionInGrid position) {
        rotateMeasurements(position);
        increaseGridAndUpdateWalls(position);

        GridDataTransmitter.convertAndSendData(grid);
        GridDataTransmitter.sendRobotLocation(robotLocation.getX(), robotLocation.getY(),
                robotLocation.getDirection().getDegrees());

        return findWay();
    }

    protected abstract Direction findWay();

    protected List<DirectionalPoint> getRoute(){
        return new ArrayList<DirectionalPoint>();
    }

    protected void increaseGridAndUpdateWalls(PositionInGrid position) {
        int x = position.getCurrentPosition().getX();
        int y = position.getCurrentPosition().getY();

        int coordinateOfTheWallOnTheLeft = x - (position.getLeftMeasurement() < MAX_SENSOR_MEASUREMENT ? position.getLeftMeasurement() : x);
        int coordinateOfTheWallOnTheRight = x + (position.getRightMeasurement() < MAX_SENSOR_MEASUREMENT ? position.getRightMeasurement() : 0);
            int coordinateOfTheWallInFront = y + (position.getFrontMeasurement() < MAX_SENSOR_MEASUREMENT ? position.getFrontMeasurement() : 0);
        int coordinateOfTheWallOnTheBack = y - (position.getBackMeasure() < MAX_SENSOR_MEASUREMENT ? position.getBackMeasure() : y);

        /*if (coordinateOfTheWallOnTheLeft < x){
            coordinateOfTheWallOnTheLeft = x;
        }
        if (coordinateOfTheWallOnTheRight > x){
            coordinateOfTheWallOnTheRight = x;
        }
        if (coordinateOfTheWallInFront > y){
            coordinateOfTheWallInFront = y;
        }
        if (coordinateOfTheWallOnTheBack < y){
            coordinateOfTheWallOnTheBack = y;
        }*/

        int plusToX = coordinateOfTheWallOnTheLeft < 0 ? -1 * coordinateOfTheWallOnTheLeft : 0;
        int plusToY = coordinateOfTheWallOnTheBack < 0 ? -1 * coordinateOfTheWallOnTheBack : 0;

        int newWidth = plusToX + Math.max(grid[0].length, coordinateOfTheWallOnTheRight + 1);
        int newLength = plusToY + Math.max(grid.length, coordinateOfTheWallInFront + 1);

        goal.setY(goal.getY() + plusToY);
        goal.setX(goal.getX() + plusToX);

        position.getCurrentPosition().setY((position.getCurrentPosition().getY()) + plusToY);
        position.getCurrentPosition().setX(position.getCurrentPosition().getX() + plusToX);
        robotLocation = position.getCurrentPosition();
//        robotLocation.setX(robotLocation.getX() + plusToX);
//        robotLocation.setY(robotLocation.getY() + plusToY);

        int[][] newGrid = initGrid(newLength, newWidth);
        for (int i=0; i<grid.length; i++){
            for (int j=0; j<grid[i].length; j++){
                if (grid[i][j] == GOAL || grid[i][j] == WALL){
                    newGrid[i + plusToY][j + plusToX] = grid[i][j];
                }
            }
        }

        grid = newGrid;


        if (position.getLeftMeasurement() != 0)
            grid[y + plusToY][coordinateOfTheWallOnTheLeft + plusToX] = WALL;
        if (position.getRightMeasurement() != 0)
            grid[y+ plusToY][coordinateOfTheWallOnTheRight + plusToX] = WALL;
        if (position.getFrontMeasurement() != 0)
            grid[coordinateOfTheWallInFront + plusToY][x + plusToX] = WALL;
        if (position.getBackMeasure() != 0)
            grid[coordinateOfTheWallOnTheBack + plusToY][x + plusToX] = WALL;
    }

    public int[][] getGrid() {
        return grid;
    }

    public static void rotateMeasurements(PositionInGrid position){
        int leftMeasurement = position.getLeftMeasurement();
        int rightMeasurement = position.getRightMeasurement();
        int frontMeasurement = position.getFrontMeasurement();

        switch (position.getCurrentPosition().getDirection()) {
            case LEFT:
                position.setLeftMeasure(frontMeasurement);
                position.setFrontMeasure(rightMeasurement);
                position.setRearMeasure(leftMeasurement);
                position.setRightMeasure(0);
                break;
            case RIGHT:
                position.setRightMeasure(frontMeasurement);
                position.setRearMeasure(rightMeasurement);
                position.setFrontMeasure(leftMeasurement);
                position.setLeftMeasure(0);
                break;
            case BACK:
                position.setRearMeasure(frontMeasurement);
                position.setLeftMeasure(rightMeasurement);
                position.setRightMeasure(leftMeasurement);
                position.setFrontMeasure(0);
                break;
        }
    }

    public static int getDistanceInCells(double distance) {
        return (int) Math.ceil(Math.abs(distance) / RobotConstants.ROBOT_LENGTH);
    }

    public String printGrid(int[][] grid, DirectionalPoint robotLocation){
        String result = "";
        for (int i=grid.length - 1; i>=0; i--){
            for (int j=0; j<grid[i].length; j++){
                if (j == robotLocation.getX() && i == robotLocation.getY()){
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
                    if (grid[i][j] != GOAL && getRoute().contains(new MazePoint(j, i))){
                        result += "o ";
                    } else {
                        result += grid[i][j] + " ";
                    }
                }
            }
            result += '\n';
        }

        return result;
    }

    public String printGrid(){
        return printGrid(this.grid, this.robotLocation);
    }

    public MazePoint getGoal() {
        return goal;
    }

    protected List<DirectionalPoint> initAllPossibleActions(int i, int j) {
        List<DirectionalPoint> actions = new ArrayList<DirectionalPoint>();

        addAction(i, j - 1, Direction.LEFT, actions);
        addAction(i, j + 1, Direction.RIGHT, actions);
        addAction(i + 1, j, Direction.FRONT, actions);
        addAction(i - 1, j, Direction.BACK, actions);

        return actions;
    }

    private void addAction(int i, int j, Direction direction, List<DirectionalPoint> actions) {
        if (i >= 0 && i < getGrid().length &&
                j >= 0 && j < getGrid()[i].length &&
                (getGrid()[i][j] != EMPTY && getGrid()[i][j] != WALL))
            actions.add(new DirectionalPoint(direction, j, i));
    }
}
