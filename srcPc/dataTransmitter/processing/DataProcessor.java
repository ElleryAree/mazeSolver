package dataTransmitter.processing;

import dataTransmitter.reader.exception.BadReadingsException;
import visualize.UpdatableView;

public interface DataProcessor {
    void processString(String readings) throws BadReadingsException;
    void updateView(UpdatableView view);
    boolean doContinue();
}
