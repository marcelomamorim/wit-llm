/* @test    @summary Test for StringCharBuffer.put(int, char) method to verify ReadOnlyBufferException is thrown.    @run main StringCharBufferWitupTest */

import java.nio.StringCharBuffer;
import java.nio.ReadOnlyBufferException;

public class StringCharBufferWitupTest {

    public static void main(String[] args) {
        testPutThrowsReadOnlyBufferException();
    }

    public static void testPutThrowsReadOnlyBufferException() {
        String sample = "abc";
        StringCharBuffer buffer = new StringCharBuffer(sample, 0, sample.length());
        int testIndex = 1;
        char testChar = 'z';
        try {
            buffer.put(testIndex, testChar);
            System.err.println("FAIL: Expected ReadOnlyBufferException was not thrown.");
            System.exit(1);
        } catch (ReadOnlyBufferException e) {
            // Expected exception, test passes
            System.out.println("PASS: ReadOnlyBufferException thrown as expected.");
        } catch (Throwable t) {
            System.err.println("FAIL: Unexpected exception: " + t);
            System.exit(1);
        }
    }
}
