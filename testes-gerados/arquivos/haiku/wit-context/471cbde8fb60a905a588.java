/*
 * @test
 * @summary Witup-derived checks for StringBuffer.codePointBefore(int) bounds and surrogate handling
 * @run main StringBufferWitupTest
 */
public class StringBufferWitupTest {
    private static int failures = 0;

    public static void main(String[] args) {
        String emoji = new String(Character.toChars(0x1F600));
        StringBuffer sb = new StringBuffer("a" + emoji + "b");

        check(sb.codePointBefore(3) == 0x1F600, "supplementary pair before index 3");
        check(sb.codePointBefore(1) == 'a', "char before index 1");
        check(sb.codePointBefore(4) == 'b', "char before index equal to length");
        check(sb.codePointBefore(2) == emoji.charAt(0), "lone high surrogate before index 2");

        expectSIOOBE(new StringBuffer("abc"), 0);
        expectSIOOBE(new StringBuffer("abc"), -1);
        expectSIOOBE(new StringBuffer("abc"), 4);
        expectSIOOBE(new StringBuffer(), 0);
        expectSIOOBE(new StringBuffer(), 1);

        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            failures++;
            System.err.println("FAILED: " + message);
        }
    }

    private static void expectSIOOBE(StringBuffer sb, int index) {
        try {
            int cp = sb.codePointBefore(index);
            failures++;
            System.err.println("FAILED: no exception for index " + index + " returned " + cp);
        } catch (StringIndexOutOfBoundsException expected) {
            // expected
        }
    }
}
