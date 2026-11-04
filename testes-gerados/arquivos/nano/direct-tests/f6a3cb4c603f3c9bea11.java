/* @test    @summary Test StringBuffer.append(char[] str, int offset, int len) method for correct appending of character arrays.    @run main StringBufferWitupTest */

import java.lang.StringBuffer;

public class StringBufferWitupTest {

    public static void main(String[] args) {
        testAppendCharArray();
    }

    public static void testAppendCharArray() {
        StringBuffer sb = new StringBuffer();
        char[] chars = {'H', 'e', 'l', 'l', 'o'};
        sb.append(chars, 0, 5);
        assert sb.toString().equals("Hello") : "Test failed: expected 'Hello', got '" + sb.toString() + "'";

        sb = new StringBuffer();
        char[] partialChars = {'W', 'o', 'r', 'l', 'd'};
        sb.append(partialChars, 1, 3);
        assert sb.toString().equals("orl") : "Test failed: expected 'orl', got '" + sb.toString() + "'";

        sb = new StringBuffer();
        sb.append(new char[] {'A', 'B', 'C', 'D', 'E'}, 2, 2);
        assert sb.toString().equals("CD") : "Test failed: expected 'CD', got '" + sb.toString() + "'";
    }
}
