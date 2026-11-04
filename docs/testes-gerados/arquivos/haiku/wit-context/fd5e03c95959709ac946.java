/*
 * @test
 * @summary Verifies observable behavior of the no-argument PipedInputStream constructor
 * @run main PipedInputStreamWitupTest
 */

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class PipedInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testUnconnectedReadThrowsIOException();
        testUnconnectedAvailableIsZero();
        testCloseOnUnconnectedStreamDoesNotThrow();
        testConnectedStreamTransfersData();
    }

    private static void testUnconnectedReadThrowsIOException() throws Exception {
        PipedInputStream pis = new PipedInputStream();
        boolean thrown = false;
        try {
            pis.read();
        } catch (IOException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("read() on unconnected stream must throw IOException");
        }
    }

    private static void testUnconnectedAvailableIsZero() throws Exception {
        PipedInputStream pis = new PipedInputStream();
        if (pis.available() != 0) {
            throw new AssertionError("available() on new stream must be 0");
        }
    }

    private static void testCloseOnUnconnectedStreamDoesNotThrow() throws Exception {
        PipedInputStream pis = new PipedInputStream();
        pis.close();
    }

    private static void testConnectedStreamTransfersData() throws Exception {
        PipedInputStream pis = new PipedInputStream();
        PipedOutputStream pos = new PipedOutputStream(pis);
        pos.write(42);
        if (pis.available() != 1) {
            throw new AssertionError("available() must be 1 after one byte written");
        }
        int value = pis.read();
        if (value != 42) {
            throw new AssertionError("expected 42 but got " + value);
        }
        pos.close();
        pis.close();
    }
}
