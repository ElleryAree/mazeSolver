package dataTransmitter.processing;

/**
 * Factory class that will return data processor by recieved code
 */
public class ProcessorFactory {
    public static DataProcessor getProcessorByCode(String code){
        if (GridDataProcessor.getCode().equals(code)){
            return new GridDataProcessor();
        } else if (FinishedProcessor.getCode().equals(code)){
            return new FinishedProcessor();
        } else if (RobotPositionProcessor.getCode().equals(code)){
            return new RobotPositionProcessor();
        } else if (MessageDataProcessor.getCode().equals(code)){
            return new MessageDataProcessor();
        } else if (RouteProcessor.getCode().equals(code)){
            return new RouteProcessor();
        }

        throw new IllegalArgumentException("No processor for code " + code);
    }
}
