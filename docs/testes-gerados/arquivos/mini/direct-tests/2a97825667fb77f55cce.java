/*
 * @test
 * @summary Test String.codePointCount(int, int) method behavior and boundary checks
 * @run main StringWitupTest
 */

public class StringWitupTest {
    public static void main(String[] args) {
        testBasicLatin1();
        testEmptyString();
        testFullString();
        testInvalidIndices();
        testSupplementaryCodePoints();
    }

    private static void testBasicLatin1() {
        String s = "abcdef"; // Latin1 string
        int count = s.codePointCount(1, 4); // 'b','c','d'
        if (count != 3) {
            throw new Error("Expected 3 code points, got " + count);
        }
    }

    private static void testEmptyString() {
        String s = "";
        int count = s.codePointCount(0, 0);
        if (count != 0) {
            throw new Error("Expected 0 code points for empty string, got " + count);
        }
    }

    private static void testFullString() {
        String s = "hello world";
        int count = s.codePointCount(0, s.length());
        if (count != s.length()) {
            throw new Error("Expected code point count equal to length for Latin1 string");
        }
    }

    private static void testInvalidIndices() {
        String s = "abc";
        boolean thrown = false;
        try {
            s.codePointCount(-1, 2);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new Error("Expected IndexOutOfBoundsException for negative beginIndex");
        }

        thrown = false;
        try {
            s.codePointCount(2, 1);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new Error("Expected IndexOutOfBoundsException for beginIndex > endIndex");
        }

        thrown = false;
        try {
            s.codePointCount(0, 4);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new Error("Expected IndexOutOfBoundsException for endIndex > length");
        }
    }

    private static void testSupplementaryCodePoints() {
        // String with supplementary characters (outside BMP)
        // U+1F600 GRINNING FACE (😀) represented by surrogate pair
        String s = "a\uD83D\uDE00b"; // length 4 chars: 'a', high surrogate, low surrogate, 'b'
        // codePointCount from 0 to length should count 3 code points: 'a', 😀, 'b'
        int count = s.codePointCount(0, s.length());
        if (count != 3) {
            throw new Error("Expected 3 code points including supplementary character, got " + count);
        }
        // codePointCount from 1 to 3 should count 1 code point (the emoji)
        count = s.codePointCount(1, 3);
        if (count != 1) {
            throw new Error("Expected 1 code point for surrogate pair range, got " + count);
        }
    }
}
