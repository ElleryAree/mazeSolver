package clustering.reader;

import lejos.pc.comm.NXTCommException;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClusterTest {

    @Test
    public void testPrint() throws NXTCommException {
        /*String toString = "\u0000\u001A[42, 48, 0, 0, 0, 0, 0, 0]\u0000\u0018[0, 0, 0, 0, 0, 0, 0, 0]\u0000\u0019[66, 0, 0, 0, 0, 0, 0, 0]";

        AbstractReader reader = new MockReader(null);
        reader.printReadings(toString);
        Assert.assertEquals("Front", Arrays.asList(42, 48), reader.getFront());
        Assert.assertEquals("Right", new ArrayList<Integer>(), reader.getRight());
        Assert.assertEquals("Left", Arrays.asList(66), reader.getLeft());*/
    }


    @Test
    public void testStreamRead() throws NXTCommException, IOException {
        MockReader reader = new MockReader(Arrays.asList(1, 2, 3));
        reader.read();
        Assert.assertEquals("Front", Arrays.asList(1, 2, 3), reader.getOutput());
    }

    @Test
    public void testIterator(){
        final List<Byte> bytes = new ArrayList<Byte>();
        for (byte b: Arrays.toString(Arrays.asList(1, 2, 3).toArray()).getBytes()){
            bytes.add(b);
        }
//        while (iterable.hasNext()){
            System.out.println(bytes.iterator().next());
            System.out.println(bytes.iterator().next());
            System.out.println(bytes.iterator().next());
            System.out.println(bytes.iterator().next());
//        }

    }
}
