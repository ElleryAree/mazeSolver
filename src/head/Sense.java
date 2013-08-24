package head;

import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;
import lejos.nxt.UltrasonicSensor;

public class Sense implements SensorReadings{
    private final UltrasonicSensor sonar;

    private int left;
    private int right;
    private int front;

    public Sense(){
        Motor.A.setSpeed(400);
        sonar = new UltrasonicSensor(SensorPort.S4);
    }

    void turn(int degrees) {
        Motor.A.rotate(degrees * 2);
    }

    private int sense(){
        int[] distances = senceDistances();

        return getMinimumDistance(distances);
    }

    protected int[] senceDistances() {
        int[] distances = new int[8];

        sonar.ping();
        Sound.pause(30);
        sonar.getDistances(distances);
        return distances;
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
        int sum = 0;

        for (int distance: distances){
            sum += distance;
        }

        return sum/distances.length;
    }
}
