/*
 * @test
 * @summary Verify ByteArrayOutputStream.toString(int hibyte) maps each byte to a char using hibyte as the high byte
 * @run main ByteArrayOutputStreamWitupTest
 */

public class ByteArrayOutputStreamWitupTest {

    @SuppressWarnings("deprecation")
    public static void main(String[] args) {
        testEmptyStream();
        testLowByteZeroHibyte();
        testHibyteNonZero();
        testHibyteLowEightBitsOnly();
        testHighBytFFMapping();
        testHibyteHighBitSet();
        testToStringDoesNotModifyStream();
        testResetThenToString();
    }

    @SuppressWarnings("deprecation")
    static void testEmptyStream() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        String s = baos.toString(0);
        check(s.length() == 0, "empty stream should yield empty string");
    }

    @SuppressWarnings("deprecation")
    static void testLowByteZeroHibyte() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write('A');
        baos.write('B');
        String s = baos.toString(0);
        check(s.equals("AB"), "hibyte 0 should map bytes to chars directly, got " + s);
    }

    @SuppressWarnings("deprecation")
    static void testHibyteNonZero() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(0x41);
        String s = baos.toString(0x01);
        check(s.length() == 1, "expected one char, got length " + s.length());
        check(s.charAt(0) == (char) 0x141, "expected char 0x141, got " + (int) s.charAt(0));
    }

    @SuppressWarnings("deprecation")
    static void testHibyteLowEightBitsOnly() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write('A');
        String s = baos.toString(0x100);
        check(s.equals("A"), "only low 8 bits of hibyte should matter, got " + s);
    }

    @SuppressWarnings("deprecation")
    static void testHighBytFFMapping() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(0xFF);
        String s = baos.toString(0);
        check(s.length() == 1 && s.charAt(0) == (char) 0xFF,
              "byte 0xFF with hibyte 0 should map to char 0xFF, got " + (int) s.charAt(0));
    }

    @SuppressWarnings("deprecation")
    static void testHibyteHighBitSet() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(0x00);
        String s = baos.toString(0x80);
        check(s.length() == 1 && s.charAt(0) == (char) 0x8000,
              "hibyte 0x80 with byte 0 should map to char 0x8000, got " + (int) s.charAt(0));
    }

    @SuppressWarnings("deprecation")
    static void testToStringDoesNotModifyStream() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write('X');
        String first = baos.toString(0);
        String second = baos.toString(0);
        check(first.equals("X") && second.equals("X"),
              "repeated toString(int) should be stable, got " + first + " and " + second);
    }

    @SuppressWarnings("deprecation")
    static void testResetThenToString() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write('Z');
        baos.reset();
        String s = baos.toString(0);
        check(s.length() == 0, "after reset toString(int) should be empty, got " + s);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
