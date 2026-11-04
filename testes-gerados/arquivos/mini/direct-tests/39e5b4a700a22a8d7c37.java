/*
 * @test
 * @summary Test StringCharBuffer.compact() throws ReadOnlyBufferException
 * @run main StringCharBufferWitupTest
 */

import java.nio.CharBuffer;
import java.nio.ReadOnlyBufferException;

public class StringCharBufferWitupTest {
    public static void main(String[] args) {
        // Create a StringCharBuffer instance with a simple string
        CharBuffer buf = new StringCharBuffer("abcde", 0, 5);

        // Confirm isReadOnly returns true
        if (!buf.isReadOnly()) {
            throw new AssertionError("Buffer should be read-only");
        }

        // Attempt to call compact() and expect ReadOnlyBufferException
        try {
            buf.compact();
            throw new AssertionError("Expected ReadOnlyBufferException not thrown");
        } catch (ReadOnlyBufferException expected) {
            // Expected exception
        }
    }
}
