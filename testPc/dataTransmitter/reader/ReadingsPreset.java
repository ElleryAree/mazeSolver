package dataTransmitter.reader;

public class ReadingsPreset {
    protected Integer[][] grid;
    protected boolean isReady;

    public ReadingsPreset(){
    }

    public ReadingsPreset(Integer[][] grid){
        this.grid = grid;
        this.isReady = true;
    }
}
