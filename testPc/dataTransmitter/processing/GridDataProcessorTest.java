package dataTransmitter.processing;

import dataTransmitter.reader.exception.BadReadingsException;
import junit.framework.Assert;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

public class GridDataProcessorTest {
    @Test
    public void baseTest(){
        Integer[][] grid = new Integer[][]{{1, 2, 3}, {2, 3, 1}, {3, 2, 1}};
        String stringToParse = gridToString(grid);

        GridDataProcessor processor = new GridDataProcessor();
        try {
            processor.processString(stringToParse);
        } catch (BadReadingsException e) {
            Assert.fail(e.getMessage());
        }

        int[][] parsedGrid = processor.getGrid();
        Assert.assertEquals("Number of rows", grid.length, parsedGrid.length);

        for (int i=0; i<parsedGrid.length; i++){
            Assert.assertEquals("Number of columns in a row #" + i, grid[i].length, parsedGrid[i].length);
            for (int j=0; j<parsedGrid[i].length; j++){
                Assert.assertEquals("grid[" + i + "][" + j + "]", (int)grid[i][j], parsedGrid[i][j]);
            }
        }
    }

    public static String gridToString(Integer[][] grid){
        String[] rows = new String[grid.length];

        for (int i=0; i<grid.length; i++){
            rows[i] = StringUtils.join(grid[i], ",");
        }

        return StringUtils.join(rows, "|");
    }
}
