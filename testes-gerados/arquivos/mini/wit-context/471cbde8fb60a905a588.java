/*
 * @test
 * @summary Test StringBuffer.codePointBefore(int) for valid and invalid indices
 * @run main StringBufferWitupTest
 */

public class StringBufferWitupTest {
    public static void main(String[] args) {
        testCodePointBeforeValid();
        testCodePointBeforeInvalid();
    }

    private static void testCodePointBeforeValid() {
        java.lang.StringBuffer sb = new java.lang.StringBuffer("abc");
        // codePointBefore(1) should return code point before index 1, i.e. char at 0
        int cp = sb.codePointBefore(1);
        if (cp != 'a') {
            throw new AssertionError("Expected code point 'a' before index 1, got: " + cp);
        }
        // codePointBefore(2) should return code point before index 2, i.e. char at 1
        cp = sb.codePointBefore(2);
        if (cp != 'b') {
            throw new AssertionError("Expected code point 'b' before index 2, got: " + cp);
        }
    }

    private static void testCodePointBeforeInvalid() {
        java.lang.StringBuffer sb = new java.lang.StringBuffer("abc");
        // According to WIT expath and source, codePointBefore throws StringIndexOutOfBoundsException
        // if index < 1 or count - index <= -1
        // Test index 0 (less than 1)
        try {
            sb.codePointBefore(0);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for index 0");
        } catch (StringIndexOutOfBoundsException expected) {
        }
        // Test index negative
        try {
            sb.codePointBefore(-1);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for index -1");
        } catch (StringIndexOutOfBoundsException expected) {
        }
        // Test index greater than length (count is 3)
        try {
            sb.codePointBefore(4);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for index 4");
        } catch (StringIndexOutOfBoundsException expected) {
        }
    }
}
