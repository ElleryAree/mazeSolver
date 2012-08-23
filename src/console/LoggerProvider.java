package console;


public class LoggerProvider {
    private static Logger logger;

    public static void initiateLogger(boolean debug, boolean useLogger){
        if (useLogger){
            logger = new ConsoleLogger(debug);
        } else {
            logger = new IdleLogger();
        }
    }

    public static void sendMessage(String message){
        logger.message(message);
    }

    public static void closeLogger(){
        logger.close();
    }
}
