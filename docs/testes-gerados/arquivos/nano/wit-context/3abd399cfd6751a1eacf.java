/*
 * @test
 * @summary Tests for java.io.PipedWriter.connect(PipedReader)
 * @run main PipedWriterWitupTest
 */

import java.io.PipedReader;
import java.io.PipedWriter;
import java.io.IOException;

public class PipedWriterWitupTest {

    public static void main(String[] args) {
        testConnectNullPointerException();
        testConnectAlreadyConnected();
        testConnectClosedByReader();
    }

    private static void testConnectNullPointerException() {
        PipedReader reader = new PipedReader();
        PipedWriter writer = new PipedWriter();
        try {
            writer.connect(null);
            System.err.println("Expected NullPointerException not thrown");
            System.exit(1);
        } catch (NullPointerException e) {
            // Expected exception
        } catch (IOException e) {
            System.err.println("Unexpected IOException: " + e);
            System.exit(1);
        }
    }

    private static void testConnectAlreadyConnected() {
        PipedReader reader1 = new PipedReader();
        PipedReader reader2 = new PipedReader();
        try {
            PipedWriter writer = new PipedWriter();
            writer.connect(reader1);
            // Attempt to connect again to a different reader
            writer.connect(reader2);
            System.err.println("Expected IOException for already connected not thrown");
            System.exit(1);
        } catch (IOException e) {
            // Expected exception
        }
    }

    private static void testConnectClosedByReader() {
        PipedReader reader = new PipedReader();
        try {
            reader.closedByReader = true; // Accessing package-private for test; in real scenario, use reflection or package access
            PipedWriter writer = new PipedWriter();
            writer.connect(reader);
            System.err.println("Expected IOException for closed by reader not thrown");
            System.exit(1);
        } catch (IOException e) {
            // Expected exception
        }
    }
}
