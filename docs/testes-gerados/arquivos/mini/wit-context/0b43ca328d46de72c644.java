/*
 * @test
 * @summary Test java.io.PipedWriter.flush() behavior including IOException on closed pipe
 * @run main PipedWriterWitupTest
 */

import java.io.PipedWriter;
import java.io.PipedReader;
import java.io.IOException;

public class PipedWriterWitupTest {
    public static void main(String[] args) throws Exception {
        testFlushNormal();
        testFlushThrowsIOExceptionWhenClosedByReader();
        testFlushThrowsIOExceptionWhenWriterClosed();
    }

    private static void testFlushNormal() throws Exception {
        PipedReader reader = new PipedReader();
        PipedWriter writer = new PipedWriter(reader);

        // flush should not throw when pipe is open
        writer.flush();
    }

    private static void testFlushThrowsIOExceptionWhenClosedByReader() throws Exception {
        PipedReader reader = new PipedReader();
        PipedWriter writer = new PipedWriter(reader);

        // simulate reader closed
        // The field closedByReader is package-private in PipedReader, but not public.
        // We cannot access it directly, so we close the reader to simulate.
        reader.close();

        try {
            writer.flush();
            throw new RuntimeException("Expected IOException not thrown when sink.closedByReader is true");
        } catch (IOException e) {
            // expected
        }
    }

    private static void testFlushThrowsIOExceptionWhenWriterClosed() throws Exception {
        PipedReader reader = new PipedReader();
        PipedWriter writer = new PipedWriter(reader);

        // close the writer
        writer.close();

        try {
            writer.flush();
            throw new RuntimeException("Expected IOException not thrown when writer is closed");
        } catch (IOException e) {
            // expected
        }
    }
}
