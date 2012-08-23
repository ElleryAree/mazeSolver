package console;

public interface Logger {
    public void open();
    public void message(String message);
    public void close();
}
