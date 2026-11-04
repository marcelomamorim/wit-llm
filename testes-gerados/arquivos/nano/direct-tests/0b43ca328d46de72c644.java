/* @test    @summary Test the flush() method of java.io.PipedWriter    @run main PipedWriterWitupTest */

import java.io.PipedReader;
import java.io.PipedWriter;
import java.io.IOException;

public class PipedWriterWitupTest {

    public static void main(String[] args) throws Exception {
        testFlushWithConnectedPipe();
        testFlushWithUnconnectedPipe();
        testFlushWithClosedPipe();
    }

    static void testFlushWithConnectedPipe() throws IOException {
        PipedReader reader = new PipedReader();
        PipedWriter writer = new PipedWriter();
        writer.connect(reader);
        try {
            writer.flush();
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException during flush with connected pipe", e);
        }
    }

    static void testFlushWithUnconnectedPipe() {
        PipedWriter writer = new PipedWriter();
        try {
            writer.flush();
            throw new AssertionError("Expected IOException not thrown for unconnected pipe");
        } catch (IOException e) {
            // Expected exception
            if (!"Pipe closed".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage(), e);
            }
        }
    }

    static void testFlushWithClosedPipe() throws IOException {
        PipedReader reader = new PipedReader();
        PipedWriter writer = new PipedWriter();
        writer.connect(reader);
        // Simulate pipe closure by setting sink's closedByReader and writer's closed
        // Since these are private, we test by closing the reader to cause sink.closedByReader to be true
        // but as we cannot access private fields, we simulate by closing the writer and expecting no exception
        // Alternatively, we can test that calling flush after closing the writer throws an exception
        // but the current implementation does not throw on close, so we skip this test
        // Instead, test that calling flush after closing the writer throws IOException
        // but since close() is not overridden, we simulate by setting closed to true via reflection (not allowed), so we skip
        // Therefore, we test that calling flush on a disconnected or closed writer throws IOException
        // but in current code, no such exception is thrown unless sink is null or closed is true
        // We proceed to close the writer and then call flush to see if it throws
        // Since close() is not overridden, we call close() from Writer, which sets closed to true
        writer.close();
        try {
            writer.flush();
            throw new AssertionError("Expected IOException not thrown after closing writer");
        } catch (IOException e) {
            if (!"Pipe closed".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message after close: " + e.getMessage(), e);
            }
        }
    }
}
