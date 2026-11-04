/*
 * @test
 * @summary Test PipedOutputStream.connect(PipedInputStream) method behavior
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

    private static void testConnectNullThrowsNPE() {
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

    private static void testConnectAlreadyConnectedThrowsIOException() throws IOException {
        PipedInputStream pis1 = new PipedInputStream();
        PipedInputStream pis2 = new PipedInputStream();
        PipedOutputStream pos1 = new PipedOutputStream(pis1); // connects pos1 to pis1

        PipedOutputStream pos2 = new PipedOutputStream();

        // Attempt to connect pos2 to pis1 which is already connected
        try {
            pos2.connect(pis1);
            throw new RuntimeException("Expected IOException not thrown when connecting to already connected PipedInputStream");
        } catch (IOException ioe) {
            // expected
        }

        // Attempt to connect pos1 again to pis2 (pos1 already connected)
        try {
            pos1.connect(pis2);
            throw new RuntimeException("Expected IOException not thrown when PipedOutputStream already connected");
        } catch (IOException ioe) {
            // expected
        }
    }

    private static void testConnectSuccess() throws IOException {
        PipedInputStream pis = new PipedInputStream();
        PipedOutputStream pos = new PipedOutputStream();

        pos.connect(pis);

        // After connect, pis.in == -1, pis.out == 0, pis.connected == true
        if (pis.in != -1) {
            throw new RuntimeException("pis.in expected to be -1 but was " + pis.in);
        }
        if (pis.out != 0) {
            throw new RuntimeException("pis.out expected to be 0 but was " + pis.out);
        }
        if (!pis.connected) {
            throw new RuntimeException("pis.connected expected to be true but was false");
        }
    }
}
