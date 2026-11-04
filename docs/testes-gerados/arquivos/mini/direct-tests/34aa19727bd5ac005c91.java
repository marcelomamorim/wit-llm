/*
 * @test
 * @summary Test java.lang.StringBuffer.charAt(int) method
 * @run main StringBufferWitupTest
 */

public class StringBufferWitupTest {
    public static void main(String[] args) {
        testCharAtValidIndex();
        testCharAtZeroIndex();
        testCharAtLastIndex();
        testCharAtNegativeIndex();
        testCharAtIndexEqualLength();
    }

    private static void testCharAtValidIndex() {
        StringBuffer sb = new StringBuffer("hello");
        char c = sb.charAt(1);
        if (c != 'e') {
            throw new RuntimeException("Expected 'e' at index 1 but got '" + c + "'");
        }
    }

    private static void testCharAtZeroIndex() {
        StringBuffer sb = new StringBuffer("world");
        char c = sb.charAt(0);
        if (c != 'w') {
            throw new RuntimeException("Expected 'w' at index 0 but got '" + c + "'");
        }
    }

    private static void testCharAtLastIndex() {
        StringBuffer sb = new StringBuffer("abc");
        char c = sb.charAt(sb.length() - 1);
        if (c != 'c') {
            throw new RuntimeException("Expected 'c' at last index but got '" + c + "'");
        }
    }

    private static void testCharAtNegativeIndex() {
        StringBuffer sb = new StringBuffer("test");
        try {
            sb.charAt(-1);
            throw new RuntimeException("Expected StringIndexOutOfBoundsException for negative index");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testCharAtIndexEqualLength() {
        StringBuffer sb = new StringBuffer("test");
        try {
            sb.charAt(sb.length());
            throw new RuntimeException("Expected StringIndexOutOfBoundsException for index equal to length");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }
}
