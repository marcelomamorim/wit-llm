/*
 * @test
 * @summary Witup-generated tests for Integer.parseInt(String)
 * @run main IntegerWitupTest
 */

public class IntegerWitupTest {
    public static void main(String[] args) {
        check(Integer.parseInt("42") == 42, "parseInt(\"42\") should be 42");
        check(Integer.parseInt("-42") == -42, "parseInt(\"-42\") should be -42");
        check(Integer.parseInt("+7") == 7, "parseInt(\"+7\") should be 7");
        check(Integer.parseInt("0") == 0, "parseInt(\"0\") should be 0");
        check(Integer.parseInt("2147483647") == Integer.MAX_VALUE, "MAX_VALUE boundary");
        check(Integer.parseInt("-2147483648") == Integer.MIN_VALUE, "MIN_VALUE boundary");

        expectNFE(null);
        expectNFE("");
        expectNFE("abc");
        expectNFE(" 1");
        expectNFE("1 ");
        expectNFE("-");
        expectNFE("+");
        expectNFE("2147483648");
        expectNFE("-2147483649");
    }

    static void expectNFE(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return;
        }
        throw new RuntimeException("NumberFormatException expected for input: " + s);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
