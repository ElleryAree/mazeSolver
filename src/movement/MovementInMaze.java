package movement;

import dataTransmitter.GridDataTransmitter;
import head.Sense;
import head.SensorReadings;
import localization.grid.GridWorld;
import localization.grid.PositionInGrid;
import localization.maze.Direction;
import localization.maze.DirectionalPoint;
import main.RobotConstants;

/**
 * Class for movements in the localization.maze:
 * sense walls after each move.
 * <p/>
 * User: elleryaree
 * Date: 8/13/12
 */
public class MovementInMaze extends BasicMovement implements RunnerWithSenses {
    private SensorReadings sense;

    public MovementInMaze(boolean debug){
        super(debug);
    }

    public MovementInMaze() {
        super();
        sense = new Sense();
    }

    @Override
    public void getSense(PositionInGrid position) {
        sense.senseIteration();

        position.setFrontMeasure(GridWorld.getDistanceInCells(sense.getFront()));
        position.setLeftMeasure(GridWorld.getDistanceInCells(sense.getLeft()));
        position.setRightMeasure(GridWorld.getDistanceInCells(sense.getRight()));
        position.setRearMeasure(0);
    }

    @Override
    public void move(Direction direction, PositionInGrid position) {
        DirectionalPoint currentPosition = position.getCurrentPosition();

        Direction directionToTurn = convertDirection(currentPosition.getDirection(), direction);
        currentPosition.setDirection(direction);

        switch (currentPosition.getDirection()) {
            case LEFT:
                currentPosition.setX(currentPosition.getX() - 1);
                break;
            case RIGHT:
                currentPosition.setX(currentPosition.getX() + 1);
                break;
            case BACK:
                currentPosition.setY(currentPosition.getY() - 1);
                break;
            default:
                currentPosition.setY(currentPosition.getY() + 1);
        }

        turn(directionToTurn.getDegrees());
        forward(RobotConstants.ROBOT_LENGTH);
        getSense(position);
    }

    protected static Direction convertDirection(Direction currentDirection, Direction newDirection) {
        GridDataTransmitter.sendInfoMessage("Current position: " + currentDirection + ", new: " + newDirection);

        int computedDirection = newDirection.getDegrees() - currentDirection.getDegrees();
        if (computedDirection > 180){
            computedDirection = 180 - computedDirection;
        }
        if (computedDirection <= -180){
            computedDirection = 360 + computedDirection;
        }

        Direction direction = Direction.parseDegrees(computedDirection);
        GridDataTransmitter.sendInfoMessage("Turn to: " + direction);
        return direction;
    }
}
