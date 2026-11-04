/* @test    @summary Test StringCharBuffer.put(int, char) method for ReadOnlyBufferException    @run main StringCharBufferWitupTest */

import java.nio.CharBuffer;
import java.nio.ReadOnlyBufferException;

public class StringCharBufferWitupTest {

    public static void main(String[] args) {
        testPutMethodThrowsReadOnlyBufferException();
    }

    public static void testPutMethodThrowsReadOnlyBufferException() {
        String sampleString = "Hello";
        CharBuffer buffer = new java.nio.StringCharBuffer(sampleString, 0, sampleString.length());
        int testIndex = 2;
        char testChar = 'X';
        try {
            buffer.put(testIndex, testChar);
            // If no exception is thrown, the test fails
            throw new AssertionError("Expected ReadOnlyBufferException was not thrown.");
        } catch (ReadOnlyBufferException e) {
            // Expected exception caught, test passes
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e, e);
        }
    }
}
