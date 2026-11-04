/*
 * @test
 * @summary Verify that compact() on a read-only CharBuffer backed by a String throws ReadOnlyBufferException and leaves state unchanged
 * @run main StringCharBufferWitupTest
 */
import java.nio.CharBuffer;
import java.nio.ReadOnlyBufferException;

public class StringCharBufferWitupTest {
    public static void main(String[] args) {
        checkCompactThrows(CharBuffer.wrap("abc"), 0);
        checkCompactThrows(CharBuffer.wrap("abc"), 1);
        checkCompactThrows(CharBuffer.wrap("abc"), 3);
        checkCompactThrows(CharBuffer.wrap(""), 0);

        CharBuffer cb = CharBuffer.wrap("xyz");
        if (!cb.isReadOnly()) {
            throw new AssertionError("buffer must be read-only");
        }
        cb.position(2);
        try {
            cb.compact();
            throw new AssertionError("compact() did not throw ReadOnlyBufferException");
        } catch (ReadOnlyBufferException expected) {
        }
        if (cb.position() != 2) {
            throw new AssertionError("position changed after failed compact(): " + cb.position());
        }
        if (cb.limit() != 3) {
            throw new AssertionError("limit changed after failed compact(): " + cb.limit());
        }
        if (cb.get(0) != 'x' || cb.get(2) != 'z') {
            throw new AssertionError("content changed after failed compact()");
        }
    }

    private static void checkCompactThrows(CharBuffer cb, int position) {
        cb.position(position);
        try {
            cb.compact();
        } catch (ReadOnlyBufferException expected) {
            return;
        }
        throw new AssertionError("compact() did not throw at position " + position);
    }
}
