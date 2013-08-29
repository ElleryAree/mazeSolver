package visualize;

public abstract class ConsoleDisplay implements UpdatableView {
    @Override
    public void showMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void updateWorld(int[][] grid) {
        //do nothing yet
    }

    @Override
    public void updateRobot(int x, int y, int rotation) {
        //do nothing yet
    }
}
