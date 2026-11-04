/*
 * @test
 * @summary Verifies that put(int, char) on a read-only StringCharBuffer always throws ReadOnlyBufferException and leaves the buffer unchanged
 * @run main StringCharBufferWitupTest
 */

import java.nio.CharBuffer;
import java.nio.ReadOnlyBufferException;

public class StringCharBufferWitupTest {

    public static void main(String[] args) {
        CharBuffer cb = CharBuffer.wrap("abc");
        if (!cb.isReadOnly()) {
            throw new RuntimeException("buffer from CharBuffer.wrap(String) should be read-only");
        }

        expectReadOnly(cb, 0, 'x');
        expectReadOnly(cb, 2, 'x');
        expectReadOnly(cb, -1, 'x');
        expectReadOnly(cb, 10, 'x');

        if (cb.get(0) != 'a' || cb.get(1) != 'b' || cb.get(2) != 'c') {
            throw new RuntimeException("content changed after rejected put(int, char)");
        }
        if (cb.position() != 0 || cb.limit() != 3) {
            throw new RuntimeException("position/limit changed after rejected put(int, char)");
        }

        CharBuffer empty = CharBuffer.wrap("");
        expectReadOnly(empty, 0, 'z');
    }

    private static void expectReadOnly(CharBuffer buffer, int index, char c) {
        try {
            buffer.put(index, c);
        } catch (ReadOnlyBufferException e) {
            return;
        }
        throw new RuntimeException("expected ReadOnlyBufferException for index " + index);
    }
}
