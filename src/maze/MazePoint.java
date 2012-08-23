package maze;

/**
 * Sensed point of a wall of the maze.
 *
 * Date: 8/13/12
 * Time: 2:42 AM
 */
public class MazePoint {
    private double x;
    private double y;

    public MazePoint(){

    }

    public MazePoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return (int) x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public int getY() {
        return (int) y;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MazePoint)) return false;

        MazePoint mazePoint = (MazePoint) o;

        if (Double.compare(mazePoint.x, x) != 0) return false;
        if (Double.compare(mazePoint.y, y) != 0) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = x != +0.0d ? Double.doubleToLongBits(x) : 0L;
        result = (int) (temp ^ (temp >>> 32));
        temp = y != +0.0d ? Double.doubleToLongBits(y) : 0L;
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "[" + x + "][" + y + "]";
    }
}
