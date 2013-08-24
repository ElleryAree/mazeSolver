package console;


import display.DisplayableFeature;
import display.FeatureCallback;
import main.Feature;

public class LoggerProvider extends Feature implements FeatureCallback<Boolean>{
    private static LoggerProvider provider;

    private Logger logger;
    private boolean debug;


    public static LoggerProvider getProvider(){
        if (provider == null)
            throw new RuntimeException("Not initialized");

        return provider;
    }

    public static void initProvider(boolean debug) {
        provider = new LoggerProvider(debug);
    }

    private LoggerProvider(boolean debug) {
        super();
        this.debug = debug;
    }


    private void initiateLogger(boolean useLogger){
        if (useLogger){
            logger = new ConsoleLogger(debug);
        } else {
            logger = new IdleLogger();
        }
    }

    public static void sendMessage(String message){
        getProvider().logger.message(message);
    }

    public static void closeLogger(){
        getProvider().logger.close();
    }

    @Override
    protected DisplayableFeature getFeature() {
        DisplayableFeature<Boolean> feature = new DisplayableFeature<Boolean>();
        feature.setCaption("Use remote\nlogger?");
        feature.setCallback(this);

        return feature;
    }

    @Override
    public void callback(Boolean answer) {
        initiateLogger(answer);
    }
}
