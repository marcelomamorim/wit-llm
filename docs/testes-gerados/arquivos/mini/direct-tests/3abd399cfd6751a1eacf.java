/*
 * @test
 * @summary Test PipedWriter(PipedReader) constructor behavior
 * @run main PipedWriterWitupTest
 */

import java.io.PipedReader;
import java.io.PipedWriter;
import java.io.IOException;

public class PipedWriterWitupTest {
    public static void main(String[] args) throws Exception {
        testConstructorConnectsSuccessfully();
        testConstructorThrowsOnNull();
        testConstructorThrowsOnAlreadyConnected();
        testConstructorThrowsOnClosedPipe();
    }

    private static void testConstructorConnectsSuccessfully() throws IOException {
        PipedReader reader = new PipedReader();
        PipedWriter writer = new PipedWriter(reader);
        // If no exception, connection succeeded
        writer.close();
        reader.close();
    }

    private static void testConstructorThrowsOnNull() {
        try {
            new PipedWriter(null);
            throw new RuntimeException("Expected NullPointerException");
        } catch (NullPointerException e) {
            // expected
        } catch (IOException e) {
            throw new RuntimeException("Expected NullPointerException, got IOException", e);
        }
    }

    private static void testConstructorThrowsOnAlreadyConnected() throws IOException {
        PipedReader reader = new PipedReader();
        PipedWriter writer1 = new PipedWriter(reader);
        try {
            new PipedWriter(reader);
            throw new RuntimeException("Expected IOException for already connected pipe");
        } catch (IOException e) {
            // expected
        }
        writer1.close();
        reader.close();
    }

    private static void testConstructorThrowsOnClosedPipe() throws IOException {
        PipedReader reader = new PipedReader();
        PipedWriter writer = new PipedWriter();
        writer.close();
        try {
            new PipedWriter(reader);
            // The constructor calls connect, which checks if writer or reader closed
            // But reader is open, writer is closed, so connect should throw IOException
            // However, since writer is closed but not connected, connect will throw IOException
            // So we expect IOException
        } catch (IOException e) {
            // expected
            reader.close();
            return;
        }
        reader.close();
        throw new RuntimeException("Expected IOException for closed pipe");
    }
}
