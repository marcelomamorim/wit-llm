/*
 * @test
 * @summary Test java.lang.String substring(int, int) method for valid and invalid indices
 * @run main StringWitupTest
 */

public class StringWitupTest {
    public static void main(String[] args) {
        testValidSubstring();
        testSubstringBeginIndexEqualsEndIndex();
        testSubstringFullString();
        testSubstringInvalidIndices();
    }

    private static void testValidSubstring() {
        String s = "Hello, World!";
        String sub = s.substring(7, 12);
        if (!"World".equals(sub)) {
            throw new Error("Expected substring 'World' but got: " + sub);
        }
    }

    private static void testSubstringBeginIndexEqualsEndIndex() {
        String s = "abc";
        String sub = s.substring(1, 1);
        if (!"".equals(sub)) {
            throw new Error("Expected empty substring but got: " + sub);
        }
    }

    private static void testSubstringFullString() {
        String s = "test";
        String sub = s.substring(0, s.length());
        if (sub != s) { // should return this
            throw new Error("Expected same string instance when substring is full string");
        }
    }

    private static void testSubstringInvalidIndices() {
        String s = "abc";
        // beginIndex > endIndex
        try {
            s.substring(2, 1);
            throw new Error("Expected StringIndexOutOfBoundsException for beginIndex > endIndex");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
        // endIndex > length
        try {
            s.substring(0, 4);
            throw new Error("Expected StringIndexOutOfBoundsException for endIndex > length");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
        // beginIndex < 0
        try {
            s.substring(-1, 2);
            throw new Error("Expected StringIndexOutOfBoundsException for beginIndex < 0");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }
}
