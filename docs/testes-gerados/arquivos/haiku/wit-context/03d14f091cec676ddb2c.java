/*
 * @test
 * @summary Verify StringBuffer.codePointAt returns code points and throws StringIndexOutOfBoundsException for invalid indices
 * @run main StringBufferWitupTest
 */

public class StringBufferWitupTest {
    static int failures = 0;

    public static void main(String[] args) {
        StringBuffer sb = new StringBuffer("abc");
        check(sb.codePointAt(0) == 'a', "index 0 should return 'a'");
        check(sb.codePointAt(2) == 'c', "index 2 should return 'c'");

        expectOutOfBounds(sb, -1);
        expectOutOfBounds(sb, 3);
        expectOutOfBounds(sb, Integer.MIN_VALUE);
        expectOutOfBounds(sb, Integer.MAX_VALUE);

        String emoji = new String(Character.toChars(0x1F600));
        StringBuffer s2 = new StringBuffer("a" + emoji);
        check(s2.codePointAt(1) == 0x1F600, "supplementary code point should be returned whole");

        expectOutOfBounds(new StringBuffer(), 0);

        if (failures > 0) {
            throw new RuntimeException(failures + " failure(s)");
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            failures++;
            System.err.println("FAIL: " + message);
        }
    }

    static void expectOutOfBounds(StringBuffer sb, int index) {
        try {
            int cp = sb.codePointAt(index);
            failures++;
            System.err.println("FAIL: no exception for index " + index + ", returned " + cp);
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }
}
