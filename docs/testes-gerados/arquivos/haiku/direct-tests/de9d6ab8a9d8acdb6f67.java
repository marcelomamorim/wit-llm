/*
 * @test
 * @summary Verify String.charAt returns the expected char for Latin1 and UTF16 backed strings
 * @run main StringWitupTest
 */

public class StringWitupTest {

    public static void main(String[] args) {
        testLatin1();
        testUtf16();
        testSurrogates();
        testAgainstToCharArray();
    }

    static void testLatin1() {
        String s = new String(new char[] {'a', 'b', 'c', 'X', 'Y', 'Z'});
        check(s.charAt(0) == 'a', "latin1 index 0");
        check(s.charAt(2) == 'c', "latin1 index 2");
        check(s.charAt(3) == 'X', "latin1 index 3");
        check(s.charAt(5) == 'Z', "latin1 last index");
    }

    static void testUtf16() {
        char c0 = (char) 0x4E2D;
        char c1 = (char) 0x6587;
        String s = new String(new char[] {c0, c1, 'a', 'b'});
        check(s.charAt(0) == c0, "utf16 index 0");
        check(s.charAt(1) == c1, "utf16 index 1");
        check(s.charAt(2) == 'a', "utf16 index 2");
        check(s.charAt(3) == 'b', "utf16 last index");
    }

    static void testSurrogates() {
        char high = (char) 0xD83D;
        char low = (char) 0xDE00;
        String s = new String(new char[] {high, low, 'x'});
        check(s.charAt(0) == high, "high surrogate");
        check(s.charAt(1) == low, "low surrogate");
        check(s.charAt(2) == 'x', "char after surrogate pair");
        check(s.codePointAt(0) == 0x1F600, "surrogate pair code point");
    }

    static void testAgainstToCharArray() {
        String[] samples = {
            new String(new char[] {'h', 'e', 'l', 'l', 'o'}),
            new String(new char[] {(char) 0x00E9, 'a', (char) 0x20AC}),
            new String(new char[] {(char) 0x4E2D, 'b', (char) 0xD83D, (char) 0xDE00})
        };
        for (String s : samples) {
            char[] expected = s.toCharArray();
            for (int i = 0; i < expected.length; i++) {
                check(s.charAt(i) == expected[i], "mismatch at index " + i + " in " + s);
            }
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Check failed: " + message);
        }
    }
}
