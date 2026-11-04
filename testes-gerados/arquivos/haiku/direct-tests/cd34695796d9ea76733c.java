/*
 * @test
 * @summary Basic behavior of the PipedReader(PipedWriter, int) constructor: data flows through the pipe, wraps around the circular buffer, and non-positive pipe sizes are rejected.
 * @run main PipedReaderWitupTest
 */

import java.io.PipedReader;
import java.io.PipedWriter;

public class PipedReaderWitupTest {
    public static void main(String[] args) throws Exception {
        testDataFlowThroughConnectedPipe();
        testCircularBufferWraparound();
        testOneArgConstructorDefaultSize();
        testNonPositivePipeSizeRejected();
    }

    static void testDataFlowThroughConnectedPipe() throws Exception {
        PipedWriter w = new PipedWriter();
        PipedReader r = new PipedReader(w, 4);
        w.write("ab");
        if (!r.ready()) {
            throw new RuntimeException("reader should be ready after write");
        }
        if (r.read() != 'a') {
            throw new RuntimeException("expected 'a'");
        }
        if (r.read() != 'b') {
            throw new RuntimeException("expected 'b'");
        }
        w.close();
        r.close();
    }

    static void testCircularBufferWraparound() throws Exception {
        PipedWriter w = new PipedWriter();
        PipedReader r = new PipedReader(w, 4);
        w.write("abc");
        if (r.read() != 'a' || r.read() != 'b') {
            throw new RuntimeException("expected 'a' then 'b'");
        }
        w.write("de");
        if (r.read() != 'c' || r.read() != 'd' || r.read() != 'e') {
            throw new RuntimeException("expected 'c', 'd', 'e' after wraparound");
        }
        w.close();
        r.close();
    }

    static void testOneArgConstructorDefaultSize() throws Exception {
        PipedWriter w = new PipedWriter();
        PipedReader r = new PipedReader(w);
        w.write('x');
        if (r.read() != 'x') {
            throw new RuntimeException("expected 'x' from default-size pipe");
        }
        w.close();
        r.close();
    }

    static void testNonPositivePipeSizeRejected() throws Exception {
        boolean thrown = false;
        try {
            new PipedReader(new PipedWriter(), 0);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("pipeSize 0 should throw IllegalArgumentException");
        }
    }
}
