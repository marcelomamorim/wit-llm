/*
 * @test
 * @summary WITUP-derived tests for Integer(String) constructor: valid input, NumberFormatException for null, empty, non-numeric and overflow inputs
 * @run main IntegerWitupTest
 */

public class IntegerWitupTest {
    public static void main(String[] args) {
        check(new Integer("123").intValue() == 123, "positive decimal");
        check(new Integer("-45").intValue() == -45, "negative decimal");
        check(new Integer("+7").intValue() == 7, "explicit plus sign");
        check(new Integer("0").intValue() == 0, "zero");
        check(new Integer("2147483647").intValue() == Integer.MAX_VALUE, "max value");
        check(new Integer("-2147483648").intValue() == Integer.MIN_VALUE, "min value");

        expectNullNfe();
        expectNfe("");
        expectNfe("abc");
        expectNfe("12a");
        expectNfe(" 1");
        expectNfe("+");
        expectNfe("-");
        expectNfe("2147483648");
        expectNfe("-2147483649");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void expectNfe(String s) {
        try {
            new Integer(s);
        } catch (NumberFormatException e) {
            return;
        }
        throw new AssertionError("expected NumberFormatException for input: " + s);
    }

    static void expectNullNfe() {
        try {
            new Integer((String) null);
        } catch (NumberFormatException e) {
            return;
        }
        throw new AssertionError("expected NumberFormatException for null input");
    }
}
