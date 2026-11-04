/*
 * @test
 * @summary Test PipedOutputStream(PipedInputStream) constructor for exception handling
 * @run main PipedOutputStreamWitupTest
 */

import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.IOException;

public class PipedOutputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testConstructorWithNull();
        testConstructorWithAlreadyConnected();
    }

    private static void testConstructorWithNull() {
        try {
            new PipedOutputStream(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // expected
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException", e);
        }
    }

    private static void testConstructorWithAlreadyConnected() throws IOException {
        PipedInputStream pis = new PipedInputStream();
        PipedOutputStream pos1 = new PipedOutputStream(pis);
        try {
            // Attempt to connect second PipedOutputStream to the same PipedInputStream
            new PipedOutputStream(pis);
            throw new RuntimeException("Expected IOException not thrown when connecting already connected stream");
        } catch (IOException e) {
            // expected
        } catch (NullPointerException e) {
            throw new RuntimeException("Unexpected NullPointerException", e);
        } finally {
            pos1.close();
            pis.close();
        }
    }
}
