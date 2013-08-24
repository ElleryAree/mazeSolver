package dataTransmitter.reader;

import dataTransmitter.reader.exception.BadReadingsException;
import junit.framework.Assert;
import org.junit.Test;

public class AbstractReaderTest {
    @Test
    public void testParser(){
        assertReadings("AB", "some data");
    }

    @Test
    public void testParserLongCode(){
        assertReadings("A VERY LONG LONG CODE", "some data");
    }

    @Test
    public void testParserShortCode(){
        assertReadings("a", "some data");
    }

    @Test
    public void testParserNoMessage(){
        assertReadings("NM", "");
    }

    @Test (expected = BadReadingsException.class)
    public void testParserNoCode() throws BadReadingsException {
        String message = "A message";

        Readings readings = AbstractReader.parseData(AbstractReader.CODE_DELIMITER + message);
        Assert.assertEquals(message, readings.data);
    }

    @Test (expected = BadReadingsException.class)
    public void testBadString() throws BadReadingsException {
        String message = "A message";

        Readings readings = AbstractReader.parseData(message);
        Assert.assertEquals(message, readings.data);
    }

    private void assertReadings(String code, String data) {
        Readings readings = null;
        try {
            readings = AbstractReader.parseData("junk" + AbstractReader.CODE_DELIMITER + code + AbstractReader.CODE_DELIMITER + data);
        } catch (BadReadingsException e) {
            Assert.fail(e.getMessage());
        }

        Assert.assertEquals(code, readings.code);
        Assert.assertEquals(data, readings.data);
    }
}
