/*
 * @test
 * @summary Test PushbackInputStream.read() behavior including pushback buffer and underlying stream
 * @run main PushbackInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PushbackInputStream;

public class PushbackInputStreamWitupTest {
    public static void main(String[] args) throws IOException {
        testReadFromPushbackBuffer();
        testReadFromUnderlyingStream();
        testReadAfterCloseThrows();
    }

    private static void testReadFromPushbackBuffer() throws IOException {
        byte[] data = {10, 20, 30};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        PushbackInputStream pis = new PushbackInputStream(bais, 3);

        // Push back bytes into the buffer
        pis.unread(1); // push back byte 1
        pis.unread(2); // push back byte 2
        pis.unread(3); // push back byte 3

        // Now read() should return bytes from pushback buffer in order 3,2,1
        int r1 = pis.read();
        if (r1 != 3) throw new AssertionError("Expected 3 but got " + r1);
        int r2 = pis.read();
        if (r2 != 2) throw new AssertionError("Expected 2 but got " + r2);
        int r3 = pis.read();
        if (r3 != 1) throw new AssertionError("Expected 1 but got " + r3);
    }

    private static void testReadFromUnderlyingStream() throws IOException {
        byte[] data = {40, 50};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        PushbackInputStream pis = new PushbackInputStream(bais, 2);

        // No pushback, read() should read from underlying stream
        int r1 = pis.read();
        if (r1 != 40) throw new AssertionError("Expected 40 but got " + r1);
        int r2 = pis.read();
        if (r2 != 50) throw new AssertionError("Expected 50 but got " + r2);

        // End of stream returns -1
        int r3 = pis.read();
        if (r3 != -1) throw new AssertionError("Expected -1 but got " + r3);
    }

    private static void testReadAfterCloseThrows() throws IOException {
        byte[] data = {60};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        PushbackInputStream pis = new PushbackInputStream(bais);
        pis.close();
        try {
            pis.read();
            throw new AssertionError("Expected IOException on read after close");
        } catch (IOException e) {
            // Expected
            String msg = e.getMessage();
            if (msg == null || !msg.equals("Stream closed")) {
                throw new AssertionError("Expected IOException with message 'Stream closed' but got: " + msg);
            }
        }
    }
}
