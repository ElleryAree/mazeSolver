package dataTransmitter.processing;

import dataTransmitter.reader.exception.BadReadingsException;
import localization.maze.Direction;
import localization.maze.DirectionalPoint;
import visualize.UpdatableView;

import java.util.ArrayList;
import java.util.List;

public class RouteProcessor extends ContinuingDataProcessor {
    private List<DirectionalPoint> points;


    @Override
    public void processString(String readings) throws BadReadingsException {
        String[] points = readings.split("\\|");
        if (points.length == 0){
            this.points = null;
            return;
        }
        this.points = new ArrayList<DirectionalPoint>(points.length);

        for (String point : points){
            String[] coordinates = point.split(",");
            if (coordinates.length != 3){
                this.points = null;
                return;
            }

            DirectionalPoint mazePoint = new DirectionalPoint();
            mazePoint.setX(Integer.parseInt(coordinates[0]));
            mazePoint.setY(Integer.parseInt(coordinates[1]));
            mazePoint.setDirection(Direction.parseDegrees(Integer.parseInt(coordinates[2])));

            this.points.add(mazePoint);
        }
    }

    @Override
    public void updateView(UpdatableView view) {
        if (points != null)
            view.updateRoute(points);
    }

    public static String getCode(){
        return "RT";
    }
}
