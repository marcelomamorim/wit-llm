/*
 * @test
 * @summary Test DeflaterInputStream.available() method behavior
 * @run main DeflaterInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.zip.Deflater;
import java.util.zip.DeflaterInputStream;

public class DeflaterInputStreamWitupTest {
    public static void main(String[] args) throws IOException {
        testAvailableBeforeClose();
        testAvailableAfterClose();
    }

    private static void testAvailableBeforeClose() throws IOException {
        byte[] input = {1, 2, 3, 4, 5};
        ByteArrayInputStream bais = new ByteArrayInputStream(input);
        Deflater deflater = new Deflater();
        DeflaterInputStream dis = new DeflaterInputStream(bais, deflater);

        // Initially, reachEOF is false, so available() should return 1
        int avail = dis.available();
        if (avail != 1) {
            throw new AssertionError("Expected available() to return 1 but got " + avail);
        }

        dis.close();
    }

    private static void testAvailableAfterClose() throws IOException {
        byte[] input = {1, 2, 3, 4, 5};
        ByteArrayInputStream bais = new ByteArrayInputStream(input);
        Deflater deflater = new Deflater();
        DeflaterInputStream dis = new DeflaterInputStream(bais, deflater);

        dis.close();

        try {
            dis.available();
            throw new AssertionError("Expected IOException after close when calling available()");
        } catch (IOException e) {
            // Expected exception
            String msg = e.getMessage();
            if (msg == null || !msg.equals("Stream closed")) {
                throw new AssertionError("Expected IOException with message 'Stream closed' but got: " + msg);
            }
        }
    }
}
