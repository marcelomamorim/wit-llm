/*
 * @test
 * @summary Test java.lang.StringBuffer.codePointAt(int) method
 * @run main StringBufferWitupTest
 */

public class StringBufferWitupTest {
    public static void main(String[] args) {
        testCodePointAtBasic();
        testCodePointAtSurrogatePair();
        testCodePointAtIndexZero();
    }

    private static void testCodePointAtBasic() {
        StringBuffer sb = new StringBuffer("abcde");
        int cp = sb.codePointAt(2); // 'c' = 99
        if (cp != 'c') {
            throw new RuntimeException("Expected code point 99 but got " + cp);
        }
    }

    private static void testCodePointAtSurrogatePair() {
        // Use a supplementary character (e.g. U+1F600 GRINNING FACE) which is represented by surrogate pair
        StringBuffer sb = new StringBuffer();
        sb.append("\uD83D\uDE00"); // surrogate pair for U+1F600
        int cp = sb.codePointAt(0);
        if (cp != 0x1F600) {
            throw new RuntimeException("Expected code point 0x1F600 but got " + Integer.toHexString(cp));
        }
    }

    private static void testCodePointAtIndexZero() {
        StringBuffer sb = new StringBuffer("A");
        int cp = sb.codePointAt(0);
        if (cp != 'A') {
            throw new RuntimeException("Expected code point 65 but got " + cp);
        }
    }
}