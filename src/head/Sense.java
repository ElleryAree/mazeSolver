package head;

import console.ConsoleLogger;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;
import lejos.nxt.UltrasonicSensor;

import java.util.Arrays;

public class Sense implements SensorReadings{
    private final UltrasonicSensor sonar;

    private int left;
    private int right;
    private int front;

    public Sense(){
        Motor.A.setSpeed(200);
        sonar = new UltrasonicSensor(SensorPort.S4);
    }

    private void turn(int degrees) {
        Motor.A.rotate(degrees * 2);
    }

    private int sense(){
        int[] distances = new int[8];

        sonar.ping();
        Sound.pause(30);
        sonar.getDistances(distances);

        return getMinimumDistance(distances);
    }

    public void senseIteration() {
        turn(90);
        right = sense();

        turn(-90);
        front = sense();

        turn(-90);
        left = sense();

        turn(90);
    }

    public int getLeft() {
        return left;
    }

    public int getRight() {
        return right;
    }

    public int getFront() {
        return front;
    }

    public static int getMinimumDistance(int[] distances){
        int min = 256; //in terms of Ultrasonic sensor, 255 is infinity

        for (int distance: distances){
            if (distance < min && distance > 0){
                min = distance;
            }
        }

        return min;
    }
}
