package clustering.reader;

import lejos.pc.comm.NXTCommException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

public abstract class AbstractReader {
    public void read() throws NXTCommException, IOException {
        InputStream stream = null;
        try{
            stream = getInputStream();

            String s = convertStreamToString(stream);
            printReadings(s);


            System.out.println("Readings: " + s);
        }   finally {
            if (stream != null){
                stream.close();
            }
        }
    }

    protected abstract InputStream getInputStream();


    protected void printReadings(String s) {
        try {
            List<List<Integer>> lists = getArraysForData();

            String[] arrays = s.split("]");
            for (int i=0; i<3; i++){
                convertStringToArray(arrays[i], lists.get(i));
            }
        } catch (java.util.NoSuchElementException e) {
            e.printStackTrace();
        }
    }

    protected abstract List<List<Integer>> getArraysForData();

    private void convertStringToArray(String s, List<Integer> front) {
        String[] preSb = s.split("\\[");
        String[] sb = preSb[1].split(", ");
        for (String d: sb){
            Integer i = Integer.valueOf(d);
            if (i != 0){
                front.add(i);
            }
        }
    }

    private String convertStreamToString(java.io.InputStream is) {
        try {
            Scanner scanner = new Scanner(is);
            return scanner.useDelimiter("\\A").next();
        } catch (java.util.NoSuchElementException e) {
            return "";
        }
    }
}
