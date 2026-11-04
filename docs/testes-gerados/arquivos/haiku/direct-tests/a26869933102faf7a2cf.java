/*
 * @test
 * @summary Tests Byte.decode for valid radix prefixes, boundary values, cached instances and out-of-range or malformed input
 * @run main ByteWitupTest
 */

public class ByteWitupTest {

    public static void main(String[] args) {
        checkValue("0", 0);
        checkValue("5", 5);
        checkValue("+5", 5);
        checkValue("-5", -5);
        checkValue("127", 127);
        checkValue("-128", -128);
        checkValue("0x7f", 127);
        checkValue("0X7F", 127);
        checkValue("#7f", 127);
        checkValue("-0x80", -128);
        checkValue("010", 8);
        checkValue("0x0", 0);

        checkCached("5", (byte) 5);
        checkCached("-128", Byte.MIN_VALUE);
        checkCached("127", Byte.MAX_VALUE);

        expectNumberFormatException("128");
        expectNumberFormatException("-129");
        expectNumberFormatException("0x80");
        expectNumberFormatException("#100");
        expectNumberFormatException("256");
        expectNumberFormatException("abc");
        expectNumberFormatException("");
        expectNumberFormatException("1.5");
    }

    private static void checkValue(String input, int expected) {
        Byte result = Byte.decode(input);
        if (result == null) {
            throw new RuntimeException("decode returned null for " + input);
        }
        if (result.intValue() != expected) {
            throw new RuntimeException("decode(" + input + ") = "
                    + result.intValue() + ", expected " + expected);
        }
    }

    private static void checkCached(String input, byte expected) {
        Byte decoded = Byte.decode(input);
        Byte cached = Byte.valueOf(expected);
        if (decoded != cached) {
            throw new RuntimeException("decode(" + input
                    + ") did not return the cached instance");
        }
    }

    private static void expectNumberFormatException(String input) {
        try {
            Byte result = Byte.decode(input);
            throw new RuntimeException("No NumberFormatException for " + input
                    + ", got " + result);
        } catch (NumberFormatException expected) {
            // expected
        }
    }
}
