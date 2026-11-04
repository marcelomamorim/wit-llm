/*
 * @test
 * @summary Tests String(byte[], int, int) decoding and bounds checking
 * @run main StringWitupTest
 */

public class StringWitupTest {

    public static void main(String[] args) {
        byte[] ascii = {'a', 'b', 'c', 'd'};

        check("bcd".equals(new String(ascii, 1, 3)), "offset 1 length 3 should yield bcd");
        check("".equals(new String(ascii, 2, 0)), "zero length should yield empty string");
        check("abcd".equals(new String(ascii, 0, ascii.length)), "full range should yield abcd");
        check("a".equals(new String(ascii, 0, 1)), "prefix of length 1 should yield a");
        check("d".equals(new String(ascii, 3, 1)), "last byte should yield d");
        check(new String(ascii, 4, 0).isEmpty(), "offset at end with zero length should be empty");

        expectSIOOBE(ascii, -1, 2, "negative offset");
        expectSIOOBE(ascii, 0, -1, "negative length");
        expectSIOOBE(ascii, 3, 2, "offset plus length beyond array");
        expectSIOOBE(ascii, 5, 0, "offset past end of array");

        boolean npe = false;
        try {
            new String((byte[]) null, 0, 0);
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "null byte array should throw NullPointerException");
    }

    private static void expectSIOOBE(byte[] bytes, int offset, int length, String what) {
        boolean thrown = false;
        try {
            new String(bytes, offset, length);
        } catch (StringIndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "expected StringIndexOutOfBoundsException for " + what);
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
