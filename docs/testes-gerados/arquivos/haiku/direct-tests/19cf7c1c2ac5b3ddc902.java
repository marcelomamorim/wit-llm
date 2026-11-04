/*
 * @test
 * @summary Tests observable behavior of Integer.parseInt(String)
 * @run main IntegerWitupTest
 */

public class IntegerWitupTest {
    public static void main(String[] args) {
        check(Integer.parseInt("0") == 0, "zero");
        check(Integer.parseInt("123") == 123, "positive");
        check(Integer.parseInt("-45") == -45, "negative");
        check(Integer.parseInt("+7") == 7, "explicit plus sign");
        check(Integer.parseInt("-0") == 0, "negative zero");
        check(Integer.parseInt("2147483647") == Integer.MAX_VALUE, "MAX_VALUE");
        check(Integer.parseInt("-2147483648") == Integer.MIN_VALUE, "MIN_VALUE");
        check(Integer.parseInt("007") == 7, "leading zeros");

        check(Integer.parseInt("42") == Integer.parseInt("42", 10), "consistent with radix 10");

        expectNumberFormatException(null);
        expectNumberFormatException("");
        expectNumberFormatException("+");
        expectNumberFormatException("-");
        expectNumberFormatException("abc");
        expectNumberFormatException(" 1");
        expectNumberFormatException("1 ");
        expectNumberFormatException("1.0");
        expectNumberFormatException("2147483648");
        expectNumberFormatException("-2147483649");
    }

    static void check(boolean condition, String label) {
        if (!condition) {
            throw new RuntimeException("Check failed: " + label);
        }
    }

    static void expectNumberFormatException(String input) {
        try {
            Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return;
        }
        throw new RuntimeException("NumberFormatException expected for input: " + input);
    }
}
