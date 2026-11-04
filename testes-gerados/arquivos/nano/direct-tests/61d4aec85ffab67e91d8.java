/* @test    @summary Test StringBuffer.append(char[]) method for correct appending of character arrays.    @run main StringBufferWitupTest */

import java.lang.StringBuffer;

public class StringBufferWitupTest {

    public static void main(String[] args) {
        testAppendCharArray();
    }

    public static void testAppendCharArray() {
        StringBuffer sb = new StringBuffer();
        char[] chars = {'H', 'e', 'l', 'l', 'o'};
        sb.append(chars);
        String result = sb.toString();
        if (!"Hello".equals(result)) {
            throw new AssertionError("Expected 'Hello', but got: " + result);
        }
    }
}
