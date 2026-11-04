/*
 * @test
 * @summary Basic behavior of PipedReader(int pipeSize) constructor observable through public read/write API
 * @run main PipedReaderWitupTest
 */

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

public class PipedReaderWitupTest {

    public static void main(String[] args) throws Exception {
        testSingleCharPipeRoundTrip();
        testNonPositivePipeSizeRejected();
        testEofAfterWriterClose();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testSingleCharPipeRoundTrip() throws IOException {
        PipedReader reader = new PipedReader(1);
        PipedWriter writer = new PipedWriter();
        writer.connect(reader);
        writer.write('a');
        check(reader.read() == 'a', "expected 'a' from pipe of size 1");
        writer.write('b');
        check(reader.read() == 'b', "expected 'b' from pipe of size 1 after reuse");
        writer.close();
        reader.close();
    }

    static void testNonPositivePipeSizeRejected() {
        boolean thrown = false;
        try {
            new PipedReader(0);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "expected IllegalArgumentException for pipeSize 0");
    }

    static void testEofAfterWriterClose() throws IOException {
        PipedReader reader = new PipedReader(4);
        PipedWriter writer = new PipedWriter();
        writer.connect(reader);
        writer.write('z');
        writer.close();
        check(reader.read() == 'z', "expected buffered 'z' before EOF");
        check(reader.read() == -1, "expected -1 (EOF) after writer closed and buffer drained");
        reader.close();
    }
}
