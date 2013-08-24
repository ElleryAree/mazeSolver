package dataTransmitter.reader;

import dataTransmitter.reader.exception.BadReadingsException;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.Charset;

public abstract class AbstractReader {
    public static String CODE_DELIMITER = "\n";

    public Readings read() throws IOException, BadReadingsException {
        InputStream stream = null;
        try{
            stream = getInputStream();
            if (stream == null){
                throw new BadReadingsException("No input stream found");
            }

            return parseData(convertStreamToString(stream));
        } finally {
            if (stream != null){
                stream.close();
            }
        }
    }

    protected static Readings parseData(String s) throws BadReadingsException {
        int codeDelimiter = s.indexOf(CODE_DELIMITER);
        if (codeDelimiter <= 0){
            throw new BadReadingsException("Data \"" + s + "\" does not contain code or code delimiter");
        }

        String[] parts = s.split(CODE_DELIMITER);

        if (parts.length != 3){
            throw new BadReadingsException("Bad format \"" + s + "\". Expected exactly 3 parts, but found " + parts.length);
        }

        Readings readings = new Readings();
        readings.code = parts[1];
        readings.data = parts[2];

        return readings;
    }

    protected abstract InputStream getInputStream();
    public abstract boolean canRead();

    private String convertStreamToString(InputStream inputStream) throws IOException {
        StringWriter writer = new StringWriter();
        IOUtils.copy(inputStream, writer, Charset.defaultCharset());
        return writer.toString();
    }
}
