package main;

import clustering.DataCollector;
import console.LoggerProvider;
import display.EnterInformation;
import head.Sense;
import lejos.nxt.*;
import lejos.robotics.objectdetection.*;
import localization.maze.MazePoint;
import movement.*;

/**
 * Entry point.
 * <p/>
 * User: elleryaree
 * Date: 8/11/12
 */
public class Main {
    private static boolean debugMode;

    public static void main(String[] args) throws InterruptedException {
        debugMode = args != null && args.length > 0;

        assertHit();
        maseSolve(args);
//        testMovement();
//        loggerTest();

//        sensorTest();
    }

    private static void testMovement(){
        MovementTest.test();
    }

    private static void assertHit(){
        if (debugMode){
            return;
        }

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
        LoggerProvider.initiateLogger(false, false);

        Sense sense = new Sense();
        sense.senseIteration();

        LoggerProvider.sendMessage("Left: " + sense.getLeft());
        LoggerProvider.sendMessage("Right: " + sense.getRight());
        LoggerProvider.sendMessage("Front: " + sense.getFront());

        LoggerProvider.closeLogger();
        System.out.println("Press any key again");
        Button.waitForAnyPress();
    }

    private static void maseSolve(String[] args) {
        boolean useLogger = true;
        if (!debugMode){
        EnterInformation.showGreetings();
        useLogger = EnterInformation.enterLoggerSetting();
        }

        MazePoint goal;

        LoggerProvider.initiateLogger(debugMode, useLogger);

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

        LoggerProvider.sendMessage("I will go to:\n" + goal);
        System.out.print("I will go to:\n" + goal);
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

    /**
     * Entry point for testing usage of clustering,
     * based on many sonic sensor's readings
     */
    private static void sensorTest(){
        DataCollector collector = new DataCollector();
        collector.collectAndPrecess();
    }
}
