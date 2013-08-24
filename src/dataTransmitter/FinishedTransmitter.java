package dataTransmitter;

public class FinishedTransmitter extends DataTransmitter{
    public static void sendFinished(String message){
        convertAndSendData("0\nF\n" + message);
    }
}
