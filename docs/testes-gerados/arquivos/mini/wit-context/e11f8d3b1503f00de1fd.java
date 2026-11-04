/*
 * @test
 * @summary Test java.util.zip.InflaterInputStream.available() method behavior including closed stream and EOF
 * @run main InflaterInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

public class InflaterInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testAvailableOnOpenStream();
        testAvailableAfterEOF();
        testAvailableOnClosedStreamThrows();
        System.out.println("All tests passed.");
    }

    private static void testAvailableOnOpenStream() throws IOException {
        byte[] compressedData = new byte[] {0x78, (byte)0x9c, 0x03, 0x00, 0x00, 0x00, 0x00, 0x01}; // minimal deflate stream
        ByteArrayInputStream bais = new ByteArrayInputStream(compressedData);
        Inflater inflater = new Inflater();
        InflaterInputStream iis = new InflaterInputStream(bais, inflater);

        int avail = iis.available();
        if (avail != 1) {
            throw new AssertionError("Expected available() to return 1 on open stream but got " + avail);
        }

        iis.close();
    }

    private static void testAvailableAfterEOF() throws IOException {
        byte[] compressedData = new byte[] {0x78, (byte)0x9c, 0x03, 0x00, 0x00, 0x00, 0x00, 0x01}; // minimal deflate stream
        ByteArrayInputStream bais = new ByteArrayInputStream(compressedData);
        Inflater inflater = new Inflater();
        InflaterInputStream iis = new InflaterInputStream(bais, inflater);

        // Read all data to reach EOF
        byte[] buffer = new byte[10];
        while (iis.read(buffer) != -1) {
            // consume all
        }

        int availAfterEOF = iis.available();
        if (availAfterEOF != 0) {
            throw new AssertionError("Expected available() to return 0 after EOF but got " + availAfterEOF);
        }

        iis.close();
    }

    private static void testAvailableOnClosedStreamThrows() {
        byte[] compressedData = new byte[] {0x78, (byte)0x9c, 0x03, 0x00, 0x00, 0x00, 0x00, 0x01};
        ByteArrayInputStream bais = new ByteArrayInputStream(compressedData);
        Inflater inflater = new Inflater();
        InflaterInputStream iis = new InflaterInputStream(bais, inflater);

        try {
            iis.close();
            iis.available();
            throw new AssertionError("Expected IOException on available() after close");
        } catch (IOException e) {
            // Expected
        }
    }
}
