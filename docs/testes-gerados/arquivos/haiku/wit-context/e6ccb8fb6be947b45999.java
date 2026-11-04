/*
 * @test
 * @summary White-box-derived checks for PipedOutputStream(PipedInputStream) constructor null and connection-state exceptions
 * @run main PipedOutputStreamWitupTest
 */

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class PipedOutputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testNullSinkThrowsNullPointerException();
        testConnectedConstructorTransfersByte();
        testSecondConnectionThrowsIOException();
    }

    static void testNullSinkThrowsNullPointerException() {
        try {
            new PipedOutputStream(null);
            throw new RuntimeException("Expected NullPointerException for null sink");
        } catch (NullPointerException expected) {
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException for null sink", e);
        }
    }

    static void testConnectedConstructorTransfersByte() throws IOException {
        PipedInputStream snk = new PipedInputStream();
        PipedOutputStream src = new PipedOutputStream(snk);
        src.write(42);
        int value = snk.read();
        if (value != 42) {
            throw new RuntimeException("Expected 42 but got " + value);
        }
        src.close();
        snk.close();
    }

    static void testSecondConnectionThrowsIOException() throws IOException {
        PipedInputStream snk = new PipedInputStream();
        PipedOutputStream first = new PipedOutputStream(snk);
        try {
            new PipedOutputStream(snk);
            throw new RuntimeException("Expected IOException when sink is already connected");
        } catch (IOException expected) {
        } finally {
            first.close();
            snk.close();
        }
    }
}
