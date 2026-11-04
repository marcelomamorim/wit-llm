/*
 * @test
 * @summary Verify Integer(String) constructor parses decimal strings and rejects invalid input
 * @run main IntegerWitupTest
 */

public class IntegerWitupTest {

    @SuppressWarnings("deprecation")
    public static void main(String[] args) {
        check(new Integer("42").intValue() == 42, "positive value");
        check(new Integer("-7").intValue() == -7, "negative value");
        check(new Integer("+5").intValue() == 5, "explicit plus sign");
        check(new Integer("0").intValue() == 0, "zero");
        check(new Integer("-0").intValue() == 0, "negative zero");
        check(new Integer("2147483647").intValue() == Integer.MAX_VALUE, "MAX_VALUE bound");
        check(new Integer("-2147483648").intValue() == Integer.MIN_VALUE, "MIN_VALUE bound");
        check(new Integer("0010").intValue() == 10, "leading zeros");

        check(new Integer("123").equals(Integer.valueOf(123)), "equals to valueOf");
        check(new Integer("123").hashCode() == 123, "hashCode equals value");

        check(new Integer("12").intValue() == Integer.parseInt("12"), "consistent with parseInt");

        expectNFE("");
        expectNFE("-");
        expectNFE("+");
        expectNFE("abc");
        expectNFE("12a");
        expectNFE(" 1");
        expectNFE("1 ");
        expectNFE("1.5");
        expectNFE("2147483648");
        expectNFE("-2147483649");
    }

    @SuppressWarnings("deprecation")
    static void expectNFE(String s) {
        try {
            new Integer(s);
        } catch (NumberFormatException e) {
            return;
        }
        throw new AssertionError("expected NumberFormatException for: " + s);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
