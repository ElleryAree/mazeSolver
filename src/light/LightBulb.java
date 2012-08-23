package light;

import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.robotics.LightScanner;

public class LightBulb {
    private LightSensor sensor;

    public LightBulb() {
        sensor = new LightSensor(SensorPort.S4, true);
    }
}
