package localization;

import head.SensorReadings;
import main.RobotConstants;
import maze.Direction;

import java.util.ArrayList;

/**
 * Evaluates sensor readings and computes possible actions
 *
 * User: elleryaree
 * Date: 8/14/12
 */
public class Localizer {

    public static ArrayList<Direction> getActions(SensorReadings readings){
        ArrayList<Direction> actions = new ArrayList<Direction>();

        if (readings.getLeft() > RobotConstants.ROBOT_LENGTH){
            actions.add(Direction.LEFT);
        }
        if (readings.getFront() > RobotConstants.ROBOT_LENGTH){
            actions.add(Direction.FRONT);
        }
        if (readings.getRight() > RobotConstants.ROBOT_LENGTH){
            actions.add(Direction.RIGHT);
        }

        return actions;
    }
}
