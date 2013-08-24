package display;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import localization.maze.MazePoint;
import main.RobotConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 8/19/12
 */
@SuppressWarnings("ALL")
public class EnterInformation{
    private static final int X_LINE = 3;
    private static final int Y_LINE = 5;

    private static List<DisplayableFeature> features = new ArrayList<DisplayableFeature>();

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
        int currentValue = 0;
        int currentY = X_LINE;

        while (true) {
            LCD.drawInt(currentValue, 3, 6, currentY);
            LCD.drawString(" (" + (currentValue * RobotConstants.ROBOT_LENGTH) + " cm)", 4, currentY + 1);
            int more = Button.waitForAnyPress();

            if (more == Button.ID_ESCAPE) {
                if (currentY == Y_LINE) {
                    LCD.clear(currentY);
                    LCD.clear(currentY + 1);
                    result.setY(currentValue);
                    currentValue = (int) (result.getX());
                    currentY = X_LINE;
                } else {
                    result = null;
                    break;
                }
            }
            if (more == Button.ID_ENTER) {
                if (currentY == Y_LINE) {
                    result.setY(currentValue);
                    break;
                }

                currentY = Y_LINE;
                result.setX(currentValue);
                currentValue = (int)(result.getY()) == 0 ? 0 : (int) (result.getY());
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

    public static <T> void registerFeature(DisplayableFeature<T> feature){
        features.add(feature);
    }

    private static void enterFeature(DisplayableFeature feature) {
        LCD.clear();
        LCD.drawString(feature.getCaption(), 0, 0);
        boolean answer = false;

        while (true) {
            LCD.drawString(answer ? "Yes" : " No", 3, 2);
            int more = Button.waitForAnyPress();

            if (more == Button.ID_ENTER || more == Button.ID_ESCAPE) {
                LCD.clear();
                feature.callback(answer);
                return;
            }

            if (more == Button.ID_LEFT || more == Button.ID_RIGHT) {
                answer = !answer;
            }
        }
    }

    public static void setupFeatures() {
        for (DisplayableFeature feature: features){
            enterFeature(feature);
        }
    }
}
