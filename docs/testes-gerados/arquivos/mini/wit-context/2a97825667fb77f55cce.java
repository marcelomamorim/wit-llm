/*
 * @test
 * @summary Test String.codePointCount(int, int) for valid and invalid indices
 * @run main StringWitupTest
 */

public class StringWitupTest {
    public static void main(String[] args) {
        testValidRanges();
        testInvalidRanges();
    }

    private static void testValidRanges() {
        String latin1 = "abcdef"; // Latin-1 subset
        // codePointCount for Latin1 returns endIndex - beginIndex
        int count = latin1.codePointCount(1, 4);
        if (count != 3) {
            throw new Error("Expected 3 but got " + count);
        }

        String supplementary = "a\uD83D\uDE00b"; // 'a' + 😀 + 'b'
        // codePointCount counts Unicode code points correctly
        int supCount = supplementary.codePointCount(0, supplementary.length());
        if (supCount != 3) {
            throw new Error("Expected 3 code points but got " + supCount);
        }

        // Subrange covering only the surrogate pair
        int supCountSub = supplementary.codePointCount(1, 3);
        if (supCountSub != 1) {
            throw new Error("Expected 1 code point in surrogate pair range but got " + supCountSub);
        }
    }

    private static void testInvalidRanges() {
        String s = "abc";
        // beginIndex < 0
        try {
            s.codePointCount(-1, 2);
            throw new Error("Expected IndexOutOfBoundsException for beginIndex < 0");
        } catch (IndexOutOfBoundsException expected) {
        }

        // beginIndex > endIndex
        try {
            s.codePointCount(2, 1);
            throw new Error("Expected IndexOutOfBoundsException for beginIndex > endIndex");
        } catch (IndexOutOfBoundsException expected) {
        }

        // endIndex > length()
        try {
            s.codePointCount(0, 4);
            throw new Error("Expected IndexOutOfBoundsException for endIndex > length()");
        } catch (IndexOutOfBoundsException expected) {
        }
    }
}
