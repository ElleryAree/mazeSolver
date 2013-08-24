package dataTransmitter.reader;

import dataTransmitter.processing.FinishedProcessor;
import dataTransmitter.processing.GridDataProcessor;
import dataTransmitter.processing.GridDataProcessorTest;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MockReader extends AbstractReader{

    private List<Integer> output = new ArrayList<Integer>();

    private List<ReadingsPreset> readingsPresets;

    public MockReader(List<ReadingsPreset> readingsPresets) {
        this.readingsPresets = readingsPresets;
    }

    @Override
    protected InputStream getInputStream() {
        final Iterator<Byte> byteIterator;

        if (readingsPresets.size() > 0){
            ReadingsPreset readingsPreset = readingsPresets.get(0);
            readingsPresets.remove(0);
            byteIterator = getGridByteIterator(readingsPreset);
        } else {
            byteIterator = getByteIteratorFromString(getString(FinishedProcessor.getCode(), "Finished successfully"));
        }

        return new InputStream() {
            @Override
            public int read() throws IOException {
                return byteIterator.hasNext() ? byteIterator.next() : -1;
            }


        };
    }

    private Iterator<Byte> getGridByteIterator(ReadingsPreset readingsPreset) {
        String outputString = getString(GridDataProcessor.getCode(), GridDataProcessorTest.gridToString(readingsPreset.grid));
        return getByteIteratorFromString(outputString);
    }

    private String getString(String code, String data) {
        return code + AbstractReader.CODE_DELIMITER + data;
    }

    private Iterator<Byte> getByteIteratorFromString(String outputString) {
        List<Byte> bytes = new ArrayList<Byte>();
        for (byte b: outputString.getBytes()){
            bytes.add(b);
        }
        return bytes.iterator();
    }

    @Override
    public boolean canRead() {
        if (readingsPresets.isEmpty()){
            return true;
        }

        ReadingsPreset readingsPreset = readingsPresets.get(0);
        if (readingsPreset.isReady){
            return true;
        }

        readingsPresets.remove(0);
        return false;
    }

    public List<Integer> getOutput() {
        return output;
    }
}
