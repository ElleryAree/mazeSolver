package dataTransmitter.processing;

public abstract class ContinuingDataProcessor implements DataProcessor{
    @Override
    public boolean doContinue() {
        return true;
    }
}
