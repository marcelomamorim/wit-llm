/*
 * @test
 * @summary Test String.codePointBefore(int) method behavior
 * @run main StringWitupTest
 */

public class StringWitupTest {
    public static void main(String[] args) {
        testCodePointBeforeBasic();
        testCodePointBeforeLatin1();
        testCodePointBeforeInvalidIndex();
    }

    private static void testCodePointBeforeBasic() {
        String s = "abc";
        // codePointBefore(1) should return code point of char at index 0
        int cp = s.codePointBefore(1);
        if (cp != 'a') {
            throw new Error("Expected code point 'a' (97) but got " + cp);
        }
        // codePointBefore(3) should return code point before index 3, i.e. char at index 2
        cp = s.codePointBefore(3);
        if (cp != 'c') {
            throw new Error("Expected code point 'c' (99) but got " + cp);
        }
    }

    private static void testCodePointBeforeLatin1() {
        // Latin1 string: all chars < 256
        char[] chars = {(char)0x41, (char)0x42, (char)0x43}; // 'A','B','C'
        String s = new String(chars);
        int cp = s.codePointBefore(2);
        if (cp != 'B') {
            throw new Error("Expected code point 'B' (66) but got " + cp);
        }
    }

    private static void testCodePointBeforeInvalidIndex() {
        String s = "abc";
        try {
            s.codePointBefore(0);
            throw new Error("Expected StringIndexOutOfBoundsException for index 0");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
        try {
            s.codePointBefore(4);
            throw new Error("Expected StringIndexOutOfBoundsException for index 4");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
        try {
            s.codePointBefore(-1);
            throw new Error("Expected StringIndexOutOfBoundsException for index -1");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }
}
