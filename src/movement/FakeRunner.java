package movement;


import localization.grid.GridWorld;
import localization.grid.PositionInGrid;

public class FakeRunner extends MovementInMaze {
    private int[][] grid;

    public FakeRunner(int[][] grid) {
        super(true);
        this.grid = grid;
    }

    @Override
    protected void forward(int distance) {
        //do nothing
    }

    @Override
    protected void turn(int angle) {
        //do nothing
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
