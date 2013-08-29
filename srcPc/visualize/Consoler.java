package visualize;

import dataTransmitter.processing.DataProcessor;
import dataTransmitter.processing.ProcessorFactory;
import dataTransmitter.reader.AbstractReader;
import dataTransmitter.reader.NXTReader;
import dataTransmitter.reader.Readings;
import dataTransmitter.reader.exception.BadReadingsException;
import dataTransmitter.reader.exception.NoBrickException;
import lejos.pc.comm.NXTCommException;

import java.io.IOException;

public class Consoler {

    public static void main(String[] args) throws NoBrickException, NXTCommException, IOException, InterruptedException {
        new Consoler().start(new NXTReader());
    }


    protected void start(AbstractReader reader) throws InterruptedException, IOException {
//        UpdatableView visualizer = new ConsoleDisplay();

//        mainLoop(reader, visualizer);
    }

    private void mainLoop(AbstractReader reader, UpdatableView visualizer) throws InterruptedException, IOException {
        while(true){
            Thread.sleep(700);

            if (!reader.canRead()) {
                for (int i=3; i>0; i--){
                    Thread.sleep(1000);
                }
                continue;
            }

            DataProcessor processor;
            try {
                Readings readings = reader.read();

                processor = ProcessorFactory.getProcessorByCode(readings.code);
                processor.processString(readings.data);
            } catch (BadReadingsException e) {
                e.printStackTrace();
                continue;
            } catch (Throwable t) {
                t.printStackTrace();
                break;
            }

            processor.updateView(visualizer);
        }
    }
}
