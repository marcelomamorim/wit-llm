/*
 * @test
 * @summary Test java.io.PipedWriter.connect(PipedReader) method
 * @run main PipedWriterWitupTest
 */

import java.io.PipedWriter;
import java.io.PipedReader;
import java.io.IOException;

public class PipedWriterWitupTest {
    public static void main(String[] args) throws Exception {
        testConnectNullThrowsNPE();
        testConnectAlreadyConnectedThrowsIOException();
        testConnectClosedPipeThrowsIOException();
        testConnectSuccess();
    }

    static void testConnectNullThrowsNPE() {
        PipedWriter pw = new PipedWriter();
        try {
            pw.connect(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            // expected
        } catch (IOException ioe) {
            throw new RuntimeException("Unexpected IOException", ioe);
        }
    }

    static void testConnectAlreadyConnectedThrowsIOException() throws IOException {
        PipedReader pr1 = new PipedReader();
        PipedReader pr2 = new PipedReader();
        PipedWriter pw = new PipedWriter();
        pw.connect(pr1);
        try {
            pw.connect(pr2);
            throw new RuntimeException("Expected IOException not thrown when writer already connected");
        } catch (IOException ioe) {
            // expected
        }

        PipedWriter pw2 = new PipedWriter();
        try {
            pw2.connect(pr1); // pr1 already connected
            throw new RuntimeException("Expected IOException not thrown when reader already connected");
        } catch (IOException ioe) {
            // expected
        }
    }

    static void testConnectClosedPipeThrowsIOException() throws IOException {
        PipedReader pr = new PipedReader();
        PipedWriter pw = new PipedWriter();

        // simulate closed reader
        pr.closedByReader = true;
        try {
            pw.connect(pr);
            throw new RuntimeException("Expected IOException not thrown when reader closed");
        } catch (IOException ioe) {
            // expected
        }

        // reset and simulate closed writer
        pr.closedByReader = false;
        pw = new PipedWriter();
        pw.closed = true;
        try {
            pw.connect(pr);
            throw new RuntimeException("Expected IOException not thrown when writer closed");
        } catch (IOException ioe) {
            // expected
        }
    }

    static void testConnectSuccess() throws IOException {
        PipedReader pr = new PipedReader();
        PipedWriter pw = new PipedWriter();
        pw.connect(pr);

        if (pr.in != -1) {
            throw new RuntimeException("Expected pr.in == -1 after connect");
        }
        if (pr.out != 0) {
            throw new RuntimeException("Expected pr.out == 0 after connect");
        }
        if (!pr.connected) {
            throw new RuntimeException("Expected pr.connected == true after connect");
        }
    }
}
