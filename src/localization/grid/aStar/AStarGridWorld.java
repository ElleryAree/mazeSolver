package localization.grid.aStar;

import console.LoggerProvider;
import dataTransmitter.GridDataTransmitter;
import localization.grid.GridWorld;
import localization.maze.Direction;
import localization.maze.DirectionalPoint;
import localization.maze.MazePoint;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AStarGridWorld extends GridWorld{
    private List<MazePoint> visitedArea;
    private List<DirectionalPoint> route;

    public AStarGridWorld(MazePoint goal) {
        super(goal, 1);

        visitedArea = new ArrayList<MazePoint>();
    }

    @Override
    protected Direction findWay() {
        if (!checkRoute()){
            searchForTheWay();
            LoggerProvider.sendMessage("Route: " + route);
        }

        GridDataTransmitter.sendRouteMessage(route);
        return getNextStep();
    }

    @Override
    protected List<DirectionalPoint> getRoute() {
        if (route == null) return super.getRoute();

        return route;
    }

    private void searchForTheWay() {
        int y = getRobotLocation().getY();
        int x = getRobotLocation().getX();

        ArrayList<List<DirectionalPoint>> possibleRoutes = new ArrayList<List<DirectionalPoint>>();
        List<DirectionalPoint> actions;
        DirectionalPoint currentPosition = new DirectionalPoint(Direction.FRONT, x, y);
        visitedArea.clear();
        route = new ArrayList<DirectionalPoint>(1);
        route.add(currentPosition);
        possibleRoutes.add(route);

        while (!possibleRoutes.isEmpty()){
            route = getMinimumRoute(possibleRoutes);
            if (route == null){
                break;
            }
            currentPosition = route.get(route.size() - 1);
            if (checkGoal(currentPosition)){
                return;
            }

            visitedArea.add(currentPosition);

            actions = initAllPossibleActions(currentPosition.getY(), currentPosition.getX());

            for (DirectionalPoint point: actions){
                List<DirectionalPoint> newRoute = new ArrayList<DirectionalPoint>(route.size() + 1);
                newRoute.addAll(route);
                newRoute.add(point);
                possibleRoutes.add(newRoute);
            }
        }

        GridDataTransmitter.sendFinishedMessage("Failed: No route!");
        throw new RuntimeException("No route");
    }

    private List<DirectionalPoint> getMinimumRoute(ArrayList<List<DirectionalPoint>> possibleRoutes) {
        List<DirectionalPoint> shortestRoute = null;
        int shortestLength = -1;

        Iterator<List<DirectionalPoint>> possibleRoutesIterator = possibleRoutes.iterator();
        while (possibleRoutesIterator.hasNext()){
            List<DirectionalPoint> route = possibleRoutesIterator.next();

            if (route.size() == 0 || visitedArea.contains(route.get(route.size() - 1))){
                possibleRoutesIterator.remove();
                continue;
            }

            int length = getLength(route);

            if (shortestLength == -1 || length < shortestLength){
                shortestLength = length;
                shortestRoute = route;
            }
        }

        possibleRoutes.remove(shortestRoute);
        return shortestRoute;
    }

    private boolean checkGoal(DirectionalPoint currentPosition) {
        int y = currentPosition.getY();
        int x = currentPosition.getX();

        return x == getGoal().getX() && y == getGoal().getY();
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
            if (getGrid()[point.getY()][point.getX()] == -2){
                return false;
            }
        }

        return !route.isEmpty();
    }

    protected List<DirectionalPoint> getPossibleRoutes() {
        return route;
    }

    private int getLength(List<DirectionalPoint> route) {
        MazePoint goal = getGoal();
        DirectionalPoint directionalPoint = route.get(route.size() - 1);
        return route.size() + (goal.getX() - directionalPoint.getX()) + (goal.getY() - directionalPoint.getY());
    }
}
