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

    public static Direction parseDegrees(int degree){
        if (degree == LEFT.degrees)
            return LEFT;
        else if (degree == RIGHT.degrees)
            return RIGHT;
        else if (degree == FRONT.degrees)
            return FRONT;
        else return BACK;
    }
}
