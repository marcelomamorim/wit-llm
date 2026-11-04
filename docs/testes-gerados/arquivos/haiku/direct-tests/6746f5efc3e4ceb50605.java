/*
 * @test
 * @summary Verifies PipedWriter.write(int) behavior when unconnected and when connected to a PipedReader
 * @run main PipedWriterWitupTest
 */

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

public class PipedWriterWitupTest {

    public static void main(String[] args) throws Exception {
        testUnconnectedWriteThrows();
        testConnectedWriteDeliversChar();
        testWriteAfterConnectMethod();
        testSequentialWritesPreserveOrder();
    }

    static void testUnconnectedWriteThrows() {
        PipedWriter w = new PipedWriter();
        try {
            w.write('x');
        } catch (IOException e) {
            if (!"Pipe not connected".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message: " + e.getMessage());
            }
            return;
        }
        throw new RuntimeException("Expected IOException for unconnected writer");
    }

    static void testConnectedWriteDeliversChar() throws Exception {
        PipedReader r = new PipedReader();
        PipedWriter w = new PipedWriter(r);
        w.write('A');
        int c = r.read();
        if (c != 'A') {
            throw new RuntimeException("Expected 'A' but got " + c);
        }
    }

    static void testWriteAfterConnectMethod() throws Exception {
        PipedReader r = new PipedReader();
        PipedWriter w = new PipedWriter();
        w.connect(r);
        w.write('Z');
        int c = r.read();
        if (c != 'Z') {
            throw new RuntimeException("Expected 'Z' but got " + c);
        }
    }

    static void testSequentialWritesPreserveOrder() throws Exception {
        PipedReader r = new PipedReader();
        PipedWriter w = new PipedWriter(r);
        w.write('a');
        w.write('b');
        int first = r.read();
        int second = r.read();
        if (first != 'a' || second != 'b') {
            throw new RuntimeException("Unexpected order: " + first + "," + second);
        }
    }
}
