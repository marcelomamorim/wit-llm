/*
 * @test
 * @summary Test java.lang.String substring(int, int) method behavior
 * @run main StringWitupTest
 */

public class StringWitupTest {
    public static void main(String[] args) {
        testSubstringFullRangeReturnsSameInstance();
        testSubstringPartialLatin1();
        testSubstringPartialUTF16();
        testSubstringEmptyString();
        testSubstringSingleChar();
        testSubstringBeginEqualsEnd();
    }

    private static void testSubstringFullRangeReturnsSameInstance() {
        String s = "Hello, world!";
        String sub = s.substring(0, s.length());
        if (sub != s) {
            throw new RuntimeException("substring(0, length) should return same instance");
        }
    }

    private static void testSubstringPartialLatin1() {
        String s = "Hello, world!"; // Latin1 string
        String sub = s.substring(7, 12);
        if (!sub.equals("world")) {
            throw new RuntimeException("substring(7, 12) expected 'world' but got '" + sub + "'");
        }
    }

    private static void testSubstringPartialUTF16() {
        // Use supplementary character to force UTF16 internal representation
        String s = "a\uD83D\uDE00bcd"; // "a😀bcd"
        // substring to get the emoji character only
        String sub = s.substring(1, 3);
        if (!sub.equals("\uD83D\uDE00")) {
            throw new RuntimeException("substring(1, 3) expected emoji but got '" + sub + "'");
        }
    }

    private static void testSubstringEmptyString() {
        String s = "";
        String sub = s.substring(0, 0);
        if (!sub.equals("")) {
            throw new RuntimeException("substring(0, 0) on empty string should return empty string");
        }
    }

    private static void testSubstringSingleChar() {
        String s = "abc";
        String sub = s.substring(1, 2);
        if (!sub.equals("b")) {
            throw new RuntimeException("substring(1, 2) expected 'b' but got '" + sub + "'");
        }
    }

    private static void testSubstringBeginEqualsEnd() {
        String s = "abc";
        String sub = s.substring(2, 2);
        if (!sub.equals("")) {
            throw new RuntimeException("substring(2, 2) expected empty string but got '" + sub + "'");
        }
    }
}
