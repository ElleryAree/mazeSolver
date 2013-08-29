package head;

import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;
import lejos.nxt.UltrasonicSensor;

public class Sense implements SensorReadings{
    private final UltrasonicSensor sonar;

    protected int left;
    protected int right;
    protected int front;

    public Sense(){
        Motor.A.setSpeed(400);
        sonar = new UltrasonicSensor(SensorPort.S4);
    }

    void turn(int degrees) {
        Motor.A.rotate(degrees * 2);
    }

    protected int sense(){
        int[] distances = senseDistances();

        return getMinimumDistance(distances);
    }

    protected int senseOnce(){
        return sonar.getDistance();
    }

    protected int[] senseDistances() {
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
        int count = 0;

        for (int distance: distances){
            sum += distance;
            if (distance > 0) count++;
        }

        return count != 0 ? sum/count : 0;
    }
}
