/*
 * @test
 * @summary Witup-derived tests for Byte.valueOf(String)
 * @run main ByteWitupTest
 */

public class ByteWitupTest {

    public static void main(String[] args) {
        checkValue("0", 0);
        checkValue("5", 5);
        checkValue("-5", -5);
        checkValue("+5", 5);
        checkValue("-0", 0);
        checkValue("127", 127);
        checkValue("-128", -128);

        expectNumberFormatException(null);
        expectNumberFormatException("");
        expectNumberFormatException(" 1");
        expectNumberFormatException("1 ");
        expectNumberFormatException("1a");
        expectNumberFormatException("128");
        expectNumberFormatException("-129");
        expectNumberFormatException("+");
        expectNumberFormatException("-");
    }

    static void checkValue(String s, int expected) {
        Byte b = Byte.valueOf(s);
        if (b == null) {
            throw new RuntimeException("null result for: " + s);
        }
        if (b.byteValue() != (byte) expected) {
            throw new RuntimeException("unexpected value for " + s + ": " + b.byteValue());
        }
    }

    static void expectNumberFormatException(String s) {
        try {
            Byte.valueOf(s);
        } catch (NumberFormatException e) {
            return;
        }
        throw new RuntimeException("expected NumberFormatException for: " + s);
    }
}
