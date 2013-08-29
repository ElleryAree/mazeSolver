package dataTransmitter.reader;

import dataTransmitter.reader.exception.BadReadingsException;
import dataTransmitter.reader.exception.NoBrickException;
import lejos.pc.comm.*;

import java.io.IOException;
import java.io.InputStream;

public class NXTReader extends AbstractReader {
    private NXTComm nxtComm;

    protected InputStream getInputStream() {
        InputStream stream;
        nxtComm = null;
        try {
            nxtComm = openConnection();
        } catch (Throwable t) {
            t.printStackTrace();
        }
        if (nxtComm == null){
            return null;
        }
        stream = nxtComm.getInputStream();
        return stream;
    }

    @Override
    public Readings read() throws IOException, BadReadingsException {
        try {
            return super.read();
        } finally {
            closeNXTConnection();
        }
    }

    @Override
    public boolean canRead() {
        NXTConnector connector = new NXTConnector();
        NXTInfo[] nxts = connector.search(null, null, NXTCommFactory.USB);

        return nxts.length > 0;
    }

    private NXTComm openConnection() throws NXTCommException, NoBrickException {
        NXTComm nxtComm = NXTCommFactory.createNXTComm(NXTCommFactory.USB);

        NXTConnector connector = new NXTConnector();
        NXTInfo[] nxts = connector.search(null, null, NXTCommFactory.USB);
        if (nxts.length == 0){
            throw new NoBrickException();
        }

        nxtComm.open(nxts[0]);
        return nxtComm;
    }

    public void closeNXTConnection() throws IOException {
        if (nxtComm != null)
            nxtComm.close();
    }

}
