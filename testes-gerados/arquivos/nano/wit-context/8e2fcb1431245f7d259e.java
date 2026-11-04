/* @test    @summary Test for StringCharBuffer.put(char c) method to verify ReadOnlyBufferException is thrown as expected.    @run main StringCharBufferWitupTest */

import java.nio.StringCharBuffer;

public class StringCharBufferWitupTest {

    public static void main(String[] args) {
        testPutThrowsReadOnlyBufferException();
    }

    public static void testPutThrowsReadOnlyBufferException() {
        StringCharBuffer buffer = new StringCharBuffer("test", 0, 4);
        try {
            buffer.put('a');
            System.out.println("FAIL: Expected ReadOnlyBufferException was not thrown.");
        } catch (ReadOnlyBufferException e) {
            System.out.println("PASS: ReadOnlyBufferException was thrown as expected.");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception type: " + e);
        }
    }
}
