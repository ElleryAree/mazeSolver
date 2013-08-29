package visualize;

import localization.maze.DirectionalPoint;

import java.util.List;

public interface UpdatableView {
    void showMessage(String message);
    void updateWorld(int[][] grid);
    void updateRobot(int x, int y, int rotation);
    void updateRoute(List<DirectionalPoint> points);
}
