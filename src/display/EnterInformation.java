package display;

import lejos.nxt.*;
import main.RobotConstants;
import maze.MazePoint;

/**
 * Date: 8/19/12
 */
@SuppressWarnings("ALL")
public class EnterInformation {
    private static final int X_LINE = 3;
    private static final int Y_LINE = 5;

    public static MazePoint enter(){
        LCD.drawString("Enter goal: (in", 0, 0);
        LCD.drawString("robot's lengths)", 0, 1);
        LCD.drawString("Set x:", 0, X_LINE);

        MazePoint result = new MazePoint();
        int currentValue = 1;
        int currentY = X_LINE;

        ColorSensor color = new ColorSensor(SensorPort.S3, SensorConstants.BLUE);

        while(true)
        {
            LCD.drawInt(currentValue, 3, 6, currentY);
            LCD.drawString(" (" + (currentValue * RobotConstants.ROBOT_LENGTH) + " cm)", 4, currentY+1);
            int more = Button.waitForAnyPress();

            if (more == Button.ID_ESCAPE){
                result = null;
                break;
            }
            if (more == Button.ID_ENTER){
                if (currentY == Y_LINE){
                    result.setY(currentValue * RobotConstants.ROBOT_LENGTH);
                    break;
                }

                currentY = Y_LINE;
                result.setX(currentValue * RobotConstants.ROBOT_LENGTH);
                currentValue = 1;
                LCD.drawString("Set y:", 0, currentY);
            }

            if (more == Button.ID_LEFT){
                currentValue--;
                if (currentValue == 0){
                    currentValue--;
                }
            }
            if (more == Button.ID_RIGHT){
                currentValue++;
                if (currentValue == 0){
                    currentValue++;
                }
            }

            color.setFloodlight(false);
        }

        LCD.clear();
        return result;
    }
}
