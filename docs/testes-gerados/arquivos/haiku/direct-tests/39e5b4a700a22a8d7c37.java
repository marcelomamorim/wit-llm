/*
 * @test
 * @summary StringCharBuffer.compact() must always throw ReadOnlyBufferException and leave the buffer state unchanged
 * @run main StringCharBufferWitupTest
 */

import java.nio.CharBuffer;
import java.nio.ReadOnlyBufferException;

public class StringCharBufferWitupTest {
    public static void main(String[] args) {
        CharBuffer cb = CharBuffer.wrap("abc");
        expectReadOnly(cb);
        if (cb.position() != 0 || cb.limit() != 3 || cb.get(0) != 'a') {
            throw new RuntimeException("buffer state changed after compact()");
        }

        CharBuffer ro = CharBuffer.wrap("xyz").asReadOnlyBuffer();
        expectReadOnly(ro);
        if (!ro.isReadOnly()) {
            throw new RuntimeException("buffer should be read-only");
        }
    }

    static void expectReadOnly(CharBuffer b) {
        try {
            b.compact();
        } catch (ReadOnlyBufferException e) {
            return;
        }
        throw new RuntimeException("expected ReadOnlyBufferException");
    }
}
