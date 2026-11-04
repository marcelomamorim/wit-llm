/*
 * @test
 * @summary Test java.io.PipedWriter.connect(PipedReader) for exception paths and normal connect
 * @run main PipedWriterWitupTest
 */

import java.io.PipedReader;
import java.io.PipedWriter;
import java.io.IOException;

public class PipedWriterWitupTest {
    public static void main(String[] args) throws Exception {
        testConnectNullThrowsNPE();
        testConnectAlreadyConnectedThrowsIOException();
        testConnectClosedPipeThrowsIOException();
        testConnectSuccess();
    }

    private static void testConnectNullThrowsNPE() {
        PipedWriter pw = new PipedWriter();
        try {
            pw.connect(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            // expected
        } catch (IOException ioe) {
            throw new RuntimeException("Unexpected IOException instead of NullPointerException", ioe);
        }
    }

    private static void testConnectAlreadyConnectedThrowsIOException() throws IOException {
        PipedReader pr1 = new PipedReader();
        PipedReader pr2 = new PipedReader();
        PipedWriter pw = new PipedWriter(pr1); // connects pw to pr1

        // Attempt to connect pw again to pr2 should throw IOException
        try {
            pw.connect(pr2);
            throw new RuntimeException("Expected IOException for already connected writer not thrown");
        } catch (IOException ioe) {
            // expected
        }

        // Attempt to connect a new PipedWriter to pr1 (already connected) should throw IOException
        PipedWriter pw2 = new PipedWriter();
        try {
            pw2.connect(pr1);
            throw new RuntimeException("Expected IOException for already connected reader not thrown");
        } catch (IOException ioe) {
            // expected
        }
    }

    private static void testConnectClosedPipeThrowsIOException() throws IOException {
        PipedReader pr = new PipedReader();
        PipedWriter pw = new PipedWriter();

        // Close the reader to simulate closedByReader
        pr.close();

        try {
            pw.connect(pr);
            throw new RuntimeException("Expected IOException for closed pipe not thrown");
        } catch (IOException ioe) {
            // expected
        }

        // Also test closed writer
        PipedReader pr2 = new PipedReader();
        PipedWriter pw2 = new PipedWriter();
        pw2.close();
        try {
            pw2.connect(pr2);
            throw new RuntimeException("Expected IOException for closed writer not thrown");
        } catch (IOException ioe) {
            // expected
        }
    }

    private static void testConnectSuccess() throws IOException {
        PipedReader pr = new PipedReader();
        PipedWriter pw = new PipedWriter();

        pw.connect(pr);

        // After connect, pr.connected and pw.sink should be set
        // pr.connected is package-private, so we cannot assert directly
        // Instead, test that writing a char does not throw and can be read

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
