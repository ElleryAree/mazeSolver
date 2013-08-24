package dataTransmitter;

import junit.framework.Assert;
import org.junit.Test;

public class GridToStringTest {
    @Test
    public void testSimpleGrid(){
        int[][] grid = new int[][]{{-1, -1, -1}, {-2, -2, -2}};

        String gridString = GridDataTransmitter.convertGrid(grid);

        Assert.assertEquals("-1,-1,-1|-2,-2,-2", gridString);
    }
}
