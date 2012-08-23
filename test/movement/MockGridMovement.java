package movement;

import grid.GridWorld;
import grid.PositionInGrid;
import head.Sense;
import main.RobotConstants;
import maze.Direction;
import maze.DirectionalPoint;

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
    public PositionInGrid move(Direction direction, PositionInGrid currentPosition) {
        PositionInGrid position = new PositionInGrid();

        DirectionalPoint newPosition = new DirectionalPoint();
        newPosition.setX(currentPosition.getCurrentPosition().getX() * (int) RobotConstants.ROBOT_LENGTH);
        newPosition.setY(currentPosition.getCurrentPosition().getY() * (int) RobotConstants.ROBOT_LENGTH);
        newPosition.setDirection(direction);

        switch (direction) {
            case LEFT: {
                newPosition.setX(newPosition.getX() - (int) RobotConstants.ROBOT_LENGTH);
                break;
            }
            case RIGHT: {
                newPosition.setX(newPosition.getX() + (int) RobotConstants.ROBOT_LENGTH);
                break;
            }
            case BACK: {
                newPosition.setY(newPosition.getY() - (int) RobotConstants.ROBOT_LENGTH);
                break;
            }
            case FRONT:{
                newPosition.setY(newPosition.getY() + (int) RobotConstants.ROBOT_LENGTH);
            }
        }
        position.setCurrentPosition(newPosition);
        getSense(position);

        return position;
    }

    @Override
    public void getSense(PositionInGrid position) {
        int x = GridWorld.getDistanceInCells(position.getCurrentPosition().getX());
        int y = GridWorld.getDistanceInCells(position.getCurrentPosition().getY());

        for (int i=x; i>=0; i--){
            if (grid[y][i] == GridWorld.WALL){
                position.setLeftMeasure((int) (i * RobotConstants.ROBOT_LENGTH));
                break;
            }
        }
        for (int i=x; i<grid[y].length; i++){
            if (grid[y][i] == GridWorld.WALL){
                position.setRightMeasure((int) (i * RobotConstants.ROBOT_LENGTH));
                break;
            }
        }
        for (int i=y; i < grid.length; i++){
            if (grid[i][x] == GridWorld.WALL){
                position.setFrontMeasure((int) (i * RobotConstants.ROBOT_LENGTH));
                break;
            }
        }


        PositionInGrid rotatedPosition = position;
        switch (position.getCurrentPosition().getDirection()){
            case LEFT:
                rotatedPosition = GridWorld.rotateMeasurements(position, Direction.RIGHT);
                break;
            case RIGHT:
                rotatedPosition = GridWorld.rotateMeasurements(position, Direction.LEFT);
                break;
            case BACK:
                rotatedPosition = GridWorld.rotateMeasurements(position, Direction.BACK);
                break;
        }

        position.setFrontMeasure(rotatedPosition.getFrontMeasurement());
        position.setLeftMeasure(rotatedPosition.getLeftMeasurement());
        position.setRightMeasure(rotatedPosition.getRightMeasurement());
        position.setRearMeasure(rotatedPosition.getBackMeasure());
    }
}
