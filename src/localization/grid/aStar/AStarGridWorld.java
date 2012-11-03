package localization.grid.aStar;

import console.LoggerProvider;
import localization.grid.GridWorld;
import localization.maze.Direction;
import localization.maze.DirectionalPoint;
import localization.maze.MazePoint;

import java.util.ArrayList;
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

        return getNextStep();
    }

    private void searchForTheWay() {
        int y = getDistanceInCells(getRobotLocation().getY());
        int x = getDistanceInCells(getRobotLocation().getX());

        ArrayList<List<DirectionalPoint>> possibleRoutes = new ArrayList<List<DirectionalPoint>>();
        List<DirectionalPoint> actions;
        DirectionalPoint currentPosition = new DirectionalPoint(Direction.FRONT, x, y);
        visitedArea.clear();
        route = new ArrayList<DirectionalPoint>(1);
        route.add(currentPosition);
        possibleRoutes.add(route);

        visitedArea.add(currentPosition);
        LoggerProvider.sendMessage("Current position: " + currentPosition);

        while (!possibleRoutes.isEmpty()){
//            LoggerProvider.sendMessage("Search step");
//            LoggerProvider.sendMessage("\tCurrent position: " + currentPosition);
//            LoggerProvider.sendMessage("\tPossible routes: " + possibleRoutes.size());
//            LoggerProvider.sendMessage("\tVisitedArea: " + visitedArea.size());
//            LoggerProvider.sendMessage("\tGoal: " + getGoal());
//            LoggerProvider.sendMessage(printGrid());

            route = getMinimumRoute(possibleRoutes);

            LoggerProvider.sendMessage("\tShortest route: " + route);

            currentPosition = route.get(route.size() - 1);
            if (checkGoal(currentPosition)){
                return;
            }

            actions = findActions(currentPosition);
            LoggerProvider.sendMessage("\tActions: " + actions);
            visitedArea.addAll(actions);

            possibleRoutes.remove(route);
            for (DirectionalPoint point: actions){
                List<DirectionalPoint> newRoute = new ArrayList<DirectionalPoint>(route.size() + 1);
                newRoute.addAll(route);
                newRoute.add(point);
                possibleRoutes.add(newRoute);
            }
        }

        throw new RuntimeException("No route");
    }

    private List<DirectionalPoint> findActions(DirectionalPoint position) {
        int y = getDistanceInCells(position.getY());
        int x = getDistanceInCells(position.getX());

        List<DirectionalPoint> allActions = initAllPossibleActions(y, x);
        List<DirectionalPoint> actions = new ArrayList<DirectionalPoint>();
        for (DirectionalPoint action: allActions){
            if (!visitedArea.contains(action)){
                actions.add(action);
            }
        }
        return actions;
    }


    private List<DirectionalPoint> getMinimumRoute(ArrayList<List<DirectionalPoint>> possibleRoutes) {
        List<DirectionalPoint> shortestRoute = possibleRoutes.get(0);
        int shortestLength = getLength(shortestRoute);

        for (List<DirectionalPoint> route: possibleRoutes){
            int length = getLength(route);

            if (length < shortestLength){
                shortestLength = length;
                shortestRoute = route;
            }
        }

        return shortestRoute;
    }

    private boolean checkGoal(DirectionalPoint currentPosition) {
        int y = currentPosition.getY();
        int x = currentPosition.getX();

        LoggerProvider.sendMessage("Position: " + x + ", " + y);
        LoggerProvider.sendMessage("Goal: " + getGoal().getX() + ", " + getGoal().getY());
        LoggerProvider.sendMessage("");

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
        /*if (route == null){
            return false;
        }

        for (DirectionalPoint point: route){
            if (getGrid()[point.getX()][point.getY()] == -2){
                return false;
            }
        }

        return !route.isEmpty();*/
        return false;
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
