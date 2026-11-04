/*
 * @test
 * @summary Witup-derived tests for Integer.valueOf(String)
 * @run main IntegerWitupTest
 */

public class IntegerWitupTest {
    public static void main(String[] args) {
        expectNFE(null);
        expectNFE("");
        expectNFE("-");
        expectNFE("abc");
        expectNFE(" 1");
        expectNFE("0x10");
        expectNFE("2147483648");
        expectNFE("-2147483649");

        check(Integer.valueOf("42").intValue() == 42, "42");
        check(Integer.valueOf("-7").intValue() == -7, "-7");
        check(Integer.valueOf("+5").intValue() == 5, "+5");
        check(Integer.valueOf("0").intValue() == 0, "0");
        check(Integer.valueOf("2147483647").intValue() == Integer.MAX_VALUE, "MAX_VALUE");
        check(Integer.valueOf("-2147483648").intValue() == Integer.MIN_VALUE, "MIN_VALUE");
    }

    static void expectNFE(String s) {
        try {
            Integer.valueOf(s);
        } catch (NumberFormatException e) {
            return;
        }
        throw new AssertionError("expected NumberFormatException for: " + s);
    }

    static void check(boolean condition, String msg) {
        if (!condition) {
            throw new AssertionError(msg);
        }
    }
}
