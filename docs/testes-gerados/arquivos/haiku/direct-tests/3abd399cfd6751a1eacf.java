/*
 * @test
 * @summary Verify PipedWriter(PipedReader) connects the pipe and reports null, already-connected and closed-reader errors
 * @run main PipedWriterWitupTest
 */

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

public class PipedWriterWitupTest {

    public static void main(String[] args) throws Exception {
        testConstructorConnectsPipe();
        testNullReaderThrowsNullPointerException();
        testAlreadyConnectedReaderThrowsIOException();
        testClosedReaderThrowsIOException();
    }

    static void testConstructorConnectsPipe() throws IOException {
        PipedReader reader = new PipedReader();
        PipedWriter writer = new PipedWriter(reader);
        writer.write('x');
        writer.flush();
        int c = reader.read();
        check(c == 'x', "expected 'x' but got " + c);
        writer.close();
        reader.close();
    }

    static void testNullReaderThrowsNullPointerException() {
        try {
            new PipedWriter(null);
            fail("expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        } catch (IOException e) {
            fail("unexpected IOException: " + e);
        }
    }

    static void testAlreadyConnectedReaderThrowsIOException() throws IOException {
        PipedReader reader = new PipedReader();
        PipedWriter first = new PipedWriter(reader);
        try {
            new PipedWriter(reader);
            fail("expected IOException for already connected reader");
        } catch (IOException expected) {
            check("Already connected".equals(expected.getMessage()),
                  "unexpected message: " + expected.getMessage());
        }
        first.close();
    }

    static void testClosedReaderThrowsIOException() throws IOException {
        PipedReader reader = new PipedReader();
        reader.close();
        try {
            new PipedWriter(reader);
            fail("expected IOException for closed reader");
        } catch (IOException expected) {
            check("Pipe closed".equals(expected.getMessage()),
                  "unexpected message: " + expected.getMessage());
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void fail(String message) {
        throw new RuntimeException(message);
    }
}
