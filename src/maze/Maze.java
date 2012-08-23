package maze;

import java.util.ArrayList;

/**
 * Maze: a collection of sensed points of walls
 * and algorithm for localization, using these points.
 *
 * User: elleryaree
 * Date: 8/13/12
 */
public class Maze {
    private ArrayList<MazePoint> mazePoints;

    public Maze() {
        mazePoints = new ArrayList<MazePoint>();
    }

    public MazeWall getNearestWall(MazePoint currentPosition) {

        getHalf(currentPosition.getX(), currentPosition.getY(), mazePoints);
        getHalf(currentPosition.getY(), currentPosition.getX(), mazePoints);

        return null;
    }

    private void getHalf(double x, double y, ArrayList<MazePoint> source) {
        ArrayList<MazePoint> leftHalf = new ArrayList<MazePoint>();
        ArrayList<MazePoint> rightHalf = new ArrayList<MazePoint>();

        for (MazePoint point : source) {
            if (point.getX() >= x){
                rightHalf.add(point);
            } else {
                leftHalf.add(point);
            }
        }

        MazeWall leftWall = getVerticalHalf(y, leftHalf);
        MazeWall rightWall = getVerticalHalf(y, rightHalf);
    }

    private MazeWall getVerticalHalf(double y, ArrayList<MazePoint> leftHalf) {
        MazePoint closestTopPoint = null;
        MazePoint closestBottomPoint = null;
        double closestTopX = 256;
        double closestBottomX = 256;
        double temp;

        for (MazePoint point: leftHalf){
            temp = Math.sqrt(y*y - point.getY() * point.getY());
            if (point.getY() >= y){
                if (temp < closestTopX){
                    closestTopX = temp;
                    closestTopPoint = point;
                }
            } else {
                if (temp < closestBottomX){
                    closestBottomX = temp;
                    closestBottomPoint = point;
                }
            }
        }

        return new MazeWall(closestBottomPoint, closestTopPoint);
    }
}
