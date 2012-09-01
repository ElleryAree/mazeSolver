package localization.maze;

/**
 * User: elleryaree
 * Date: 8/13/12
 */
public enum Direction {
    LEFT(90), RIGHT(-90), FRONT(0), BACK(180);

    private int degrees;

    private Direction(int degrees) {
        this.degrees = degrees;
    }

    public int getDegrees() {
        return degrees;
    }
}
