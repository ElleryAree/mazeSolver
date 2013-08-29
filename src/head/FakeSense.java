package head;

import localization.grid.GridWorld;
import localization.grid.PositionInGrid;
import main.RobotConstants;

public class FakeSense implements SensorReadings{

    private int[][] grid;
    public FakeSense(int[][] grid) {
        this.grid = grid;
    }

    public void getSense(PositionInGrid position) {
        int x = position.getCurrentPosition().getX();
        int y = position.getCurrentPosition().getY();

        for (int i=x; i>=0; i--){
            if (grid[y][i] == GridWorld.WALL){
                position.setLeftMeasure(i * RobotConstants.ROBOT_LENGTH);
                break;
            }
        }
        for (int i=x; i<grid[y].length; i++){
            if (grid[y][i] == GridWorld.WALL){
                position.setRightMeasure(i * RobotConstants.ROBOT_LENGTH);
                break;
            }
        }
        for (int i=y; i < grid.length; i++){
            if (grid[i][x] == GridWorld.WALL){
                position.setFrontMeasure(i * RobotConstants.ROBOT_LENGTH);
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
