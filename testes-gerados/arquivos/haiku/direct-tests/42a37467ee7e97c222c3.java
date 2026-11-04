/*
 * @test
 * @summary Behavioral tests for Integer.valueOf(String), covering valid parsing, boundaries, sign handling, caching identity and NumberFormatException cases
 * @run main IntegerWitupTest
 */

public class IntegerWitupTest {

    public static void main(String[] args) {
        check(Integer.valueOf("42").intValue() == 42, "42");
        check(Integer.valueOf("-7").intValue() == -7, "-7");
        check(Integer.valueOf("0").intValue() == 0, "0");
        check(Integer.valueOf("-0").intValue() == 0, "-0");
        check(Integer.valueOf("+5").intValue() == 5, "+5");
        check(Integer.valueOf("2147483647").intValue() == Integer.MAX_VALUE, "MAX_VALUE string");
        check(Integer.valueOf("-2147483648").intValue() == Integer.MIN_VALUE, "MIN_VALUE string");

        check(Integer.valueOf("100") == Integer.valueOf(100), "cached identity for 100");
        check(Integer.valueOf("-128") == Integer.valueOf(-128), "cached identity for -128");

        expectNfe("");
        expectNfe("abc");
        expectNfe("12a");
        expectNfe(" 1");
        expectNfe("2147483648");
        expectNfe("-2147483649");
        expectNfe(null);

        System.out.println("PASSED");
    }

    static void check(boolean condition, String what) {
        if (!condition) {
            throw new RuntimeException("Check failed: " + what);
        }
    }

    static void expectNfe(String s) {
        try {
            Integer.valueOf(s);
        } catch (NumberFormatException e) {
            return;
        }
        throw new RuntimeException("Expected NumberFormatException for: " + s);
    }
}
