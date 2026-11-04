/*
 * @test
 * @summary Test StringCharBuffer.put(int, char) throws ReadOnlyBufferException
 * @run main StringCharBufferWitupTest
 */

import java.nio.CharBuffer;
import java.nio.ReadOnlyBufferException;
import java.nio.StringCharBuffer;

public class StringCharBufferWitupTest {
    public static void main(String[] args) {
        // Create a StringCharBuffer instance via the package-private constructor using a CharSequence
        // Since StringCharBuffer is package-private and no public constructor/factory is available,
        // we create a subclass in the same package to access the constructor for testing.
        TestStringCharBuffer buf = new TestStringCharBuffer("abc", 0, 3);

        // Test that put(int, char) always throws ReadOnlyBufferException
        try {
            buf.put(0, 'x');
            throw new AssertionError("Expected ReadOnlyBufferException not thrown");
        } catch (ReadOnlyBufferException expected) {
            // expected
        }
    }

    // Subclass to access package-private constructor
    static class TestStringCharBuffer extends StringCharBuffer {
        TestStringCharBuffer(CharSequence s, int start, int end) {
            super(s, start, end);
        }
    }
}
