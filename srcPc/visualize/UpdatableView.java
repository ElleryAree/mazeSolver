package visualize;

public interface UpdatableView {
    void showMessage(String message);
    void updateWorld(int[][] grid);
    void updateRobot(int x, int y, int rotation);
}
