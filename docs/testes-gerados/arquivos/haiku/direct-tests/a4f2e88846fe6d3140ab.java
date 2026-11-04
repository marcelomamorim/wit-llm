/*
 * @test
 * @summary Tests Short.decode(String) value, radix prefix and range behavior
 * @run main ShortWitupTest
 */
public class ShortWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void expectNumberFormatException(String input) {
        try {
            Short.decode(input);
        } catch (NumberFormatException e) {
            return;
        }
        throw new RuntimeException("Expected NumberFormatException for input: " + input);
    }

    public static void main(String[] args) {
        check(Short.decode("127").shortValue() == 127, "decimal 127");
        check(Short.decode("+5").shortValue() == 5, "explicit plus sign");
        check(Short.decode("-5").shortValue() == -5, "negative decimal");
        check(Short.decode("-32768").shortValue() == Short.MIN_VALUE, "MIN_VALUE boundary");
        check(Short.decode("32767").shortValue() == Short.MAX_VALUE, "MAX_VALUE boundary");
        check(Short.decode("0x7F").shortValue() == 127, "hex prefix 0x");
        check(Short.decode("#7f").shortValue() == 127, "hex prefix #");
        check(Short.decode("-0x8000").shortValue() == Short.MIN_VALUE, "negative hex MIN_VALUE");
        check(Short.decode("010").shortValue() == 8, "octal prefix 0");
        check(Short.decode("127").equals(Short.valueOf((short) 127)), "equality with valueOf");

        expectNumberFormatException("32768");
        expectNumberFormatException("-32769");
        expectNumberFormatException("0x8000");
        expectNumberFormatException("abc");
    }
}
