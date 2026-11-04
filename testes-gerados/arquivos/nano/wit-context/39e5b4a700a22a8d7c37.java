/* @test    @summary Test for StringCharBuffer.compact() method to verify ReadOnlyBufferException is thrown.    @run main StringCharBufferWitupTest */

import java.nio.StringCharBuffer;
import java.nio.ReadOnlyBufferException;

public class StringCharBufferWitupTest {

    public static void main(String[] args) {
        testCompactThrowsReadOnlyBufferException();
    }

    public static void testCompactThrowsReadOnlyBufferException() {
        StringCharBuffer buffer = createSampleBuffer();
        try {
            buffer.compact();
            throw new AssertionError("Expected ReadOnlyBufferException was not thrown.");
        } catch (ReadOnlyBufferException e) {
            // Test passes if exception is caught
        }
    }

    private static StringCharBuffer createSampleBuffer() {
        // Creating a StringCharBuffer with a sample string
        String sampleString = "Sample";
        return new StringCharBuffer(sampleString, 0, sampleString.length());
    }
}
