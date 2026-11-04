/*
 * @test
 * @summary Verifies Byte.parseByte(String) accepts valid decimal byte strings and rejects out-of-range or malformed input
 * @run main ByteWitupTest
 */

public class ByteWitupTest {

    public static void main(String[] args) {
        check(Byte.parseByte("0") == 0);
        check(Byte.parseByte("5") == 5);
        check(Byte.parseByte("-5") == -5);
        check(Byte.parseByte("+5") == 5);
        check(Byte.parseByte("-0") == 0);
        check(Byte.parseByte("007") == 7);
        check(Byte.parseByte("127") == Byte.MAX_VALUE);
        check(Byte.parseByte("-128") == Byte.MIN_VALUE);

        checkNfe("128");
        checkNfe("-129");
        checkNfe("");
        checkNfe("+");
        checkNfe("-");
        checkNfe("abc");
        checkNfe(" 1");
        checkNfe("1 ");
        checkNfe("1.0");
        checkNfe(null);
    }

    static void check(boolean condition) {
        if (!condition) {
            throw new RuntimeException("assertion failed");
        }
    }

    static void checkNfe(String s) {
        try {
            Byte.parseByte(s);
        } catch (NumberFormatException e) {
            return;
        }
        throw new RuntimeException("expected NumberFormatException for input: " + s);
    }
}
