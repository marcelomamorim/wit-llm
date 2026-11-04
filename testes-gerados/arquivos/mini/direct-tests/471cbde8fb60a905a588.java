/*
 * @test
 * @summary Test java.lang.StringBuffer.codePointBefore(int) method
 * @run main StringBufferWitupTest
 */

public class StringBufferWitupTest {
    public static void main(String[] args) {
        testCodePointBeforeValid();
        testCodePointBeforeAtZero();
        testCodePointBeforeOutOfBounds();
    }

    private static void testCodePointBeforeValid() {
        StringBuffer sb = new StringBuffer("abc");
        // codePointBefore(3) should return code point of char at index 2 ('c')
        int cp = sb.codePointBefore(3);
        if (cp != 'c') {
            throw new Error("Expected code point 'c' (99) but got " + cp);
        }
        // codePointBefore(2) should return code point of char at index 1 ('b')
        cp = sb.codePointBefore(2);
        if (cp != 'b') {
            throw new Error("Expected code point 'b' (98) but got " + cp);
        }
    }

    private static void testCodePointBeforeAtZero() {
        StringBuffer sb = new StringBuffer("abc");
        try {
            sb.codePointBefore(0);
            throw new Error("Expected StringIndexOutOfBoundsException for index 0");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testCodePointBeforeOutOfBounds() {
        StringBuffer sb = new StringBuffer("abc");
        try {
            sb.codePointBefore(4);
            throw new Error("Expected StringIndexOutOfBoundsException for index 4");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
        try {
            sb.codePointBefore(-1);
            throw new Error("Expected StringIndexOutOfBoundsException for index -1");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }
}
