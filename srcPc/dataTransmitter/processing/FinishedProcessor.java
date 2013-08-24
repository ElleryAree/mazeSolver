package dataTransmitter.processing;

import dataTransmitter.reader.exception.BadReadingsException;
import visualize.UpdatableView;

public class FinishedProcessor implements DataProcessor{
    private String finishMessage;

    @Override
    public void processString(String readings) throws BadReadingsException {
        finishMessage = readings;
    }

    @Override
    public void updateView(UpdatableView view) {
        System.out.println("Robot finished with message: \"" + finishMessage + "\"");
        view.showMessage("Robot finished with message: \"" + finishMessage + "\"");
    }

    @Override
    public boolean doContinue() {
        return false;
    }

    public static String getCode(){
        return "F";
    }
}
