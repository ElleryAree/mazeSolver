package maze;

/**
 * Date: 8/17/12
 */
public class DirectionalPoint extends MazePoint{
    private Direction direction;

    public DirectionalPoint(Direction direction, double x, double y) {
        super(x, y);
        this.direction = direction;
    }

    public DirectionalPoint(){

    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public String toString() {
        return super.toString() + " " + direction;
    }
}
