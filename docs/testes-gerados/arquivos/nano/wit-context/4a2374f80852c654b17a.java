/*
 * @test
 * @summary Tests for java.io.PipedOutputStream.connect(PipedInputStream)
 * @run main PipedOutputStreamWitupTest
 */

import java.io.*;

public class PipedOutputStreamWitupTest {

    public static void main(String[] args) {
        testConnectWithNullInputStream();
        testConnectWhenAlreadyConnected();
    }

    private static void testConnectWithNullInputStream() {
        PipedOutputStream pos = new PipedOutputStream();
        try {
            pos.connect(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Test passes
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException", e);
        }
    }

    private static void testConnectWhenAlreadyConnected() {
        try {
            PipedInputStream pis1 = new PipedInputStream();
            PipedOutputStream pos1 = new PipedOutputStream();
            pos1.connect(pis1);
            PipedInputStream pis2 = new PipedInputStream();
            PipedOutputStream pos2 = new PipedOutputStream();
            pos2.connect(pis2);
            // Attempt to connect again with a different stream should throw IOException
            try {
                pos1.connect(pis2);
                throw new AssertionError("Expected IOException not thrown");
            } catch (IOException expected) {
                // Expected exception
            }
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException during setup", e);
        }
    }
}
