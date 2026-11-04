/*
 * @test
 * @summary Test String.codePointBefore(int) for valid and invalid indices
 * @run main StringWitupTest
 */

public class StringWitupTest {
    public static void main(String[] args) {
        testCodePointBeforeValid();
        testCodePointBeforeInvalid();
    }

    private static void testCodePointBeforeValid() {
        String s = "abc";
        // index = 1, codePointBefore should return code point of char at index 0 ('a' = 97)
        int cp = s.codePointBefore(1);
        if (cp != 'a') {
            throw new AssertionError("Expected codePointBefore(1) to be 'a' (97), got: " + cp);
        }

        // index = length, codePointBefore should return code point of last char ('c' = 99)
        cp = s.codePointBefore(s.length());
        if (cp != 'c') {
            throw new AssertionError("Expected codePointBefore(length) to be 'c' (99), got: " + cp);
        }
    }

    private static void testCodePointBeforeInvalid() {
        String s = "abc";
        // index = 0 is invalid, should throw StringIndexOutOfBoundsException
        try {
            s.codePointBefore(0);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for index 0");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }

        // index = -1 is invalid, should throw StringIndexOutOfBoundsException
        try {
            s.codePointBefore(-1);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for index -1");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }

        // index = length + 1 is invalid, should throw StringIndexOutOfBoundsException
        try {
            s.codePointBefore(s.length() + 1);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for index length+1");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }
}
