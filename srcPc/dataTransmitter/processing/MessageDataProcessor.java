package dataTransmitter.processing;

import dataTransmitter.reader.exception.BadReadingsException;
import visualize.UpdatableView;

public class MessageDataProcessor implements DataProcessor {
    private String message;

    @Override
    public void processString(String readings) throws BadReadingsException {
        message = readings;
    }

    @Override
    public void updateView(UpdatableView view) {
        view.showMessage(message);
    }

    @Override
    public boolean doContinue() {
        return true;
    }

    public static String getCode(){
        return "M";
    }
}
