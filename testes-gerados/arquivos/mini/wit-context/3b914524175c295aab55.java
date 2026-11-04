/*
 * @test
 * @summary Test StringBuffer.substring(int, int) for valid and invalid ranges
 * @run main StringBufferWitupTest
 */

public class StringBufferWitupTest {
    public static void main(String[] args) {
        testValidSubstring();
        testInvalidSubstring();
    }

    private static void testValidSubstring() {
        java.lang.StringBuffer sb = new java.lang.StringBuffer("Hello, world!");
        // Normal substring within bounds
        String sub = sb.substring(0, 5);
        if (!"Hello".equals(sub)) {
            throw new AssertionError("Expected 'Hello' but got '" + sub + "'");
        }
        // substring with start == end returns empty string
        sub = sb.substring(3, 3);
        if (!"".equals(sub)) {
            throw new AssertionError("Expected empty string but got '" + sub + "'");
        }
        // substring entire string
        sub = sb.substring(0, sb.length());
        if (!sb.toString().equals(sub)) {
            throw new AssertionError("Expected full string but got '" + sub + "'");
        }
    }

    private static void testInvalidSubstring() {
        java.lang.StringBuffer sb = new java.lang.StringBuffer("Hello");
        // start < 0
        try {
            sb.substring(-1, 2);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for start < 0");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
        // end < start
        try {
            sb.substring(3, 2);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for end < start");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
        // end > length
        try {
            sb.substring(1, sb.length() + 1);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for end > length");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }
}
