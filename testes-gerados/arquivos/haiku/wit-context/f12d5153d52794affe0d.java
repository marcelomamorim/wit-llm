/*
 * @test
 * @summary Verifies that put(int, char) on a read-only CharBuffer backed by a String always throws ReadOnlyBufferException
 * @run main StringCharBufferWitupTest
 */

import java.nio.CharBuffer;
import java.nio.ReadOnlyBufferException;

public class StringCharBufferWitupTest {
    public static void main(String[] args) {
        CharBuffer cb = CharBuffer.wrap("abc");
        if (!cb.isReadOnly()) {
            throw new RuntimeException("expected read-only buffer");
        }
        if (cb.get(0) != 'a') {
            throw new RuntimeException("unexpected content at index 0");
        }
        expectReadOnly(cb, 0);
        expectReadOnly(cb, 2);
        expectReadOnly(cb, 3);
        expectReadOnly(cb, -1);
        if (cb.get(0) != 'a') {
            throw new RuntimeException("content changed after rejected put");
        }
    }

    static void expectReadOnly(CharBuffer cb, int index) {
        try {
            cb.put(index, 'x');
        } catch (ReadOnlyBufferException e) {
            return;
        }
        throw new RuntimeException("no ReadOnlyBufferException for index " + index);
    }
}
