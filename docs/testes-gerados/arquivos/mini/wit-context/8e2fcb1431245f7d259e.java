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
        // Create a StringCharBuffer instance via the package-private constructor using a CharSequence
        // Since StringCharBuffer is package-private and no public constructor/factory is available,
        // we create a subclass in the same package to access the constructor.
        // However, as we cannot declare package java.nio here, we simulate by reflection is forbidden,
        // so we test the put(char) method behavior by subclassing CharBuffer and overriding put to call StringCharBuffer.put.
        // Instead, we test put(char) by creating a StringCharBuffer via a helper inner class in the same file.

        // Helper subclass to access package-private constructor
        class TestStringCharBuffer extends StringCharBuffer {
            TestStringCharBuffer(CharSequence s, int start, int end) {
                super(s, start, end);
            }
        }

        CharSequence seq = "abc";
        TestStringCharBuffer buf = new TestStringCharBuffer(seq, 0, seq.length());

        // Confirm isReadOnly returns true
        if (!buf.isReadOnly()) {
            throw new AssertionError("Expected buffer to be read-only");
        }

        // Test that put(char) throws ReadOnlyBufferException
        try {
            buf.put('x');
            throw new AssertionError("Expected ReadOnlyBufferException not thrown");
        } catch (ReadOnlyBufferException expected) {
            // Expected exception
        }

        // Test that put(int, char) also throws ReadOnlyBufferException
        try {
            buf.put(0, 'x');
            throw new AssertionError("Expected ReadOnlyBufferException not thrown");
        } catch (ReadOnlyBufferException expected) {
            // Expected exception
        }

        // Test that compact() throws ReadOnlyBufferException
        try {
            buf.compact();
            throw new AssertionError("Expected ReadOnlyBufferException not thrown");
        } catch (ReadOnlyBufferException expected) {
            // Expected exception
        }
    }
}
