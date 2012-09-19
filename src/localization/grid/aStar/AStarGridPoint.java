package localization.grid.aStar;

import localization.maze.DirectionalPoint;

public class AStarGridPoint{
    private int step;
    private DirectionalPoint point;

    public AStarGridPoint(DirectionalPoint point, int step) {
        this.step = step;
        this.point = point;
    }

    public int getStep() {
        return step;
    }

    public DirectionalPoint getPoint() {
        return point;
    }

    @Override
    public String toString() {
        return "A*p{" +
                "step=" + step +
                ", point=" + point +
                '}';
    }
}
