/* @test    @summary Test connect method of PipedOutputStream for null and already connected cases    @run main PipedOutputStreamWitupTest */

import java.io.*;

public class PipedOutputStreamWitupTest {

    public static void main(String[] args) {
        testConnectNull();
        testConnectAlreadyConnected();
    }

    static void testConnectNull() {
        PipedOutputStream pos = new PipedOutputStream();
        try {
            pos.connect(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected exception
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException: " + e);
        }
    }

    static void testConnectAlreadyConnected() {
        PipedInputStream pis1 = new PipedInputStream();
        PipedOutputStream pos1 = new PipedOutputStream();
        try {
            pos1.connect(pis1);
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException during first connect: " + e);
        }
        PipedInputStream pis2 = new PipedInputStream();
        PipedOutputStream pos2 = new PipedOutputStream();
        try {
            pos2.connect(pis1);
            throw new AssertionError("Expected IOException not thrown on second connect");
        } catch (IOException e) {
            if (!"Already connected".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + e);
            }
        }
    }
}
