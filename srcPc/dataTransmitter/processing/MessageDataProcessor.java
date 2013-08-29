package dataTransmitter.processing;

import dataTransmitter.reader.exception.BadReadingsException;
import visualize.UpdatableView;

public class MessageDataProcessor extends ContinuingDataProcessor{
    private String message;

    @Override
    public void processString(String readings) throws BadReadingsException {
        message = readings;
    }

    @Override
    public void updateView(UpdatableView view) {
        System.out.println(message);
        view.showMessage(message);
    }

    public static String getCode(){
        return "M";
    }
}
