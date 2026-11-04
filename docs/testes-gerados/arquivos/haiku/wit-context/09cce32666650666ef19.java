/*
 * @test
 * @summary Witup-generated tests for Long.valueOf(String, int)
 * @run main LongWitupTest
 */

public class LongWitupTest {
    public static void main(String[] args) {
        check(Long.valueOf("ff", 16).longValue() == 255L, "hex ff");
        check(Long.valueOf("-7f", 16).longValue() == -127L, "hex negative");
        check(Long.valueOf("0", 10).longValue() == 0L, "zero");
        check(Long.valueOf("101", 2).longValue() == 5L, "binary");
        check(Long.valueOf("7fffffffffffffff", 16).longValue() == Long.MAX_VALUE, "max value");
        check(Long.valueOf("-8000000000000000", 16).longValue() == Long.MIN_VALUE, "min value");
        expectNFE(null, 10);
        expectNFE("", 10);
        expectNFE("zz", 10);
        expectNFE("8000000000000000", 16);
        expectNFE("10", 1);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void expectNFE(String s, int radix) {
        try {
            Long.valueOf(s, radix);
        } catch (NumberFormatException e) {
            return;
        }
        throw new RuntimeException("expected NumberFormatException for input " + s + " radix " + radix);
    }
}
