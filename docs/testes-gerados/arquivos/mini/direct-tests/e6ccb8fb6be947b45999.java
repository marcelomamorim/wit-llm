/*
 * @test
 * @summary Test PipedOutputStream constructor with PipedInputStream argument
 * @run main PipedOutputStreamWitupTest
 */

import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.IOException;

public class PipedOutputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testConstructorConnectsStreams();
        testConstructorThrowsOnNull();
    }

    private static void testConstructorConnectsStreams() throws IOException {
        PipedInputStream pis = new PipedInputStream();
        PipedOutputStream pos = new PipedOutputStream(pis);
        // Write a byte and read it back to verify connection
        pos.write(42);
        int read = pis.read();
        if (read != 42) {
            throw new RuntimeException("Data mismatch: expected 42 but got " + read);
        }
        pos.close();
        pis.close();
    }

    private static void testConstructorThrowsOnNull() {
        try {
            new PipedOutputStream(null);
            throw new RuntimeException("Expected IOException when passing null to constructor");
        } catch (IOException e) {
            // Expected
        }
    }
}
