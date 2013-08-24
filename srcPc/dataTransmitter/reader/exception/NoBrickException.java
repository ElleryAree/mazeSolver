package dataTransmitter.reader.exception;


public class NoBrickException extends Exception{
    public NoBrickException(){
        super("Brick was not found");
    }
}
