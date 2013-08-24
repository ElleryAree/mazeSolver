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

public class Visualiser implements Pausable{
    private boolean continueRun = true;
    private SwingVisualization visualizer;
    private AbstractReader reader;

    public static void main(String[] args) throws NoBrickException, NXTCommException, IOException, InterruptedException {
       new Visualiser().start(new NXTReader());
    }

    @Override
    public void pause() {
        continueRun = false;
        try {
            this.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    @Override
    public void run() {
        /*
        try {
            mainLoop(reader, visualizer);
        } catch (Throwable e) {
            e.printStackTrace();
        }*/
        continueRun = true;
        this.notify();
    }

    @Override
    public boolean getContinueRun() {
        return continueRun;
    }

    protected void start(AbstractReader reader) throws InterruptedException, IOException {
        SwingVisualization visualizer = SwingVisualization.startVisualizer(this);

        this.visualizer = visualizer;
        this.reader = reader;

        mainLoop(reader, visualizer);
    }

    private void mainLoop(AbstractReader reader, SwingVisualization visualizer) throws InterruptedException, IOException {
        while(true){
            visualizer.showServiceMessage("Waiting update");
            Thread.sleep(700);

            if (!reader.canRead()) {
                visualizer.showServiceMessage("Brick not ready. Continue waiting");
                for (int i=3; i>0; i--){
                    Thread.sleep(1000);
                    visualizer.showServiceMessage("Waiting update: " + i);
                }
                continue;
            }

            visualizer.showServiceMessage("Update received");
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
            if (!processor.doContinue()){
                break;
            }
        }
    }
}
