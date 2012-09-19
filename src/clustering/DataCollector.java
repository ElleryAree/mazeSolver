package clustering;

import head.RemoteSense;
import lejos.nxt.LCD;
import lejos.nxt.comm.NXTConnection;
import lejos.nxt.comm.USB;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class DataCollector {
    private RemoteSense readings;

    public DataCollector() {
        readings = new RemoteSense();
    }

    public void collectAndPrecess(){
        LCD.drawString("waiting for USB", 0,1 );
        NXTConnection connection = USB.waitForConnection();

        readings.senseIteration();

        DataOutputStream dataOut = connection.openDataOutputStream();
        try {
            String front = Arrays.toString(readings.getFrontArray());
            dataOut.writeUTF(front);

            String right = Arrays.toString(readings.getRightArray());
            dataOut.writeUTF(right);

            String left = Arrays.toString(readings.getLeftArray());
            dataOut.writeUTF(left);

            dataOut.flush();
        } catch (IOException e ) {
            System.out.println(" write error "+e);
        }
    }
}
