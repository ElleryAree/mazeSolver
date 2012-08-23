package console;

import lejos.nxt.comm.RConsole;

/**
 * Class for logging debug info
 *
 * User: elleryaree
 * Date: 8/11/12
 */
public class ConsoleLogger {
    private static ConsoleLogger consoleLogger;

    private boolean inDebugMode;

    public static void initLogger(boolean inDebugMode){
        consoleLogger = new ConsoleLogger(inDebugMode);
    }

    public static ConsoleLogger getLogger(){
        if (consoleLogger == null){
            consoleLogger = new ConsoleLogger(false);
        }
        return consoleLogger;
    }

    private ConsoleLogger(boolean inDebugMode){
        this.inDebugMode = inDebugMode;
//        open();
    }

    private void open() {
//        if (!inDebugMode)
//        RConsole.open();
    }

    public void close(){
//        RConsole.close();
    }

    public void message(String message) {
//        if (inDebugMode){
//            System.out.println(message);
//        } else {
//            RConsole.println(message);
//        }
    }
}
