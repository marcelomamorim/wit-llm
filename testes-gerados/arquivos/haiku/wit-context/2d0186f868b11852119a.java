/*
 * @test
 * @summary Tests for the deprecated String(byte[] ascii, int hibyte) constructor
 * @run main StringWitupTest
 */

public class StringWitupTest {

    @SuppressWarnings("deprecation")
    public static void main(String[] args) {
        testAsciiWithZeroHibyte();
        testHighBitByteWithZeroHibyte();
        testHibyteSetsHighChar();
        testEmptyArray();
        testNullArrayThrowsNPE();
    }

    @SuppressWarnings("deprecation")
    static void testAsciiWithZeroHibyte() {
        byte[] ascii = {'a', 'b', 'c'};
        String s = new String(ascii, 0);
        check("abc".equals(s), "expected \"abc\" but got \"" + s + "\"");
        check(s.length() == 3, "unexpected length " + s.length());
    }

    @SuppressWarnings("deprecation")
    static void testHighBitByteWithZeroHibyte() {
        byte[] data = {(byte) 0xE9};
        String s = new String(data, 0);
        check(s.length() == 1, "unexpected length " + s.length());
        check(s.charAt(0) == (char) 0x00E9, "expected U+00E9 but got " + (int) s.charAt(0));
    }

    @SuppressWarnings("deprecation")
    static void testHibyteSetsHighChar() {
        byte[] data = {0x41};
        String s = new String(data, 1);
        check(s.length() == 1, "unexpected length " + s.length());
        check(s.charAt(0) == (char) 0x0141, "expected U+0141 but got " + (int) s.charAt(0));
    }

    @SuppressWarnings("deprecation")
    static void testEmptyArray() {
        byte[] data = new byte[0];
        String s = new String(data, 0);
        check(s.isEmpty(), "expected empty string but got length " + s.length());
    }

    @SuppressWarnings("deprecation")
    static void testNullArrayThrowsNPE() {
        boolean thrown = false;
        try {
            new String((byte[]) null, 0);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null array");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
