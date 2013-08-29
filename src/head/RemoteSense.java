package head;

public class RemoteSense extends Sense{
    private int[] left;
    private int[] right;
    private int[] front;

    @Override
    public void senseIteration() {
        turn(90);
        right = senseDistances();

        turn(-90);
        front = senseDistances();

        turn(-90);
        left = senseDistances();

        turn(90);
    }

    public int[] getLeftArray() {
        return left;
    }

    public int[] getRightArray() {
        return right;
    }

    public int[] getFrontArray() {
        return front;
    }
}
