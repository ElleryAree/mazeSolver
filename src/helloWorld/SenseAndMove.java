package helloWorld;

import head.Sense;
import lejos.nxt.*;
import lejos.robotics.navigation.DifferentialPilot;

import java.util.Arrays;

public class SenseAndMove {
    public static void run(){
        UltrasonicSensor sonar = new UltrasonicSensor(SensorPort.S4);
        DifferentialPilot pilot = new DifferentialPilot(3, 10, Motor.B, Motor.C);

        int distanceToTheClosestObject = 256;
        int[] distances = new int[8];
        int limit = 5;

        while (distanceToTheClosestObject == 256){
            sonar.ping();
            Sound.pause(30);
            sonar.getDistances(distances);

            distanceToTheClosestObject = Sense.getMinimumDistance(distances);
            if (distanceToTheClosestObject == 256){
                pilot.travel(5);
            }

            if (limit-- <= 0){
                System.out.print("Failed");
                break;
            }
        }

        pilot.travel(distanceToTheClosestObject/2);
    }
}
