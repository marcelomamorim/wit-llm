/*
 * @test
 * @summary Tests the public String(byte[] ascii, int hibyte, int offset, int count) constructor
 * @run main StringWitupTest
 */

public class StringWitupTest {

    public static void main(String[] args) {
        testZeroCountReturnsEmpty();
        testLatin1Copy();
        testOffsetSubrange();
        testHibyteProducesUtf16Chars();
        testNegativeByteMapsToHighLatin1();
        testOutOfBoundsRejected();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testZeroCountReturnsEmpty() {
        String s = new String(new byte[]{1, 2, 3}, 0, 0, 0);
        check(s.isEmpty(), "count == 0 must yield empty string");
        String t = new String(new byte[0], 0, 0, 0);
        check(t.isEmpty(), "empty array with count == 0 must yield empty string");
    }

    static void testLatin1Copy() {
        byte[] data = {'a', 'b', 'c'};
        String s = new String(data, 0, 3, 0);
        check(s.equals("abc"), "hibyte 0 must copy ASCII bytes, got " + s);
    }

    static void testOffsetSubrange() {
        byte[] data = {'x', 'a', 'b'};
        String s = new String(data, 0, 1, 2);
        check(s.equals("ab"), "offset 1 count 2 must yield \"ab\", got " + s);
    }

    static void testHibyteProducesUtf16Chars() {
        byte[] data = {0x41, 0x42};
        String s = new String(data, 0x01, 0, 2);
        char[] expected = {(char) 0x141, (char) 0x142};
        check(s.equals(new String(expected)), "hibyte 0x01 must form chars 0x141 0x142, got " + s);
        check(s.length() == 2, "length must equal count");
    }

    static void testNegativeByteMapsToHighLatin1() {
        byte[] data = {(byte) 0xFF};
        String s = new String(data, 0, 0, 1);
        check(s.length() == 1, "length must be 1");
        check(s.charAt(0) == (char) 0xFF, "byte -1 must map to char 0xFF, got " + (int) s.charAt(0));
    }

    static void testOutOfBoundsRejected() {
        byte[] data = {'a', 'b', 'c'};
        if (!throwsIOOBE(data, 0, -1, 0)) {
            throw new RuntimeException("negative offset must be rejected");
        }
        if (!throwsIOOBE(data, 0, 0, -1)) {
            throw new RuntimeException("negative count must be rejected");
        }
        if (!throwsIOOBE(data, 0, 2, 2)) {
            throw new RuntimeException("offset + count beyond length must be rejected");
        }
    }

    static boolean throwsIOOBE(byte[] data, int hibyte, int offset, int count) {
        try {
            new String(data, hibyte, offset, count);
            return false;
        } catch (IndexOutOfBoundsException e) {
            return true;
        }
    }
}
