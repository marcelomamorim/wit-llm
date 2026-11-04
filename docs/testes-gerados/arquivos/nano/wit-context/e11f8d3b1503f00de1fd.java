/*
 * @test
 * @summary Test for InflaterInputStream.available() method, focusing on stream closed condition.
 * @run main InflaterInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

public class InflaterInputStreamWitupTest {

    public static void main(String[] args) {
        testAvailableWhenOpen();
        testAvailableWhenClosed();
    }

    static void testAvailableWhenOpen() {
        try {
            byte[] data = {0x78, 0x9c, 0x63, 0x60, 0x00, 0x00, 0x00, 0x02, 0x00, 0x01};
            ByteArrayInputStream bais = new ByteArrayInputStream(data);
            Inflater inflater = new Inflater();
            InflaterInputStream iis = new InflaterInputStream(bais, inflater);
            int result = iis.available();
            if (result != 1) {
                throw new AssertionError("Expected available() to return 1 when stream is open and not at EOF, but got: " + result);
            }
            iis.close();
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException in testAvailableWhenOpen", e);
        }
    }

    static void testAvailableWhenClosed() {
        try {
            byte[] data = {0x78, 0x9c, 0x63, 0x60, 0x00, 0x00, 0x00, 0x02, 0x00, 0x01};
            ByteArrayInputStream bais = new ByteArrayInputStream(data);
            Inflater inflater = new Inflater();
            InflaterInputStream iis = new InflaterInputStream(bais, inflater);
            iis.close();
            try {
                iis.available();
                throw new AssertionError("Expected IOException when calling available() on closed stream");
            } catch (IOException e) {
                // Expected exception
            }
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException in testAvailableWhenClosed", e);
        }
    }
}
