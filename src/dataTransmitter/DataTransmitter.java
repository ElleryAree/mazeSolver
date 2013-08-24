package dataTransmitter;

import display.DisplayableFeature;
import display.FeatureCallback;
import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.comm.NXTConnection;
import lejos.nxt.comm.USB;
import main.Feature;

import java.io.DataOutputStream;
import java.io.IOException;

public class DataTransmitter extends Feature implements FeatureCallback<Boolean> {
    private static DataTransmitter transmitter;

    private boolean useTransmitter;

    public static void initTransmitter(){
        transmitter = new DataTransmitter();
    }

    public static void initTransmitter(boolean useTransmitter){
        transmitter = new DataTransmitter();
        transmitter.useTransmitter = useTransmitter;
    }

    protected DataTransmitter() {
        super();
    }

    public static void convertAndSendData(String rawData){
        try {
            transmitter.sendData(rawData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendData(String data) throws IOException {
        if (!useTransmitter)
            return;
        LCD.clearDisplay();

        LCD.drawString("waiting for USB", 1, 0);
        NXTConnection connection = USB.waitForConnection();


        if (connection == null){
            Button.waitForAnyPress();
            return;
        }

        DataOutputStream dataOut = connection.openDataOutputStream();
        try {
            dataOut.writeUTF(data);
            dataOut.flush();
        } catch (IOException e ) {
            System.out.println(" write error "+e);
        } finally {
            dataOut.close();
            connection.close();
        }
    }

    @Override
    protected DisplayableFeature getFeature() {
        DisplayableFeature<Boolean> feature = new DisplayableFeature<Boolean>();
        feature.setCaption("Use data\ntransmitter?");
        feature.setCallback(this);

        return feature;
    }

    @Override
    public void callback(Boolean answer) {
        useTransmitter = answer;
    }
}
