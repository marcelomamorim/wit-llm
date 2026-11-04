/* @test    @summary Test for StringBuffer.setLength(int) method behavior    @run main StringBufferWitupTest */

import java.lang.StringBuffer;

public class StringBufferWitupTest {

    public static void main(String[] args) {
        testSetLengthIncrease();
        testSetLengthDecrease();
        testSetLengthZero();
    }

    static void testSetLengthIncrease() {
        StringBuffer sb = new StringBuffer("abc");
        sb.setLength(5);
        String result = sb.toString();
        if (!"abc\u0000\u0000".equals(result)) {
            throw new AssertionError("Expected 'abc\u0000\u0000', but got: '" + result + "'");
        }
    }

    static void testSetLengthDecrease() {
        StringBuffer sb = new StringBuffer("abcdef");
        sb.setLength(3);
        String result = sb.toString();
        if (!"abc".equals(result)) {
            throw new AssertionError("Expected 'abc', but got: '" + result + "'");
        }
    }

    static void testSetLengthZero() {
        StringBuffer sb = new StringBuffer("test");
        sb.setLength(0);
        String result = sb.toString();
        if (!"".equals(result)) {
            throw new AssertionError("Expected empty string, but got: '" + result + "'");
        }
    }
}
