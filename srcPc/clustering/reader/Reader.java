package clustering.reader;

import lejos.pc.comm.*;

import java.io.IOException;
import java.io.InputStream;

public class Reader extends LeftRightFrontReader{
    private NXTComm nxtComm;

    public Reader() throws NXTCommException {
        NXTComm nxtComm = openConnection();
    }

    protected InputStream getInputStream() {
        InputStream stream;
        stream = nxtComm.getInputStream();
        return stream;
    }

    private NXTComm openConnection() throws NXTCommException {
        int protocols = NXTCommFactory.USB | NXTCommFactory.BLUETOOTH;

        NXTComm nxtComm = NXTCommFactory.createNXTComm(NXTCommFactory.USB);

        NXTConnector connector = new NXTConnector();

        NXTInfo[] nxts = connector.search(null, null, protocols);

        System.out.println(nxts[0]);

        nxtComm.open(nxts[0]);
        return nxtComm;
    }

    public void close() throws IOException {
        nxtComm.close();
    }

}
