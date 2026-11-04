/*
 * @test
 * @summary Test OutputStream.write(int) behavior including IOException on closed nullOutputStream
 * @run main OutputStreamWitupTest
 */

import java.io.IOException;
import java.io.OutputStream;

public class OutputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testNullOutputStreamWriteOpen();
        testNullOutputStreamWriteClosedThrows();
    }

    private static void testNullOutputStreamWriteOpen() throws IOException {
        OutputStream os = OutputStream.nullOutputStream();
        // Writing a byte should succeed silently when open
        os.write(0);
        os.write(255);
        // Writing after close should throw IOException, tested separately
    }

    private static void testNullOutputStreamWriteClosedThrows() {
        OutputStream os = OutputStream.nullOutputStream();
        try {
            os.close();
            os.write(0);
            throw new AssertionError("Expected IOException after close not thrown");
        } catch (IOException e) {
            // Expected exception
            String msg = e.getMessage();
            if (msg == null || !msg.equals("Stream closed")) {
                throw new AssertionError("IOException message mismatch: " + msg);
            }
        }
    }
}
