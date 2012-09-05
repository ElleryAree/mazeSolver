package movement;

import localization.grid.PositionInGrid;
import head.Sense;
import head.SensorReadings;
import main.RobotConstants;
import localization.maze.Direction;
import localization.maze.DirectionalPoint;

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

        position.setFrontMeasure(sense.getFront());
        position.setLeftMeasure(sense.getLeft());
        position.setRightMeasure(sense.getRight());
    }

    @Override
    public PositionInGrid move(Direction direction, PositionInGrid currentPosition) {
        PositionInGrid position = new PositionInGrid();

        DirectionalPoint newPosition = new DirectionalPoint();
        newPosition.setX(currentPosition.getCurrentPosition().getX());
        newPosition.setY(currentPosition.getCurrentPosition().getY());
        Direction currentDirection = currentPosition.getCurrentPosition().getDirection();

        switch (direction) {
            case LEFT: {
                if (currentDirection == Direction.LEFT){
                    newPosition.setDirection(Direction.BACK);
                } else if (currentDirection == Direction.BACK){
                    newPosition.setDirection(Direction.RIGHT);
                } else if (currentDirection == Direction.RIGHT){
                    newPosition.setDirection(Direction.FRONT);
                } else{
                    newPosition.setDirection(Direction.LEFT);
                }
                break;
            }
            case RIGHT: {
                if (currentDirection == Direction.LEFT){
                    newPosition.setDirection(Direction.FRONT);
                } else if (currentDirection == Direction.BACK){
                    newPosition.setDirection(Direction.LEFT);
                } else if (currentDirection == Direction.RIGHT){
                    newPosition.setDirection(Direction.BACK);
                } else{
                    newPosition.setDirection(Direction.RIGHT);
                }
                break;
            }
            case BACK: {
                if (currentDirection == Direction.LEFT){
                    newPosition.setDirection(Direction.RIGHT);
                } else if (currentDirection == Direction.BACK){
                    newPosition.setDirection(Direction.FRONT);
                } else if (currentDirection == Direction.RIGHT){
                    newPosition.setDirection(Direction.LEFT);
                } else{
                    newPosition.setDirection(Direction.BACK);
                }
                break;
            }
            case FRONT:{
                newPosition.setDirection(direction);
            }
        }

        switch (newPosition.getDirection()) {
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
        turn(direction.getDegrees());
        forward(RobotConstants.ROBOT_LENGTH);
        position.setCurrentPosition(newPosition);
        getSense(position);

        return position;
    }

    protected void setSense(SensorReadings sense) {
        this.sense = sense;
    }
}
