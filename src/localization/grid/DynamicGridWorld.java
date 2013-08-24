package localization.grid;

import localization.maze.Direction;
import localization.maze.DirectionalPoint;
import localization.maze.MazePoint;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 8/19/12
 */
public class DynamicGridWorld extends GridWorld {
    public DynamicGridWorld(MazePoint goal) {
        super(goal, EMPTY);
    }

    @Override
    protected Direction findWay() {
        updateGrid();
        ArrayList<Direction> result = new ArrayList<Direction>();
        getWay(getRobotLocation(), result);

        return result.get(0);
    }

    private void getWay(DirectionalPoint point, ArrayList<Direction> directions){
        int y = point.getY();
        int x = point.getX();
        if (getGrid()[y][x] == GOAL){
            return;
        }

        DirectionalPoint nextPoint = (DirectionalPoint) getMinimumPoint(y, x);

        if (nextPoint == null){
            return;
        }

        //TODO: это нужно переделать на градусы
        switch (point.getDirection()){
            case LEFT:
                if (nextPoint.getDirection() == Direction.FRONT) directions.add(Direction.RIGHT);
                if (nextPoint.getDirection() == Direction.LEFT) directions.add(Direction.FRONT);
                if (nextPoint.getDirection() == Direction.RIGHT) directions.add(Direction.BACK);
                if (nextPoint.getDirection() == Direction.BACK) directions.add(Direction.LEFT);
                break;
            case RIGHT:
                if (nextPoint.getDirection() == Direction.FRONT) directions.add(Direction.LEFT);
                if (nextPoint.getDirection() == Direction.LEFT) directions.add(Direction.BACK);
                if (nextPoint.getDirection() == Direction.RIGHT) directions.add(Direction.FRONT);
                if (nextPoint.getDirection() == Direction.BACK) directions.add(Direction.RIGHT);
                break;
            case BACK:
                if (nextPoint.getDirection() == Direction.FRONT) directions.add(Direction.BACK);
                if (nextPoint.getDirection() == Direction.LEFT) directions.add(Direction.RIGHT);
                if (nextPoint.getDirection() == Direction.RIGHT) directions.add(Direction.LEFT);
                if (nextPoint.getDirection() == Direction.BACK) directions.add(Direction.FRONT);
                break;
            case FRONT:
                directions.add(nextPoint.getDirection());
        }

    }

    protected void updateGrid() {
        boolean finished = false;
        while (!finished) {
            finished = true;
            for (int i = 0; i < getGrid().length; i++) {
                for (int j = 0; j < getGrid()[i].length; j++) {
                    int minCost = getMinimumCost(i, j) + 1;
                    if (minCost != getGrid()[i][j]){
                        finished = false;
                    }
                    getGrid()[i][j] = minCost;
                }
            }
        }
    }

    private int getMinimumCost(int i, int j){
        if (getGrid()[i][j] == GOAL || getGrid()[i][j] == WALL) {
            return getGrid()[i][j] - 1;
        }
        MazePoint action = getMinimumPoint(i, j);

        return action == null ? getGrid()[i][j] - 1 : getGrid()[action.getY()][action.getX()];
    }

    private MazePoint getMinimumPoint(int i, int j) {
        List<DirectionalPoint> actions = initAllPossibleActions(i, j);

        return getMin(actions);
    }

    private MazePoint getMin(List<DirectionalPoint> mazePoints){
        int minValue = 0;
        MazePoint minPoint = null;

        for (MazePoint point: mazePoints){
            int value = getGrid()[point.getY()][point.getX()];
            if (minPoint == null || value < minValue){
                minValue = value;
                minPoint = point;
            }
        }

        return minPoint;
    }
}
