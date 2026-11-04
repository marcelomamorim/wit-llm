/*
 * @test
 * @summary Test PushbackInputStream.reset() throws IOException as expected
 * @run main PushbackInputStreamWitupTest
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;

public class PushbackInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a PushbackInputStream wrapping a simple InputStream
        InputStream in = new InputStream() {
            public int read() {
                return -1; // EOF
            }
        };
        PushbackInputStream pbis = new PushbackInputStream(in);

        // Test that reset() throws IOException with the expected message
        try {
            pbis.reset();
            throw new RuntimeException("Expected IOException not thrown by reset()");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.equals("mark/reset not supported")) {
                throw new RuntimeException("IOException thrown by reset() has unexpected message: " + msg);
            }
        }
    }
}
