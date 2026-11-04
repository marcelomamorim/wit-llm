/*
 * @test
 * @summary Tests Short.valueOf(String) for valid, boundary and invalid inputs
 * @run main ShortWitupTest
 */

public class ShortWitupTest {

    public static void main(String[] args) {
        check(Short.valueOf("0").shortValue() == 0, "zero");
        check(Short.valueOf("5").shortValue() == 5, "positive");
        check(Short.valueOf("-5").shortValue() == -5, "negative");
        check(Short.valueOf("+5").shortValue() == 5, "explicit plus sign");
        check(Short.valueOf("32767").shortValue() == Short.MAX_VALUE, "max value");
        check(Short.valueOf("-32768").shortValue() == Short.MIN_VALUE, "min value");

        expectNumberFormatException(null);
        expectNumberFormatException("");
        expectNumberFormatException("32768");
        expectNumberFormatException("-32769");
        expectNumberFormatException("abc");
        expectNumberFormatException(" 1");
        expectNumberFormatException("1.0");
    }

    static void expectNumberFormatException(String s) {
        try {
            Short.valueOf(s);
        } catch (NumberFormatException e) {
            return;
        }
        throw new RuntimeException("NumberFormatException expected for: " + s);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
