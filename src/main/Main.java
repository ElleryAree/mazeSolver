package main;

import console.ConsoleLogger;
import display.EnterInformation;
import head.Sense;
import lejos.nxt.*;
import lejos.robotics.objectdetection.*;
import maze.MazePoint;
import movement.*;

/**
 * Entry point.
 * <p/>
 * User: elleryaree
 * Date: 8/11/12
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        assertHit();
        maseSolve(args);
//        testMovement();
//        loggerTest();
    }

    private static void testMovement(){
        MovementTest.test();
    }

    private static void assertHit(){
        FeatureDetector fd1 = new TouchFeatureDetector(new TouchSensor(SensorPort.S2));
        FeatureDetector fd2 = new TouchFeatureDetector(new TouchSensor(SensorPort.S1));

        FeatureListener listener = new FeatureListener() {
            @Override
            public void featureDetected(Feature feature, FeatureDetector detector) {
                throw new RuntimeException("Hit something");
            }
        };
        fd1.addListener(listener);
        fd2.addListener(listener);
    }

    private static void loggerTest(){
        ConsoleLogger.initLogger(false);

        Sense sense = new Sense();
        sense.senseIteration();

        ConsoleLogger.getLogger().message("Left: " + sense.getLeft());
        ConsoleLogger.getLogger().message("Right: " + sense.getRight());
        ConsoleLogger.getLogger().message("Front: " + sense.getFront());

        ConsoleLogger.getLogger().close();
        System.out.println("Press any key again");
        Button.waitForAnyPress();
    }

    private static void maseSolve(String[] args) {
        MazePoint goal;
        boolean debugMode = args != null && args.length > 0;
        ConsoleLogger.initLogger(debugMode);

        if (debugMode) {
            goal = new MazePoint(Double.valueOf(args[0]) * RobotConstants.ROBOT_LENGTH,
                                 Double.valueOf(args[1]) * RobotConstants.ROBOT_LENGTH);
        } else {
            goal = EnterInformation.enter();
            if (goal == null) {
                System.out.println("Canceled");
                Button.waitForAnyPress();
                return;
            }
        }

        ConsoleLogger.getLogger().message("I will go to:\n" + goal);
        LCD.drawString("I will go to:\n" + goal, 0, 0);
        if (!debugMode) {
            Button.waitForAnyPress();
        }
        MovementInMaze movementInMaze;
        if (debugMode) {
            int[][] expectedGrid = new int[5][];
            expectedGrid[4] = new int[]{-2};
            expectedGrid[3] = new int[]{-2};
            expectedGrid[2] = new int[]{-2};
            expectedGrid[1] = new int[]{-2};
            expectedGrid[0] = new int[]{-2};

            movementInMaze = new FakeRunner(expectedGrid);
        } else {
            movementInMaze = new MovementInMaze();
        }
        GridWorldRunner runner = new GridWorldRunner(goal, movementInMaze);
        runner.run();

        if (!debugMode) {
            LCD.drawString("Complited", 0, 4);
            Button.waitForAnyPress();
        }
    }
}
