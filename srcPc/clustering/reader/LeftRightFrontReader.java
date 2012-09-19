package clustering.reader;

import java.util.ArrayList;
import java.util.List;

public abstract class LeftRightFrontReader extends AbstractReader{
    private List<Integer> front = new ArrayList<Integer>();
    private List<Integer> right = new ArrayList<Integer>();
    private List<Integer> left = new ArrayList<Integer>();

    protected List<List<Integer>> getArraysForData() {
        List<List<Integer>> lists = new ArrayList<List<Integer>>();
        lists.add(front);
        lists.add(right);
        lists.add(left);
        return lists;
    }

    public List<Integer> getFront() {
        return front;
    }

    public List<Integer> getRight() {
        return right;
    }

    public List<Integer> getLeft() {
        return left;
    }
}
