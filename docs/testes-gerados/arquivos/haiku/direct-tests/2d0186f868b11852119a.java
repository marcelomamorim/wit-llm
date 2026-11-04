/*
 * @test
 * @summary Verifies the deprecated String(byte[] ascii, int hibyte) constructor
 * @run main StringWitupTest
 */

public class StringWitupTest {
    public static void main(String[] args) {
        check("abc", new String(new byte[]{'a', 'b', 'c'}, 0), "ascii bytes with hibyte 0");
        check("", new String(new byte[0], 0), "empty array yields empty string");
        check(String.valueOf((char) 0x141), new String(new byte[]{0x41}, 1), "hibyte is placed in high byte");
        check(String.valueOf((char) 0xFF), new String(new byte[]{(byte) 0xFF}, 0), "negative byte is masked to low 8 bits");
        check(String.valueOf((char) 0x7F80), new String(new byte[]{(byte) 0x80}, 0x7F), "high bit byte combined with hibyte");
        if (new String(new byte[]{1, 2, 3, 4}, 0).length() != 4) {
            throw new RuntimeException("length mismatch");
        }
        try {
            new String((byte[]) null, 0);
            throw new RuntimeException("expected NullPointerException for null array");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void check(String expected, String actual, String msg) {
        if (!expected.equals(actual)) {
            throw new RuntimeException(msg + ": expected [" + expected + "] but was [" + actual + "]");
        }
    }
}
