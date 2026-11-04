/*
 * @test
 * @summary Test PipedWriter(PipedReader) constructor for correct exception handling and connection
 * @run main PipedWriterWitupTest
 */

import java.io.PipedWriter;
import java.io.PipedReader;
import java.io.IOException;

public class PipedWriterWitupTest {
    public static void main(String[] args) throws Exception {
        testNullPipedReader();
        testAlreadyConnected();
        testClosedPipe();
        testValidConnection();
    }

    static void testNullPipedReader() {
        try {
            new PipedWriter(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // expected
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException instead of NullPointerException", e);
        }
    }

    static void testAlreadyConnected() throws IOException {
        PipedReader pr = new PipedReader();
        PipedWriter pw1 = new PipedWriter(pr); // first connection
        try {
            new PipedWriter(pr); // second connection should fail
            throw new RuntimeException("Expected IOException for already connected pipe not thrown");
        } catch (IOException e) {
            // expected
        }
        pw1.close();
        pr.close();
    }

    static void testClosedPipe() throws IOException {
        PipedReader pr = new PipedReader();
        pr.close(); // close reader before connecting
        try {
            new PipedWriter(pr);
            throw new RuntimeException("Expected IOException for closed pipe not thrown");
        } catch (IOException e) {
            // expected
        }
    }

    static void testValidConnection() throws IOException {
        PipedReader pr = new PipedReader();
        PipedWriter pw = new PipedWriter(pr);
        // Write and read a character to verify connection
        pw.write('A');
        pw.flush();
        int readChar = pr.read();
        if (readChar != 'A') {
            throw new RuntimeException("Expected to read 'A' but got " + readChar);
        }
        pw.close();
        pr.close();
    }
}
