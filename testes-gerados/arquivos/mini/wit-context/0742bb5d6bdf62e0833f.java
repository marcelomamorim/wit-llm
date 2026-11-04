/*
 * @test
 * @summary Test StringBuffer.subSequence(int, int) for valid and invalid ranges
 * @run main StringBufferWitupTest
 */

public class StringBufferWitupTest {
    public static void main(String[] args) {
        testValidSubSequence();
        testInvalidSubSequence();
    }

    private static void testValidSubSequence() {
        StringBuffer sb = new StringBuffer("Hello, world!");
        // subSequence should return the substring from start to end
        CharSequence cs = sb.subSequence(0, 5);
        if (!"Hello".contentEquals(cs)) {
            throw new AssertionError("Expected 'Hello' but got '" + cs + "'");
        }
        // subSequence with start == end should return empty sequence
        cs = sb.subSequence(3, 3);
        if (cs.length() != 0) {
            throw new AssertionError("Expected empty sequence but got length " + cs.length());
        }
        // subSequence with full range
        cs = sb.subSequence(0, sb.length());
        if (!sb.toString().contentEquals(cs)) {
            throw new AssertionError("Expected full string but got '" + cs + "'");
        }
    }

    private static void testInvalidSubSequence() {
        StringBuffer sb = new StringBuffer("abc");
        // According to source and WIT, subSequence throws StringIndexOutOfBoundsException if:
        // count < end || end < start || start < 0
        // Here count == length == 3

        // Case: start < 0
        try {
            sb.subSequence(-1, 2);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for start < 0");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }

        // Case: end < start
        try {
            sb.subSequence(2, 1);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for end < start");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }

        // Case: end > count (length)
        try {
            sb.subSequence(1, 5);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for end > length");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }
}
