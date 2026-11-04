/*
 * @test
 * @summary Test PipedOutputStream.connect(PipedInputStream) for exception and normal behavior
 * @run main PipedOutputStreamWitupTest
 */

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class PipedOutputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testConnectNullThrowsNPE();
        testConnectAlreadyConnectedThrowsIOException();
        testConnectSuccess();
    }

    static void testConnectNullThrowsNPE() {
        PipedOutputStream pos = new PipedOutputStream();
        try {
            pos.connect(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            // expected
        } catch (IOException ioe) {
            throw new RuntimeException("Unexpected IOException instead of NullPointerException", ioe);
        }
    }

    static void testConnectAlreadyConnectedThrowsIOException() throws IOException {
        PipedInputStream pis = new PipedInputStream();
        PipedOutputStream pos1 = new PipedOutputStream(pis);
        PipedOutputStream pos2 = new PipedOutputStream();
        try {
            pos2.connect(pis);
            throw new RuntimeException("Expected IOException not thrown when connecting already connected stream");
        } catch (IOException ioe) {
            // expected
        }
    }

    static void testConnectSuccess() throws IOException {
        PipedInputStream pis = new PipedInputStream();
        PipedOutputStream pos = new PipedOutputStream();
        pos.connect(pis);
        // After connect, pis.connected should be true and pos.sink should be pis
        // We cannot access pis.connected or pos.sink directly, so test observable behavior
        // Write a byte and read it back to verify connection
        pos.write(42);
        int read = pis.read();
        if (read != 42) {
            throw new RuntimeException("Data mismatch after connect: expected 42 but got " + read);
        }
    }
}
