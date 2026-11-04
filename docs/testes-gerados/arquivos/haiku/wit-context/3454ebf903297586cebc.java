/*
 * @test
 * @summary Witup-generated tests for Byte.parseByte(String)
 * @run main ByteWitupTest
 */

public class ByteWitupTest {

    public static void main(String[] args) {
        checkValid("0", 0);
        checkValid("127", 127);
        checkValid("-128", -128);
        checkValid("+5", 5);
        checkValid("-7", -7);

        checkInvalid(null);
        checkInvalid("");
        checkInvalid("+");
        checkInvalid("-");
        checkInvalid("128");
        checkInvalid("-129");
        checkInvalid("abc");
        checkInvalid("1.0");
        checkInvalid(" 1");
        checkInvalid("1 ");
    }

    static void checkValid(String s, int expected) {
        byte b = Byte.parseByte(s);
        if (b != expected) {
            throw new RuntimeException("parseByte(" + s + ") = " + b + ", expected " + expected);
        }
    }

    static void checkInvalid(String s) {
        try {
            Byte.parseByte(s);
        } catch (NumberFormatException e) {
            return;
        }
        throw new RuntimeException("NumberFormatException expected for input: " + s);
    }
}
