/*
 * @test
 * @summary Verifies observable charAt behavior for Latin1-backed strings, which routes to StringLatin1.charAt
 * @run main StringLatin1WitupTest
 */

import java.util.Arrays;

public class StringLatin1WitupTest {

    public static void main(String[] args) {
        String ascii = "abc";
        check(ascii.charAt(0) == 'a');
        check(ascii.charAt(1) == 'b');
        check(ascii.charAt(2) == 'c');

        String latin1 = "\u00e9\u00ff\u0080";
        check(latin1.charAt(0) == '\u00e9');
        check(latin1.charAt(1) == '\u00ff');
        check(latin1.charAt(2) == '\u0080');
        check(latin1.charAt(2) == (char) 0x80);

        expectOutOfBounds(ascii, -1);
        expectOutOfBounds(ascii, 3);
        expectOutOfBounds(latin1, -1);
        expectOutOfBounds(latin1, 3);
        expectOutOfBounds("", 0);

        String sample = "hello";
        char[] expected = {'h', 'e', 'l', 'l', 'o'};
        char[] actual = new char[sample.length()];
        for (int i = 0; i < sample.length(); i++) {
            actual[i] = sample.charAt(i);
        }
        check(Arrays.equals(expected, actual));
    }

    private static void expectOutOfBounds(String s, int index) {
        try {
            char c = s.charAt(index);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for index " + index + " but got " + c);
        } catch (StringIndexOutOfBoundsException expected) {
            // expected
        }
    }

    private static void check(boolean condition) {
        if (!condition) {
            throw new AssertionError("Check failed");
        }
    }
}
