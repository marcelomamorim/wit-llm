/*
 * @test
 * @summary Tests the public Short(String) constructor for valid inputs and NumberFormatException cases
 * @run main ShortWitupTest
 */

public class ShortWitupTest {

    public static void main(String[] args) {
        checkValue("123", 123);
        checkValue("+5", 5);
        checkValue("-5", -5);
        checkValue("0", 0);
        checkValue("-32768", Short.MIN_VALUE);
        checkValue("32767", Short.MAX_VALUE);

        expectNumberFormatException(null);
        expectNumberFormatException("");
        expectNumberFormatException("32768");
        expectNumberFormatException("-32769");
        expectNumberFormatException("abc");
        expectNumberFormatException("1.5");
        expectNumberFormatException(" 1");
        expectNumberFormatException("-");
    }

    static void checkValue(String s, int expected) {
        Short v = new Short(s);
        if (v.shortValue() != (short) expected) {
            throw new RuntimeException("Unexpected value for \"" + s + "\": " + v.shortValue());
        }
    }

    static void expectNumberFormatException(String s) {
        try {
            new Short(s);
        } catch (NumberFormatException e) {
            return;
        }
        throw new RuntimeException("No NumberFormatException for input: " + s);
    }
}
