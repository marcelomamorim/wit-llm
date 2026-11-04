/*
 * @test
 * @summary Test String.codePointBefore(int index) for various scenarios
 * @run main StringWitupTest
 */

import java.lang.String;

public class StringWitupTest {

    public static void main(String[] args) {
        testCodePointBeforeBasic();
        testCodePointBeforeInvalidIndex();
        testCodePointBeforeSurrogatePair();
    }

    static void testCodePointBeforeBasic() {
        String s = "abc";
        int index = 2; // position after 'b'
        int result = s.codePointBefore(index);
        // 'b' is at index 1, code point is 98
        if (result != 98) {
            throw new AssertionError("Expected 98, but got " + result);
        }
    }

    static void testCodePointBeforeInvalidIndex() {
        String s = "abc";
        try {
            s.codePointBefore(0);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for index 0");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
        try {
            s.codePointBefore(4);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for index 4");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testCodePointBeforeSurrogatePair() {
        // U+1F600 GRINNING FACE, surrogate pair
        String s = new String(new char[] { '\uD83D', '\uDE00' });
        // index after the surrogate pair
        int index = 2;
        int result = s.codePointBefore(index);
        // Should return the code point U+1F600 (128512)
        if (result != 0x1F600) {
            throw new AssertionError("Expected 128512, but got " + result);
        }
    }
}
