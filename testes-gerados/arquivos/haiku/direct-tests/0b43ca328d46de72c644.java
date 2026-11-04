/*
 * @test
 * @summary Verifies PipedWriter.flush() behavior for unconnected, connected-open, and reader-closed pipes
 * @run main PipedWriterWitupTest
 */

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

public class PipedWriterWitupTest {

    public static void main(String[] args) throws Exception {
        testFlushUnconnectedDoesNotThrow();
        testFlushConnectedOpenDoesNotThrow();
        testFlushAfterReaderClosedThrows();
        System.out.println("PASSED");
    }

    static void testFlushUnconnectedDoesNotThrow() throws IOException {
        PipedWriter w = new PipedWriter();
        w.flush();
    }

    static void testFlushConnectedOpenDoesNotThrow() throws IOException {
        PipedReader r = new PipedReader();
        PipedWriter w = new PipedWriter(r);
        w.flush();
    }

    static void testFlushAfterReaderClosedThrows() throws IOException {
        PipedReader r = new PipedReader();
        PipedWriter w = new PipedWriter(r);
        r.close();
        try {
            w.flush();
            throw new AssertionError("expected IOException from flush() after reader closed");
        } catch (IOException expected) {
            if (!"Pipe closed".equals(expected.getMessage())) {
                throw new AssertionError("unexpected message: " + expected.getMessage());
            }
        }
    }
}
