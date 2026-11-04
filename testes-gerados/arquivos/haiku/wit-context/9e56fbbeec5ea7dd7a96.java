/*
 * @test
 * @summary Witup-derived tests for Integer.parseUnsignedInt(String s)
 * @run main IntegerWitupTest
 */

public class IntegerWitupTest {

    public static void main(String[] args) {
        testValidValues();
        testBoundaries();
        testInvalidInputs();
        testNullInputIsRejected();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void expectValue(String input, int expected) {
        int actual = Integer.parseUnsignedInt(input);
        check(actual == expected,
              "parseUnsignedInt(" + input + ") expected " + expected + " but was " + actual);
    }

    static void expectNumberFormatException(String input) {
        try {
            int result = Integer.parseUnsignedInt(input);
            throw new RuntimeException("parseUnsignedInt(" + input
                    + ") should have thrown NumberFormatException but returned " + result);
        } catch (NumberFormatException expected) {
            // expected
        }
    }

    static void testValidValues() {
        expectValue("0", 0);
        expectValue("1", 1);
        expectValue("42", 42);
        expectValue("+5", 5);
        expectValue("0042", 42);
        expectValue("2147483647", Integer.MAX_VALUE);
    }

    static void testBoundaries() {
        expectValue("2147483648", Integer.MIN_VALUE);
        expectValue("4294967295", -1);
        expectValue("+4294967295", -1);
    }

    static void testInvalidInputs() {
        expectNumberFormatException("4294967296");
        expectNumberFormatException("-1");
        expectNumberFormatException("-0");
        expectNumberFormatException("");
        expectNumberFormatException("+");
        expectNumberFormatException("-");
        expectNumberFormatException("abc");
        expectNumberFormatException("1x");
        expectNumberFormatException(" 1");
    }

    static void testNullInputIsRejected() {
        try {
            int result = Integer.parseUnsignedInt(null);
            throw new RuntimeException("parseUnsignedInt(null) should have thrown but returned " + result);
        } catch (NumberFormatException expected) {
            // expected for the String overload in this checkout
        }
    }
}
