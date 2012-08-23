package movement;

import lejos.nxt.Motor;
import lejos.robotics.navigation.DifferentialPilot;
import main.RobotConstants;

import java.awt.*;

/**
 * Class for basic movements: go forward, turn.
 *
 * User: elleryaree
 * Date: 8/11/12
 */
public class BasicMovement {
    private DifferentialPilot pilot;
    public BasicMovement() {
        this(false);
    }

    public BasicMovement(boolean debug) {
        if (!debug){
            pilot = new DifferentialPilot(RobotConstants.WHEEL_DIAMETER, RobotConstants.TRACK_WIDTH, Motor.B, Motor.C);
        }
    }

    protected void forward(int distance){
        pilot.travel(distance);
    }

    protected void turn(int angle){
        pilot.rotate(angle);
    }

    /**
     * 90˚ turn
     */
    public void simpleTurn(){
        turn(90);
    }
}
