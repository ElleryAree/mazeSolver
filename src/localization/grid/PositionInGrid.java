package localization.grid;

import localization.maze.DirectionalPoint;

/**
 * Date: 8/17/12
 */
public class PositionInGrid {
    private DirectionalPoint currentPosition;
    private int leftMeasure = 255;
    private int rightMeasure = 255;
    private int frontMeasure = 255;
    private int backMeasure = 255;

    public DirectionalPoint getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(DirectionalPoint currentPosition) {
        this.currentPosition = currentPosition;
    }

    public int getLeftMeasurement() {
        return leftMeasure;
    }

    public void setLeftMeasure(int leftMeasure) {
        this.leftMeasure = leftMeasure;
    }

    public int getRightMeasurement() {
        return rightMeasure;
    }

    public void setRightMeasure(int rightMeasure) {
        this.rightMeasure = rightMeasure;
    }

    public int getFrontMeasurement() {
        return frontMeasure;
    }

    public void setFrontMeasure(int frontMeasure) {
        this.frontMeasure = frontMeasure;
    }

    public int getBackMeasure() {
        return backMeasure;
    }

    public void setRearMeasure(int backMeasure) {
        this.backMeasure = backMeasure;
    }

    @Override
    public String toString() {
        return "PositionInGrid: " + currentPosition.toString()
                + "\n\tLeft: " + leftMeasure
                + "\n\tRight: " + rightMeasure
                + "\n\tFront: " + frontMeasure;
    }
}
