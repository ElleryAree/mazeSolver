package dataTransmitter.processing;


import dataTransmitter.reader.exception.BadReadingsException;
import visualize.UpdatableView;

public class RobotPositionProcessor extends ContinuingDataProcessor{
    private int x;
    private int y;
    private int rotation;

    @Override
    public void processString(String readings) throws BadReadingsException {
        String[] robotLocationParts = readings.split(",");
        if (robotLocationParts.length != 3){
            throw new BadReadingsException("Robot location must contain 3 parts, but was " + robotLocationParts.length + " in data " + readings);
        }

        x = Integer.parseInt(robotLocationParts[0]);
        y = Integer.parseInt(robotLocationParts[1]);
        rotation = Integer.parseInt(robotLocationParts[2]);
    }

    @Override
    public void updateView(UpdatableView view) {
        view.updateRobot(x, y, rotation);
    }

    public static String getCode(){
        return "R";
    }
}
