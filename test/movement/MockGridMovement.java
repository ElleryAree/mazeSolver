package movement;

import localization.grid.GridWorld;
import localization.grid.PositionInGrid;
import main.RobotConstants;
import localization.maze.Direction;
import localization.maze.DirectionalPoint;

/**
 * Date: 8/19/12
 */
public class MockGridMovement implements RunnerWithSenses {
    private int[][] grid;
    DirectionalPoint currentPosition;

    public MockGridMovement(int[][] grid) {
        this.grid = grid;

        currentPosition = new DirectionalPoint(Direction.FRONT, 0, 0);
    }

    @Override
    public void move(Direction direction, PositionInGrid currentPosition) {
        PositionInGrid position = new PositionInGrid();

        DirectionalPoint newPosition = new DirectionalPoint();
        newPosition.setX(currentPosition.getCurrentPosition().getX());
        newPosition.setY(currentPosition.getCurrentPosition().getY());
        newPosition.setDirection(direction);

        switch (direction) {
            case LEFT: {
                newPosition.setX(newPosition.getX() - RobotConstants.ROBOT_LENGTH);
                break;
            }
            case RIGHT: {
                newPosition.setX(newPosition.getX() + RobotConstants.ROBOT_LENGTH);
                break;
            }
            case BACK: {
                newPosition.setY(newPosition.getY() - RobotConstants.ROBOT_LENGTH);
                break;
            }
            case FRONT:{
                newPosition.setY(newPosition.getY() + RobotConstants.ROBOT_LENGTH);
            }
        }
        position.setCurrentPosition(newPosition);
        getSense(position);

//        return position;
    }

    @Override
    public void getSense(PositionInGrid position) {
        int x = position.getCurrentPosition().getX();
        int y = position.getCurrentPosition().getY();

        for (int i=x; i>=0; i--){
            if (grid[y][i] == GridWorld.WALL){
                position.setLeftMeasure(i);
                break;
            }
        }
        for (int i=x; i<grid[y].length; i++){
            if (grid[y][i] == GridWorld.WALL){
                position.setRightMeasure(i);
                break;
            }
        }
        for (int i=y; i < grid.length; i++){
            if (grid[i][x] == GridWorld.WALL){
                position.setFrontMeasure(i);
                break;
            }
        }


        switch (position.getCurrentPosition().getDirection()){
            case LEFT:
                GridWorld.rotateMeasurements(position);
                break;
            case RIGHT:
                GridWorld.rotateMeasurements(position);
                break;
            case BACK:
                GridWorld.rotateMeasurements(position);
                break;
        }

        position.setFrontMeasure(position.getFrontMeasurement());
        position.setLeftMeasure(position.getLeftMeasurement());
        position.setRightMeasure(position.getRightMeasurement());
        position.setRearMeasure(position.getBackMeasure());
    }
}
