/*
 * @test
 * @summary Test DeflaterInputStream.available() method behavior including IOException on closed stream
 * @run main DeflaterInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.zip.Deflater;
import java.util.zip.DeflaterInputStream;

public class DeflaterInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testAvailableReturnsOneWhenOpen();
        testAvailableReturnsZeroWhenEOF();
        testAvailableThrowsIOExceptionWhenClosed();
    }

    private static void testAvailableReturnsOneWhenOpen() throws IOException {
        byte[] data = {1, 2, 3};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        Deflater deflater = new Deflater();
        DeflaterInputStream dis = new DeflaterInputStream(bais, deflater);

        int avail = dis.available();
        if (avail != 1) {
            throw new AssertionError("Expected available() to return 1 when stream is open, got: " + avail);
        }

        dis.close();
    }

    private static void testAvailableReturnsZeroWhenEOF() throws IOException {
        byte[] data = {};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        Deflater deflater = new Deflater();
        DeflaterInputStream dis = new DeflaterInputStream(bais, deflater);

        // Read all data to reach EOF
        while (dis.read() != -1) {
            // consume
        }

        int avail = dis.available();
        if (avail != 0) {
            throw new AssertionError("Expected available() to return 0 after EOF, got: " + avail);
        }

        dis.close();
    }

    private static void testAvailableThrowsIOExceptionWhenClosed() {
        byte[] data = {1, 2, 3};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        Deflater deflater = new Deflater();
        DeflaterInputStream dis = new DeflaterInputStream(bais, deflater);

        try {
            dis.close();
            dis.available();
            throw new AssertionError("Expected IOException when calling available() on closed stream");
        } catch (IOException e) {
            // Expected exception
        }
    }
}
