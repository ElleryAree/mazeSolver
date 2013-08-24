package display;

public class DisplayableFeature<T> {
    private String caption;
    private FeatureCallback<T> callback;

    public void callback(T answer) {
        callback.callback(answer);
    }

    public void setCallback(FeatureCallback<T> callback) {
        this.callback = callback;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }
}
