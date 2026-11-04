/*
 * @test
 * @summary Verify PipedWriter.flush behavior for unconnected, connected, writer-closed and reader-closed pipes
 * @run main PipedWriterWitupTest
 */

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

public class PipedWriterWitupTest {

    public static void main(String[] args) throws Exception {
        testUnconnectedFlushDoesNothing();
        testConnectedFlushSucceeds();
        testFlushAfterWriterClosedThrows();
        testFlushAfterReaderClosedThrows();
        System.out.println("PASSED");
    }

    static void testUnconnectedFlushDoesNothing() throws IOException {
        PipedWriter w = new PipedWriter();
        w.flush();
    }

    static void testConnectedFlushSucceeds() throws IOException {
        PipedReader r = new PipedReader();
        PipedWriter w = new PipedWriter(r);
        w.write('a');
        w.flush();
        w.close();
        r.close();
    }

    static void testFlushAfterWriterClosedThrows() throws IOException {
        PipedReader r = new PipedReader();
        PipedWriter w = new PipedWriter(r);
        w.close();
        expectIOException(w);
        r.close();
    }

    static void testFlushAfterReaderClosedThrows() throws IOException {
        PipedReader r = new PipedReader();
        PipedWriter w = new PipedWriter(r);
        r.close();
        expectIOException(w);
        w.close();
    }

    static void expectIOException(PipedWriter w) {
        try {
            w.flush();
        } catch (IOException e) {
            return;
        }
        throw new RuntimeException("expected IOException from flush");
    }
}
