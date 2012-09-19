package localization.grid;

import localization.maze.Direction;
import localization.maze.DirectionalPoint;
import localization.maze.MazePoint;
import main.RobotConstants;

import java.util.*;
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
    public static final int MAX_WALL_DISTANCE = RobotConstants.ROBOT_LENGTH * MAX_WALL_DISTANCE_IN_CELLS;

    private int[][] grid;
    private DirectionalPoint robotLocation;
    private MazePoint goal;
    private int initialValue;

    public GridWorld(MazePoint goal, int initialValue) {
        int length = getDistanceInCells(goal.getY());
        int width = (int) Math.ceil(Math.abs(goal.getX()) / RobotConstants.ROBOT_LENGTH);
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
        PositionInGrid rotatedPosition = rotateMeasurements(position);
        increaseGridAndUpdateWalls(rotatedPosition);

        return findWay();
    }

    protected abstract Direction findWay();

    protected void increaseGridAndUpdateWalls(PositionInGrid position) {
        int x = getDistanceInCells(position.getCurrentPosition().getX());
        int y = getDistanceInCells(position.getCurrentPosition().getY());

        int coordinateOfTheWallOnTheLeft = x - (position.getLeftMeasurement() < MAX_SENSOR_MEASUREMENT ? getDistanceInCells(position.getLeftMeasurement()) : x);
        int coordinateOfTheWallOnTheRight = x + (position.getRightMeasurement() < MAX_SENSOR_MEASUREMENT ? getDistanceInCells(position.getRightMeasurement()) : 0);
        int coordinateOfTheWallInFront = y + (position.getFrontMeasurement() < MAX_SENSOR_MEASUREMENT ? getDistanceInCells(position.getFrontMeasurement()) : 0);
        int coordinateOfTheWallOnTheBack = y - (position.getBackMeasure() < MAX_SENSOR_MEASUREMENT ? getDistanceInCells(position.getBackMeasure()) : y);

        if (coordinateOfTheWallOnTheLeft < x - MAX_WALL_DISTANCE_IN_CELLS){
            coordinateOfTheWallOnTheLeft = x - MAX_WALL_DISTANCE_IN_CELLS;
        }
        if (coordinateOfTheWallOnTheRight > x + MAX_WALL_DISTANCE_IN_CELLS){
            coordinateOfTheWallOnTheRight = x + MAX_WALL_DISTANCE_IN_CELLS;
        }
        if (coordinateOfTheWallInFront > y + MAX_WALL_DISTANCE_IN_CELLS){
            coordinateOfTheWallInFront = y + MAX_WALL_DISTANCE_IN_CELLS;
        }
        if (coordinateOfTheWallOnTheBack < y - MAX_WALL_DISTANCE_IN_CELLS){
            coordinateOfTheWallOnTheBack = y - MAX_WALL_DISTANCE_IN_CELLS;
        }

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


        if (position.getLeftMeasurement() < MAX_WALL_DISTANCE)
            grid[y + plusToY][coordinateOfTheWallOnTheLeft + plusToX] = WALL;
        if (position.getRightMeasurement() < MAX_WALL_DISTANCE)
            grid[y+ plusToY][coordinateOfTheWallOnTheRight + plusToX] = WALL;
        if (position.getFrontMeasurement() < MAX_WALL_DISTANCE)
            grid[coordinateOfTheWallInFront + plusToY][x + plusToX] = WALL;
        if (position.getBackMeasure() < MAX_WALL_DISTANCE)
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

    public String printGrid(int[][] grid, DirectionalPoint robotLocation){
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

    /*private class DirectionPointComparator implements Comparator<DirectionalPoint> {
        private MazePoint goal;

        public DirectionPointComparator(MazePoint goal) {
            this.goal = goal;
        }

        @Override
        public int compare(DirectionalPoint directionalPoint, DirectionalPoint directionalPoint1) {
            int distance = (goal.getX() - directionalPoint.getX()) + (goal.getY() - directionalPoint.getY());
            int distance1 = (goal.getX() - directionalPoint1.getX()) + (goal.getY() - directionalPoint1.getY());

            if (distance1 > distance) return 1;
            if (distance1 <= distance) return -1;

            return 0;
        }
    }*/
}
