/*
 * @test
 * @summary Witup-derived tests for the Byte(String) constructor
 * @run main ByteWitupTest
 */

public class ByteWitupTest {

    public static void main(String[] args) {
        checkValue("42", 42);
        checkValue("-128", -128);
        checkValue("127", 127);
        checkValue("+5", 5);
        checkValue("0", 0);

        checkInvalid(null);
        checkInvalid("");
        checkInvalid("128");
        checkInvalid("-129");
        checkInvalid("abc");
        checkInvalid("1 ");
        checkInvalid("+");
    }

    static void checkValue(String s, int expected) {
        Byte b = new Byte(s);
        if (b.byteValue() != expected) {
            throw new RuntimeException("Expected " + expected + " for input: " + s
                    + " but got " + b.byteValue());
        }
    }

    static void checkInvalid(String s) {
        try {
            new Byte(s);
        } catch (NumberFormatException e) {
            return;
        }
        throw new RuntimeException("Expected NumberFormatException for input: " + s);
    }
}
