package head;

import grid.GridWorld;
import grid.PositionInGrid;
import main.RobotConstants;
import maze.Direction;

public class FakeSense implements SensorReadings{

    private int[][] grid;
    public FakeSense(int[][] grid) {
        this.grid = grid;
    }

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

    @Override
    public void senseIteration() {
        System.out.println("Sence iteration");
        //do nothing
    }

    @Override
    public int getLeft() {
        return 255;
    }

    @Override
    public int getRight() {
        return 255;
    }

    @Override
    public int getFront() {
        return 255;
    }
}
