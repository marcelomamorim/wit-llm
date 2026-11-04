/*
 * @test
 * @summary Tests Reader.read(CharBuffer) on the stream returned by Reader.nullReader()
 * @run main ReaderWitupTest
 */
import java.io.IOException;
import java.io.Reader;
import java.nio.CharBuffer;

public class ReaderWitupTest {

    public static void main(String[] args) throws Exception {
        testNullTargetThrowsNPE();
        testEmptyBufferReturnsZero();
        testBufferWithRemainingReturnsEof();
        testClosedStreamThrowsIOException();
    }

    static void testNullTargetThrowsNPE() {
        Reader r = Reader.nullReader();
        try {
            r.read((CharBuffer) null);
            throw new RuntimeException("Expected NullPointerException for null target");
        } catch (NullPointerException expected) {
        }
    }

    static void testEmptyBufferReturnsZero() throws IOException {
        Reader r = Reader.nullReader();
        CharBuffer cb = CharBuffer.allocate(0);
        int n = r.read(cb);
        if (n != 0) {
            throw new RuntimeException("Expected 0 for buffer without remaining, got " + n);
        }
    }

    static void testBufferWithRemainingReturnsEof() throws IOException {
        Reader r = Reader.nullReader();
        CharBuffer cb = CharBuffer.allocate(4);
        int before = cb.position();
        int n = r.read(cb);
        if (n != -1) {
            throw new RuntimeException("Expected -1 for buffer with remaining, got " + n);
        }
        if (cb.position() != before) {
            throw new RuntimeException("Buffer position changed unexpectedly");
        }
    }

    static void testClosedStreamThrowsIOException() throws IOException {
        Reader r = Reader.nullReader();
        r.close();
        r.close();
        try {
            r.read(CharBuffer.allocate(4));
            throw new RuntimeException("Expected IOException after close");
        } catch (IOException expected) {
        }
    }
}
