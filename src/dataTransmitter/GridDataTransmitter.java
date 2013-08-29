package dataTransmitter;

import localization.maze.DirectionalPoint;

import java.util.List;

public class GridDataTransmitter extends DataTransmitter {


    protected GridDataTransmitter() {
        super();
    }

    public static void convertAndSendData(int[][] grid){
        convertAndSendData("0\nG" + '\n' + convertGrid(grid));
    }

    protected static String convertGrid(int[][] grid) {
        String[] rows = new String[grid.length];

        for (int i=0; i<grid.length; i++){
            rows[i] = join(grid[i], ",");
        }

        return join(rows, "|");
    }

    public static void sendRouteMessage(List<DirectionalPoint> route) {
        String[] points = new String[route.size()];

        for (int i=0; i<route.size(); i++ ){
            DirectionalPoint point = route.get(i);
            points[i] = point.getX() + "," + point.getY() + "," + point.getDirection().getDegrees();
        }

        sendMessage("RT", join(points, "|"));
    }

    /**
     * Copied from Apache Commons. (as whole lib would not compile)
     */
    private static String join(Object[] array, String separator) {
        if (array == null) {
            return null;
        }
        if (separator == null) {
            separator = "";
        }

        int startIndex = 0;
        int endIndex = array.length;

        // endIndex - startIndex > 0:   Len = NofStrings *(len(firstString) + len(separator))
        //           (Assuming that all Strings are roughly equally long)
        int noOfItems = endIndex - startIndex;
        if (noOfItems <= 0) {
            return "";
        }

        StringBuilder buf = new StringBuilder(noOfItems * 16);

        for (int i = startIndex; i < endIndex; i++) {
            if (i > startIndex) {
                buf.append(separator);
            }
            if (array[i] != null) {
                buf.append(array[i]);
            }
        }
        return buf.toString();
    }

    private static String join(int[] array, String separator) {
        if (array == null) {
            return null;
        }
        if (separator == null) {
            separator = "";
        }

        int startIndex = 0;
        int endIndex = array.length;

        // endIndex - startIndex > 0:   Len = NofStrings *(len(firstString) + len(separator))
        //           (Assuming that all Strings are roughly equally long)
        int noOfItems = endIndex - startIndex;
        if (noOfItems <= 0) {
            return "";
        }

        StringBuilder buf = new StringBuilder(noOfItems * 16);

        for (int i = startIndex; i < endIndex; i++) {
            if (i > startIndex) {
                buf.append(separator);
            }

            buf.append(array[i]);
        }
        return buf.toString();
    }
}
