/*
 * @test
 * @summary Witup-derived checks for Byte.decode range validation and decoding
 * @run main ByteWitupTest
 */

public class ByteWitupTest {

    public static void main(String[] args) {
        checkDecode("0", 0);
        checkDecode("127", 127);
        checkDecode("-128", -128);
        checkDecode("0x7F", 127);
        checkDecode("-0x80", -128);
        checkDecode("#7f", 127);
        checkDecode("010", 8);
        checkDecode("+5", 5);

        checkOutOfRange("128");
        checkOutOfRange("-129");
        checkOutOfRange("0x80");
        checkOutOfRange("200");
        checkOutOfRange("0xFFFF");

        checkInvalid("abc");
        checkInvalid("");

        if (Byte.decode("1") != Byte.valueOf((byte) 1)) {
            throw new RuntimeException("decode(\"1\") should return cached instance");
        }
    }

    private static void checkDecode(String input, int expected) {
        Byte b = Byte.decode(input);
        if (b.byteValue() != (byte) expected) {
            throw new RuntimeException("decode(" + input + ") expected " + expected
                    + " but was " + b.byteValue());
        }
    }

    private static void checkOutOfRange(String input) {
        try {
            Byte.decode(input);
        } catch (NumberFormatException e) {
            return;
        }
        throw new RuntimeException("decode(" + input + ") should throw NumberFormatException");
    }

    private static void checkInvalid(String input) {
        try {
            Byte.decode(input);
        } catch (NumberFormatException e) {
            return;
        }
        throw new RuntimeException("decode(\"" + input + "\") should throw NumberFormatException");
    }
}
