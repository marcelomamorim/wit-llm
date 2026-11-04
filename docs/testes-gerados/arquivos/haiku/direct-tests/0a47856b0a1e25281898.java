/*
 * @test
 * @summary Behavioral checks for Reader.read(CharBuffer) via Reader.nullReader()
 * @run main ReaderWitupTest
 */

import java.io.IOException;
import java.io.Reader;
import java.nio.CharBuffer;

public class ReaderWitupTest {

    public static void main(String[] args) throws Exception {
        testReturnsEndOfStreamWhenBufferHasRemaining();
        testReturnsZeroWhenNoRemaining();
        testNullTargetThrowsNullPointerException();
        testClosedStreamThrowsIOException();
        testPositionUnchangedAtEndOfStream();
    }

    static void testReturnsEndOfStreamWhenBufferHasRemaining() throws IOException {
        Reader r = Reader.nullReader();
        CharBuffer cb = CharBuffer.allocate(4);
        int n = r.read(cb);
        check(n == -1, "expected -1 but got " + n);
    }

    static void testReturnsZeroWhenNoRemaining() throws IOException {
        Reader r = Reader.nullReader();
        CharBuffer empty = CharBuffer.allocate(0);
        int n1 = r.read(empty);
        check(n1 == 0, "expected 0 for zero-capacity buffer but got " + n1);

        CharBuffer full = CharBuffer.allocate(2);
        full.position(2);
        int n2 = r.read(full);
        check(n2 == 0, "expected 0 for buffer without remaining but got " + n2);
    }

    static void testNullTargetThrowsNullPointerException() {
        Reader r = Reader.nullReader();
        try {
            r.read((CharBuffer) null);
        } catch (NullPointerException expected) {
            return;
        } catch (IOException e) {
            throw new RuntimeException("unexpected IOException", e);
        }
        throw new RuntimeException("expected NullPointerException");
    }

    static void testClosedStreamThrowsIOException() throws IOException {
        Reader r = Reader.nullReader();
        r.close();
        try {
            r.read(CharBuffer.allocate(1));
        } catch (IOException expected) {
            return;
        }
        throw new RuntimeException("expected IOException after close");
    }

    static void testPositionUnchangedAtEndOfStream() throws IOException {
        CharBuffer cb = CharBuffer.allocate(3);
        cb.position(1);
        int n = Reader.nullReader().read(cb);
        check(n == -1, "expected -1 but got " + n);
        check(cb.position() == 1, "position changed to " + cb.position());
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
