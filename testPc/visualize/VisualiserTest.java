package visualize;

import dataTransmitter.reader.AbstractReader;
import dataTransmitter.reader.MockReader;
import dataTransmitter.reader.ReadingsPreset;
import junit.framework.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class VisualiserTest {

    @Test
    public void simpleTest(){

        List<ReadingsPreset> presets = new ArrayList<ReadingsPreset>(4);

        presets.add(new ReadingsPreset(new Integer[][]{{-2, -2}, {-2, 0}}));
        presets.add(new ReadingsPreset());
        presets.add(new ReadingsPreset(new Integer[][]{{-2, -2, -2}, {-2, -1, -1}, {-2, 0, -1}}));
        presets.add(new ReadingsPreset(new Integer[][]{{-2, -2, -2, -2}, {-2, -2, -1, -1}, {-2, -2, -1, -1}, {-2, -2, 0, -1}}));

        AbstractReader reader = new MockReader(presets);

        try {
            new Visualiser().start(reader);
        } catch (Throwable e) {
            Assert.fail(e.getMessage());
        }
    }
}
