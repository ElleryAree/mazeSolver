package clustering.reader;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class MockReader extends AbstractReader{

    private Iterator<Byte> byteIterator;
    private List<Integer> output = new ArrayList<Integer>();

    public MockReader(List<Integer> readings) {
        final List<Byte> bytes = new ArrayList<Byte>();
        String outputString = "\u0000\u001A" + Arrays.toString(readings.toArray());
        for (byte b: outputString.getBytes()){
            bytes.add(b);
        }
        byteIterator = bytes.iterator();
    }

    @Override
    protected InputStream getInputStream() {
        InputStream stream = new InputStream() {
            @Override
            public int read() throws IOException {
                return byteIterator.next();
            }
        };

        return stream;
    }

    @Override
    protected List<List<Integer>> getArraysForData() {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        result.add(output);
        return result;
    }

    public List<Integer> getOutput() {
        return output;
    }
}
