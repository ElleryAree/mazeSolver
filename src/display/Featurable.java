package display;

public interface Featurable {
   <T> void registerFeature(DisplayableFeature<T> feature);
}
