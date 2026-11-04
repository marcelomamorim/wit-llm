/*
 * @test
 * @summary Test PipedOutputStream.write(int) behavior including exception when not connected
 * @run main PipedOutputStreamWitupTest
 */

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class PipedOutputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testWriteThrowsIfNotConnected();
        testWriteAfterConnectSucceeds();
    }

    static void testWriteThrowsIfNotConnected() {
        PipedOutputStream pos = new PipedOutputStream();
        try {
            pos.write(42);
            throw new RuntimeException("Expected IOException not thrown");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.equals("Pipe not connected")) {
                throw new RuntimeException("Unexpected IOException message: " + msg);
            }
        }
    }

    static void testWriteAfterConnectSucceeds() throws IOException {
        PipedInputStream pis = new PipedInputStream();
        PipedOutputStream pos = new PipedOutputStream(pis);
        pos.write(65); // write ASCII 'A'
        // read the byte to verify it was received
        int read = pis.read();
        if (read != 65) {
            throw new RuntimeException("Expected to read 65 but got " + read);
        }
    }
}
