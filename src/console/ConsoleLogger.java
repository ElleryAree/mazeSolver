package console;

import lejos.nxt.comm.RConsole;

/**
 * Class for logging debug info
 *
 * User: elleryaree
 * Date: 8/11/12
 */
public class ConsoleLogger implements Logger{
    private boolean inDebugMode;

    protected ConsoleLogger(boolean inDebugMode){
        this.inDebugMode = inDebugMode;
        open();
    }

    public void open() {
        if (!inDebugMode)
        RConsole.open();
    }

    public void close(){
        RConsole.close();
    }

    public void message(String message) {
        if (inDebugMode){
            System.out.println(message);
        } else {
            RConsole.println(message);
        }
    }
}
