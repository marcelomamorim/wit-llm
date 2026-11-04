/*
 * @test
 * @summary Witup-generated tests for Integer.valueOf(String, int) behavior and exception paths
 * @run main IntegerWitupTest
 */

public class IntegerWitupTest {

    public static void main(String[] args) {
        checkValue("ff", 16, 255);
        checkValue("FF", 16, 255);
        checkValue("-7", 10, -7);
        checkValue("+42", 10, 42);
        checkValue("0", 2, 0);
        checkValue("101", 2, 5);
        checkValue("zz", 36, 1295);
        checkValue("-80000000", 16, Integer.MIN_VALUE);
        checkValue("7fffffff", 16, Integer.MAX_VALUE);

        checkNFE(null, 10);
        checkNFE(null, 1);
        checkNFE(null, 37);
        checkNFE("", 10);
        checkNFE("10", 1);
        checkNFE("10", 0);
        checkNFE("10", -1);
        checkNFE("10", 37);
        checkNFE("10", 100);
        checkNFE("2147483648", 10);
        checkNFE("-2147483649", 10);
        checkNFE("g", 16);
        checkNFE("-", 10);
    }

    static void checkValue(String s, int radix, int expected) {
        Integer v = Integer.valueOf(s, radix);
        if (v == null) {
            throw new RuntimeException("null result for " + s + " radix " + radix);
        }
        if (v.intValue() != expected) {
            throw new RuntimeException("unexpected value for " + s + " radix " + radix
                    + ": " + v.intValue() + " != " + expected);
        }
    }

    static void checkNFE(String s, int radix) {
        try {
            Integer.valueOf(s, radix);
        } catch (NumberFormatException e) {
            return;
        }
        throw new RuntimeException("expected NumberFormatException for " + s + " radix " + radix);
    }
}
