package head;

public class OnlyFirstSense extends Sense {
    @Override
    public void senseIteration() {
        front = senseOnce();
    }
}
