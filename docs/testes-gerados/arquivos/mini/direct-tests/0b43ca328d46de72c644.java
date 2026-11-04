/*
 * @test
 * @summary Test java.io.PipedWriter.flush() behavior
 * @run main PipedWriterWitupTest
 */

import java.io.PipedWriter;
import java.io.PipedReader;
import java.io.IOException;

public class PipedWriterWitupTest {
    public static void main(String[] args) throws Exception {
        testFlushNotConnected();
        testFlushConnected();
        testFlushClosedByWriter();
        testFlushClosedByReader();
    }

    static void testFlushNotConnected() throws IOException {
        PipedWriter pw = new PipedWriter();
        // flush when not connected should do nothing and not throw
        pw.flush();
    }

    static void testFlushConnected() throws Exception {
        final PipedReader pr = new PipedReader();
        final PipedWriter pw = new PipedWriter(pr);

        // flush when connected and open should notify sink
        // We test that flush does not throw and returns normally
        pw.flush();
    }

    static void testFlushClosedByWriter() throws Exception {
        final PipedReader pr = new PipedReader();
        final PipedWriter pw = new PipedWriter(pr);

        // Close the writer to set closed flag
        pw.close();

        try {
            pw.flush();
            throw new RuntimeException("Expected IOException not thrown");
        } catch (IOException e) {
            if (!"Pipe closed".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected IOException message: " + e.getMessage());
            }
        }
    }

    static void testFlushClosedByReader() throws Exception {
        final PipedReader pr = new PipedReader();
        final PipedWriter pw = new PipedWriter(pr);

        // Close the reader to set closedByReader flag
        pr.close();

        try {
            pw.flush();
            throw new RuntimeException("Expected IOException not thrown");
        } catch (IOException e) {
            if (!"Pipe closed".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected IOException message: " + e.getMessage());
            }
        }
    }
}
