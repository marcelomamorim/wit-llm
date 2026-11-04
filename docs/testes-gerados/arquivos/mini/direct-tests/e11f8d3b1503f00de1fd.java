/*
 * @test
 * @summary Test java.util.zip.InflaterInputStream.available() method behavior
 * @run main InflaterInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

public class InflaterInputStreamWitupTest {
    public static void main(String[] args) throws IOException {
        testAvailableBeforeEOF();
        testAvailableAfterEOF();
        testAvailableAfterInfFinished();
        testAvailableThrowsAfterClose();
    }

    private static void testAvailableBeforeEOF() throws IOException {
        byte[] compressedData = {0x78, (byte)0x9c, 0x03, 0x00, 0x00, 0x00, 0x00, 0x01}; // minimal deflate stream
        Inflater inflater = new Inflater();
        InflaterInputStream iis = new InflaterInputStream(new ByteArrayInputStream(compressedData), inflater, 8);

        int avail = iis.available();
        if (avail != 1) {
            throw new AssertionError("Expected available() to return 1 before EOF, got " + avail);
        }
        iis.close();
    }

    private static void testAvailableAfterEOF() throws IOException {
        byte[] compressedData = {0x78, (byte)0x9c, 0x03, 0x00, 0x00, 0x00, 0x00, 0x01};
        Inflater inflater = new Inflater();
        InflaterInputStream iis = new InflaterInputStream(new ByteArrayInputStream(compressedData), inflater, 8);

        // Read all data to reach EOF
        byte[] buf = new byte[10];
        while (iis.read(buf) != -1) {
            // consume all
        }

        int avail = iis.available();
        if (avail != 0) {
            throw new AssertionError("Expected available() to return 0 after EOF, got " + avail);
        }
        iis.close();
    }

    private static void testAvailableAfterInfFinished() throws IOException {
        byte[] compressedData = {0x78, (byte)0x9c, 0x03, 0x00, 0x00, 0x00, 0x00, 0x01};
        Inflater inflater = new Inflater();
        InflaterInputStream iis = new InflaterInputStream(new ByteArrayInputStream(compressedData), inflater, 8);

        // Force inflater to finish by reading all data
        byte[] buf = new byte[10];
        while (iis.read(buf) != -1) {
            // consume all
        }

        // available() should return 0 and set reachEOF true
        int avail1 = iis.available();
        int avail2 = iis.available();

        if (avail1 != 0 || avail2 != 0) {
            throw new AssertionError("Expected available() to return 0 after inflater finished, got " + avail1 + " and " + avail2);
        }
        iis.close();
    }

    private static void testAvailableThrowsAfterClose() {
        byte[] compressedData = {0x78, (byte)0x9c, 0x03, 0x00, 0x00, 0x00, 0x00, 0x01};
        Inflater inflater = new Inflater();
        InflaterInputStream iis = new InflaterInputStream(new ByteArrayInputStream(compressedData), inflater, 8);
        try {
            iis.close();
            iis.available();
            throw new AssertionError("Expected IOException after close when calling available()");
        } catch (IOException e) {
            // expected
        }
    }
}
