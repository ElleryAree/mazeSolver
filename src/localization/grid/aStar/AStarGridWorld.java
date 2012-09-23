package localization.grid.aStar;

import localization.grid.GridWorld;
import localization.maze.Direction;
import localization.maze.DirectionalPoint;
import localization.maze.MazePoint;

import java.util.*;

public class AStarGridWorld extends GridWorld{
    private Set<MazePoint> visitedArea;
    private List<DirectionalPoint> route;
    AStarComparator aStarComparator;
    private int step = 0;

    public AStarGridWorld(MazePoint goal) {
        super(goal, 1);

        visitedArea = new HashSet<MazePoint>();
        aStarComparator = new AStarComparator(getGoal());
    }

    @Override
    protected Direction findWay() {
        if (!checkRoute()){
            searchForTheWay();
        }

        return getNextStep();
    }

    private void searchForTheWay() {
        ArrayList<List<DirectionalPoint>> possibleRoutes = new ArrayList<List<DirectionalPoint>>();
        List<DirectionalPoint> actions;
        DirectionalPoint currentPosition = getRobotLocation();
        route = Arrays.asList(currentPosition);
        possibleRoutes.add(route);

        visitedArea.add(currentPosition);

        while (!checkGoal(currentPosition) && !possibleRoutes.isEmpty()){
            route = getMinimumRoute(possibleRoutes);


            currentPosition = route.get(route.size() - 1);

            actions = findActions(currentPosition);
            visitedArea.addAll(actions);

            possibleRoutes.remove(route);
            for (DirectionalPoint point: actions){
                List<DirectionalPoint> newRoute = new ArrayList<DirectionalPoint>(route.size() + 1);
                newRoute.addAll(route);
                newRoute.add(point);
                possibleRoutes.add(newRoute);
            }
        }
    }

    private List<DirectionalPoint> findActions(DirectionalPoint position) {
        List<DirectionalPoint> allActions = initAllPossibleActions(position.getY(), position.getX());
        List<DirectionalPoint> actions = new ArrayList<DirectionalPoint>();
        for (DirectionalPoint action: allActions){
            if (!visitedArea.contains(action)){
                actions.add(action);
            }
        }
        return actions;
    }


    private List<DirectionalPoint> getMinimumRoute(ArrayList<List<DirectionalPoint>> possibleRoutes) {
        Object[] routes = possibleRoutes.toArray();
        Arrays.sort(routes, aStarComparator);

        return (List<DirectionalPoint>) routes[0];
    }

    private boolean checkGoal(DirectionalPoint currentPosition) {
        int y = currentPosition.getY();
        int x = currentPosition.getX();

        return getGrid()[y][x] == GOAL;
    }

    private Direction getNextStep() {
        DirectionalPoint nextStep = route.get(0);
        route.remove(0);
        return nextStep.getDirection();
    }

    /**
     * Checkes if current shortest route is still valid,
     * which is, there is no new obstacles on thr route.
     * Otherwise there is need of new route.
     *
     * @return true, if there is no new obstacles on the route. Othewise false.
     */
    private boolean checkRoute(){
        if (route == null){
            return false;
        }

        for (DirectionalPoint point: route){
            if (getGrid()[point.getX()][point.getY()] == -2){
                return false;
            }
        }

        return !route.isEmpty();
    }

    protected List<DirectionalPoint> getPossibleRoutes() {
        return route;
    }

    private class AStarComparator implements Comparator {
        private MazePoint goal;
        public AStarComparator(MazePoint goal) {
            this.goal = goal;
        }

        @Override
        public int compare(Object o, Object o1) {
            List<DirectionalPoint> route = (List<DirectionalPoint>) o;
            List<DirectionalPoint> route1 = (List<DirectionalPoint>) o1;

            DirectionalPoint directionalPoint = route.get(route.size() - 1);
            DirectionalPoint directionalPoint1 = route1.get(route1.size() - 1);

            int distance = route.size() + (goal.getX() - directionalPoint.getX()) + (goal.getY() - directionalPoint.getY());
            int distance1 = route1.size() + (goal.getX() - directionalPoint1.getX()) + (goal.getY() - directionalPoint1.getY());

            if (distance1 > distance) return 1;
            if (distance1 < distance) return -1;

            return Math.random() > 0.5 ? 1 : 0;
        }
    }
}
