package main;

import display.DisplayableFeature;
import display.EnterInformation;

public abstract class Feature {
    public Feature(){
        EnterInformation.registerFeature(getFeature());
    }

    protected abstract DisplayableFeature getFeature();
}
