/*
 * @test
 * @summary Test StringCharBuffer.put(char) throws ReadOnlyBufferException
 * @run main StringCharBufferWitupTest
 */

import java.nio.CharBuffer;
import java.nio.ReadOnlyBufferException;
import java.nio.StringCharBuffer;

public class StringCharBufferWitupTest {
    public static void main(String[] args) {
        // Create a StringCharBuffer wrapping a simple string
        CharBuffer buf = new StringCharBuffer("abc", 0, 3);

        // Confirm isReadOnly returns true
        if (!buf.isReadOnly()) {
            throw new AssertionError("Buffer should be read-only");
        }

        // Confirm put(char) throws ReadOnlyBufferException
        try {
            buf.put('x');
            throw new AssertionError("Expected ReadOnlyBufferException not thrown");
        } catch (ReadOnlyBufferException expected) {
            // expected
        }
    }
}
