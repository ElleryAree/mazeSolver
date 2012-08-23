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

    public static void showGreetings() {
        LCD.drawString("  *************", 0, 0);
        LCD.drawString("  *Maze solver*", 0, 1);
        LCD.drawString("  *************", 0, 2);
        LCD.drawString("Press any key:", 0, 7);
        Button.waitForAnyPress();
    }

    public static MazePoint enter() {
        LCD.clear();
        LCD.drawString("Enter goal: (in", 0, 0);
        LCD.drawString("robot's lengths)", 0, 1);
        LCD.drawString("Set x:", 0, X_LINE);

        MazePoint result = new MazePoint();
        int currentValue = 1;
        int currentY = X_LINE;

        while (true) {
            LCD.drawInt(currentValue, 3, 6, currentY);
            LCD.drawString(" (" + (currentValue * RobotConstants.ROBOT_LENGTH) + " cm)", 4, currentY + 1);
            int more = Button.waitForAnyPress();

            if (more == Button.ID_ESCAPE) {
                if (currentY == Y_LINE) {
                    LCD.clear(currentY);
                    LCD.clear(currentY + 1);
                    result.setY(currentValue * RobotConstants.ROBOT_LENGTH);
                    currentValue = (int) (result.getX() / RobotConstants.ROBOT_LENGTH);
                    currentY = X_LINE;
                } else {
                    result = null;
                    break;
                }
            }
            if (more == Button.ID_ENTER) {
                if (currentY == Y_LINE) {
                    result.setY(currentValue * RobotConstants.ROBOT_LENGTH);
                    break;
                }

                currentY = Y_LINE;
                result.setX(currentValue * RobotConstants.ROBOT_LENGTH);
                currentValue = (int)(result.getY() / RobotConstants.ROBOT_LENGTH) == 0 ? 1 : (int) (result.getY() / RobotConstants.ROBOT_LENGTH);
                LCD.drawString("Set y:", 0, currentY);
            }

            if (more == Button.ID_LEFT) {
                currentValue--;
                if (currentValue == 0) {
                    currentValue--;
                }
            }
            if (more == Button.ID_RIGHT) {
                currentValue++;
                if (currentValue == 0) {
                    currentValue++;
                }
            }
        }

        LCD.clear();
        return result;
    }

    public static boolean enterLoggerSetting() {
        LCD.clear();
        LCD.drawString("Use remote\nlogger?", 0, 0);
        boolean useLogger = false;

        while (true) {
            LCD.drawString(useLogger ? "Yes" : " No", 3, 2);
            int more = Button.waitForAnyPress();

            if (more == Button.ID_ENTER || more == Button.ID_ESCAPE) {
                LCD.clear();
                return useLogger;
            }

            if (more == Button.ID_LEFT || more == Button.ID_RIGHT) {
                useLogger = !useLogger;
            }
        }
    }
}
