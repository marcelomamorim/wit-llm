/*
 * @test
 * @summary Test java.lang.StringBuffer.setLength(int) method behavior
 * @run main java.lang.StringBufferWitupTest
 */

public class StringBufferWitupTest {
    public static void main(String[] args) {
        testSetLengthIncrease();
        testSetLengthDecrease();
        testSetLengthZero();
        testSetLengthNegative();
    }

    private static void testSetLengthIncrease() {
        java.lang.StringBuffer sb = new java.lang.StringBuffer("abc");
        sb.setLength(5);
        // After increasing length, new chars should be '\u0000'
        if (sb.length() != 5) {
            throw new RuntimeException("Length not updated correctly when increased");
        }
        if (sb.charAt(3) != '\u0000' || sb.charAt(4) != '\u0000') {
            throw new RuntimeException("New characters after length increase are not zero chars");
        }
    }

    private static void testSetLengthDecrease() {
        java.lang.StringBuffer sb = new java.lang.StringBuffer("abcde");
        sb.setLength(3);
        if (sb.length() != 3) {
            throw new RuntimeException("Length not updated correctly when decreased");
        }
        if (sb.toString().equals("abcde")) {
            throw new RuntimeException("Content not truncated correctly when length decreased");
        }
        if (!sb.toString().equals("abc")) {
            throw new RuntimeException("Content incorrect after length decreased");
        }
    }

    private static void testSetLengthZero() {
        java.lang.StringBuffer sb = new java.lang.StringBuffer("abc");
        sb.setLength(0);
        if (sb.length() != 0) {
            throw new RuntimeException("Length not zero after setLength(0)");
        }
        if (!sb.toString().equals("")) {
            throw new RuntimeException("Content not empty after setLength(0)");
        }
    }

    private static void testSetLengthNegative() {
        java.lang.StringBuffer sb = new java.lang.StringBuffer("abc");
        try {
            sb.setLength(-1);
            throw new RuntimeException("Expected StringIndexOutOfBoundsException for negative length");
        } catch (StringIndexOutOfBoundsException e) {
            // Expected exception
        }
    }
}
