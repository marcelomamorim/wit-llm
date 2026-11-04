/* @test    @summary Test the available() method of InflaterInputStream for correct behavior.    @run main InflaterInputStreamWitupTest */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

public class InflaterInputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testAvailableWhenNotAtEOF();
        testAvailableWhenAtEOF();
    }

    static void testAvailableWhenNotAtEOF() throws IOException {
        byte[] compressedData = {0x78, 0x9c, 0x2b, 0x49, 0x2d, 0x2e, 0x01, 0x00, 0x04, 0x5d, 0x01, 0x0d};
        ByteArrayInputStream bais = new ByteArrayInputStream(compressedData);
        Inflater inflater = new Inflater();
        InflaterInputStream iis = new InflaterInputStream(bais, inflater);
        int avail = iis.available();
        if (avail != 1) {
            throw new AssertionError("Expected available() to return 1 when not at EOF, but got: " + avail);
        }
        iis.close();
    }

    static void testAvailableWhenAtEOF() throws IOException {
        byte[] compressedData = {0x78, 0x9c, 0x2b, 0x49, 0x2d, 0x2e, 0x01, 0x00, 0x04, 0x5d, 0x01, 0x0d};
        ByteArrayInputStream bais = new ByteArrayInputStream(compressedData);
        Inflater inflater = new Inflater();
        InflaterInputStream iis = new InflaterInputStream(bais, inflater);
        // Read all data to reach EOF
        while (iis.read() != -1) {
            // consume data
        }
        int availAfterEOF = iis.available();
        if (availAfterEOF != 0) {
            throw new AssertionError("Expected available() to return 0 after reaching EOF, but got: " + availAfterEOF);
        }
        iis.close();
    }
}
