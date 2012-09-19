package localization.grid.aStar;

import localization.grid.GridWorld;
import localization.maze.Direction;
import localization.maze.DirectionalPoint;
import localization.maze.MazePoint;

import java.util.*;

public class AStarGridWorld extends GridWorld{
    private Set<MazePoint> visitedArea;
    private List<AStarGridPoint> route;
    private int step = 0;

    public AStarGridWorld(MazePoint goal) {
        super(goal, 1);

        visitedArea = new HashSet<MazePoint>();
        route = new ArrayList<AStarGridPoint>();
    }

    @Override
    protected Direction findWay() {
        if (!checkRoute()){
            searchForTheWay();
        }

        return getNextStep();
    }

    private void searchForTheWay() {
        List<AStarGridPoint> actions = new ArrayList<AStarGridPoint>();
        AStarGridPoint currentPosition = new AStarGridPoint(getRobotLocation(), step++);
        route.add(currentPosition);

        visitedArea.add(currentPosition.getPoint());

        while (!checkGoal(currentPosition.getPoint()) && !findActions(currentPosition.getPoint(), actions)){
            currentPosition = actions.get(actions.size() - 1);
            actions.remove(currentPosition);
            visitedArea.add(currentPosition.getPoint());
            route.add(currentPosition);
            step++;
        }
    }

    private boolean checkGoal(DirectionalPoint currentPosition) {
        int y = currentPosition.getY();
        int x = currentPosition.getX();

        return getGrid()[y][x] == GOAL;
    }

    private boolean findActions(DirectionalPoint position, List<AStarGridPoint> actions) {
        int added = 0;
        List<DirectionalPoint> directionalPoints = initAllPossibleActions(position.getY(), position.getX());
        Object[] sortedActions = directionalPoints.toArray();
        Arrays.sort(sortedActions, new AStarComporator(getGoal()));
        for (Object pointObject: sortedActions){
            DirectionalPoint point = (DirectionalPoint) pointObject;
            if (!visitedArea.contains(point)){
                actions.add(new AStarGridPoint(point, step));
                added++;
            }
        }

        if (added == 0){
            AStarGridPoint last = actions.get(actions.size() - 1);
            route = route.subList(0, last.getStep());
            step = last.getStep();
        }

        return actions.isEmpty();
    }

    private Direction getNextStep() {
        DirectionalPoint nextStep = route.get(0).getPoint();
        route.remove(0);
        return nextStep.getDirection();
    }

    private boolean checkRoute(){
        for (AStarGridPoint point: route){
            if (getGrid()[point.getPoint().getX()][point.getPoint().getY()] == -2){
                return false;
            }
        }

        return !route.isEmpty();
    }

    protected List<AStarGridPoint> getRoute() {
        return route;
    }

    private class AStarComporator implements Comparator {
        private MazePoint goal;
        public AStarComporator(MazePoint goal) {
            this.goal = goal;
        }

        @Override
        public int compare(Object o, Object o1) {
            DirectionalPoint directionalPoint = (DirectionalPoint) o;
            DirectionalPoint directionalPoint1 = (DirectionalPoint) o1;

            int distance = (goal.getX() - directionalPoint.getX()) + (goal.getY() - directionalPoint.getY());
            int distance1 = (goal.getX() - directionalPoint1.getX()) + (goal.getY() - directionalPoint1.getY());

            if (distance1 > distance) return 1;
            if (distance1 < distance) return -1;

            return Math.random() > 0.5 ? 1 : 0;
        }
    }
}
