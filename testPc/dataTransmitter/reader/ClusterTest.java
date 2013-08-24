package dataTransmitter.reader;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClusterTest {

    @Test
    public void testIterator(){
        final List<Byte> bytes = new ArrayList<Byte>();
        for (byte b: Arrays.toString(Arrays.asList(1, 2, 3).toArray()).getBytes()){
            bytes.add(b);
        }

        System.out.println(bytes.iterator().next());
        System.out.println(bytes.iterator().next());
        System.out.println(bytes.iterator().next());
        System.out.println(bytes.iterator().next());
    }
}
