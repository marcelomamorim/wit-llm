/*
 * @test
 * @summary put(char) on a read-only StringCharBuffer must throw ReadOnlyBufferException without changing state
 * @run main StringCharBufferWitupTest
 */

import java.nio.CharBuffer;
import java.nio.ReadOnlyBufferException;

public class StringCharBufferWitupTest {

    public static void main(String[] args) {
        CharBuffer cb = CharBuffer.wrap("abc");
        if (!cb.isReadOnly()) {
            throw new RuntimeException("wrapped CharSequence buffer must be read-only");
        }

        int posBefore = cb.position();
        int limitBefore = cb.limit();
        boolean thrown = false;
        try {
            cb.put('x');
        } catch (ReadOnlyBufferException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("put(char) did not throw ReadOnlyBufferException");
        }
        if (cb.position() != posBefore || cb.limit() != limitBefore) {
            throw new RuntimeException("put(char) changed position or limit");
        }
        if (cb.get(0) != 'a' || cb.get(1) != 'b' || cb.get(2) != 'c') {
            throw new RuntimeException("content changed after rejected put(char)");
        }

        CharBuffer dup = CharBuffer.wrap("xyz").duplicate();
        thrown = false;
        try {
            dup.put('q');
        } catch (ReadOnlyBufferException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("put(char) on duplicate did not throw ReadOnlyBufferException");
        }
    }
}
