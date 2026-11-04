/*
 * @test
 * @summary Test StringCharBuffer.compact() throws ReadOnlyBufferException
 * @run main StringCharBufferWitupTest
 */

import java.nio.CharBuffer;
import java.nio.ReadOnlyBufferException;
import java.nio.StringCharBuffer;

public class StringCharBufferWitupTest {
    public static void main(String[] args) {
        // Create a StringCharBuffer instance via the package-private constructor using a CharSequence
        // Since StringCharBuffer is package-private and no public constructor/factory is available,
        // we use a subclass in the same package to access the constructor.
        // But as we cannot declare package java.nio here, we simulate by reflection is forbidden,
        // so we test only the compact() method behavior by subclassing CharBuffer directly is not possible.
        // Instead, we test compact() by subclassing StringCharBuffer in the same file (not possible here).
        // Therefore, we create a minimal subclass of CharBuffer that throws ReadOnlyBufferException on compact.
        // But the requirement is to test StringCharBuffer.compact(), so we use a minimal workaround:
        // We create a dummy StringCharBuffer subclass inside this test class to call compact().

        // Inner subclass to access package-private constructor
        class TestStringCharBuffer extends StringCharBuffer {
            TestStringCharBuffer(CharSequence s, int start, int end) {
                super(s, start, end);
            }
        }

        CharSequence cs = "abc";
        TestStringCharBuffer scb = new TestStringCharBuffer(cs, 0, cs.length());

        // Test that compact() always throws ReadOnlyBufferException
        try {
            scb.compact();
            throw new AssertionError("Expected ReadOnlyBufferException not thrown");
        } catch (ReadOnlyBufferException e) {
            // Expected exception
        }

        // Additional sanity check: isReadOnly() returns true
        if (!scb.isReadOnly()) {
            throw new AssertionError("Expected isReadOnly() to return true");
        }

        System.out.println("Test passed.");
    }
}
