/*
 * @test
 * @summary Test java.io.Reader.read(CharBuffer) method behavior
 * @run main ReaderWitupTest
 */

import java.io.Reader;
import java.io.IOException;
import java.nio.CharBuffer;
import java.util.Objects;

public class ReaderWitupTest {

    public static void main(String[] args) throws Exception {
        testReadNullBuffer();
        testReadEmptyBuffer();
        testReadNonEmptyBuffer();
    }

    static void testReadNullBuffer() {
        Reader r = Reader.nullReader();
        try {
            r.read(null);
            throw new RuntimeException("Expected NullPointerException");
        } catch (NullPointerException npe) {
            // expected
        } catch (IOException ioe) {
            throw new RuntimeException("Unexpected IOException", ioe);
        }
    }

    static void testReadEmptyBuffer() throws IOException {
        Reader r = Reader.nullReader();
        CharBuffer buf = CharBuffer.allocate(0);
        int result = r.read(buf);
        if (result != 0) {
            throw new RuntimeException("Expected 0 for empty buffer, got " + result);
        }
    }

    static void testReadNonEmptyBuffer() throws IOException {
        Reader r = Reader.nullReader();
        CharBuffer buf = CharBuffer.allocate(10);
        // position at 0, limit 10, so hasRemaining() == true
        int result = r.read(buf);
        if (result != -1) {
            throw new RuntimeException("Expected -1 for non-empty buffer, got " + result);
        }
    }
}
