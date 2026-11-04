/*
 * @test
 * @summary Tests observable behavior of the Byte(String) constructor
 * @run main ByteWitupTest
 */

public class ByteWitupTest {

    public static void main(String[] args) {
        check(new Byte("127").byteValue() == 127, "max value");
        check(new Byte("-128").byteValue() == -128, "min value");
        check(new Byte("+5").byteValue() == 5, "explicit plus sign");
        check(new Byte("0").byteValue() == 0, "zero");
        check(new Byte("-0").byteValue() == 0, "negative zero");
        check(new Byte("42").equals(Byte.valueOf((byte) 42)), "equals valueOf");

        expectNumberFormatException("128");
        expectNumberFormatException("-129");
        expectNumberFormatException("");
        expectNumberFormatException("abc");
        expectNumberFormatException(" 1");
        expectNumberFormatException(null);
    }

    private static void check(boolean condition, String msg) {
        if (!condition) {
            throw new RuntimeException("Failed: " + msg);
        }
    }

    private static void expectNumberFormatException(String input) {
        try {
            new Byte(input);
        } catch (NumberFormatException e) {
            return;
        }
        throw new RuntimeException("Expected NumberFormatException for input: " + input);
    }
}
