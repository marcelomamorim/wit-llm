/* @test    @summary Test StringCharBuffer.compact() method for read-only buffer behavior    @run main StringCharBufferWitupTest */

import java.nio.CharBuffer;
import java.nio.ReadOnlyBufferException;

public class StringCharBufferWitupTest {

    public static void main(String[] args) {
        testCompactThrowsReadOnlyBufferException();
    }

    public static void testCompactThrowsReadOnlyBufferException() {
        String sampleString = "Sample";
        CharBuffer buffer = new java.nio.StringCharBuffer(sampleString, 0, sampleString.length());
        try {
            buffer.compact();
            throw new AssertionError("Expected ReadOnlyBufferException was not thrown");
        } catch (ReadOnlyBufferException e) {
            // Test passes if exception is caught
        }
    }
}
