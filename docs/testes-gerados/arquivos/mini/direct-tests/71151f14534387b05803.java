/*
 * @test
 * @summary Test String constructor with int[] codePoints, offset, and count
 * @run main StringWitupTest
 */

public class StringWitupTest {
    public static void main(String[] args) {
        testEmptyArray();
        testLatin1String();
        testUtf16String();
        testOffsetCountBounds();
    }

    private static void testEmptyArray() {
        int[] empty = new int[0];
        String s = new String(empty, 0, 0);
        if (!s.equals("")) {
            throw new AssertionError("Expected empty string for empty codePoints array");
        }
    }

    private static void testLatin1String() {
        // code points all in Latin1 range
        int[] codePoints = {65, 66, 67, 68}; // 'A','B','C','D'
        String s = new String(codePoints, 0, codePoints.length);
        if (!s.equals("ABCD")) {
            throw new AssertionError("Expected 'ABCD' but got: " + s);
        }
    }

    private static void testUtf16String() {
        // code points including supplementary character (outside Latin1)
        int[] codePoints = {0x0041, 0x1F600, 0x0042}; // 'A', 😀, 'B'
        String s = new String(codePoints, 0, codePoints.length);
        // The string should contain 4 chars: 'A' + surrogate pair for 😀 + 'B'
        if (s.length() != 4) {
            throw new AssertionError("Expected length 4 but got: " + s.length());
        }
        if (s.charAt(0) != 'A' || s.charAt(3) != 'B') {
            throw new AssertionError("Unexpected characters at start or end");
        }
        // Check that the middle two chars form a surrogate pair
        char high = s.charAt(1);
        char low = s.charAt(2);
        if (!Character.isHighSurrogate(high) || !Character.isLowSurrogate(low)) {
            throw new AssertionError("Expected surrogate pair for supplementary character");
        }
    }

    private static void testOffsetCountBounds() {
        int[] codePoints = {65, 66, 67};
        // offset + count > length should throw IndexOutOfBoundsException
        try {
            new String(codePoints, 1, 3);
            throw new AssertionError("Expected IndexOutOfBoundsException for offset+count > length");
        } catch (IndexOutOfBoundsException expected) {
        }
        // negative offset
        try {
            new String(codePoints, -1, 1);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative offset");
        } catch (IndexOutOfBoundsException expected) {
        }
        // negative count
        try {
            new String(codePoints, 0, -1);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative count");
        } catch (IndexOutOfBoundsException expected) {
        }
    }
}
