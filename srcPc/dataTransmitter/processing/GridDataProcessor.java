package dataTransmitter.processing;

import dataTransmitter.reader.exception.BadReadingsException;
import visualize.UpdatableView;

public class GridDataProcessor implements DataProcessor {
    private int[][] grid;

    @Override
    public void processString(String readings) throws BadReadingsException {
        String[] rows = readings.split("\\|");
        if (rows.length == 0){
            throw new BadReadingsException("Grid readings does not contain any rows: " + readings);
        }

        grid = new int[rows.length][];
        int i=0;

        for (String row: rows){
            String[] digits = row.split(",");
            if (digits.length == 0)
                continue;

            grid[i] = new int[digits.length];
            int j=0;
            for (String digit: digits){
                grid[i][j++] = Integer.parseInt(digit);
            }
            i++;
        }
    }

    @Override
    public void updateView(UpdatableView view) {
        view.updateWorld(grid);
    }

    @Override
    public boolean doContinue() {
        return true;
    }

    public static String getCode(){
        return "G";
    }

    public int[][] getGrid() {
        return grid;
    }
}
