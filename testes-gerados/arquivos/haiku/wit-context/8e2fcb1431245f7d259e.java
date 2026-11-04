/*
 * @test
 * @summary Verifies that put(char) on a read-only StringCharBuffer (via CharBuffer.wrap(CharSequence)) throws ReadOnlyBufferException and leaves the buffer state unchanged
 * @run main StringCharBufferWitupTest
 */

import java.nio.CharBuffer;
import java.nio.ReadOnlyBufferException;

public class StringCharBufferWitupTest {

    public static void main(String[] args) {
        CharBuffer cb = CharBuffer.wrap("abc");
        if (!cb.isReadOnly()) {
            throw new AssertionError("wrapped CharSequence buffer must be read-only");
        }
        int pos = cb.position();
        int lim = cb.limit();
        try {
            cb.put('x');
            throw new AssertionError("put(char) did not throw ReadOnlyBufferException");
        } catch (ReadOnlyBufferException expected) {
        }
        if (cb.position() != pos || cb.limit() != lim) {
            throw new AssertionError("put(char) changed position or limit");
        }
        if (!cb.toString().equals("abc")) {
            throw new AssertionError("put(char) changed content");
        }

        CharBuffer cb2 = CharBuffer.wrap("xyz");
        cb2.position(1);
        try {
            cb2.put('q');
            throw new AssertionError("put(char) did not throw at non-zero position");
        } catch (ReadOnlyBufferException expected) {
        }
        if (cb2.position() != 1) {
            throw new AssertionError("position changed after failed put");
        }

        CharBuffer empty = CharBuffer.wrap("");
        try {
            empty.put('e');
            throw new AssertionError("put(char) did not throw on empty buffer");
        } catch (ReadOnlyBufferException expected) {
        }
    }
}
