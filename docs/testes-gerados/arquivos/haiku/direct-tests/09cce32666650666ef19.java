/*
 * @test
 * @summary Tests observable results and exceptions of Long.valueOf(String, int)
 * @run main LongWitupTest
 */

public class LongWitupTest {
    public static void main(String[] args) {
        check(Long.valueOf("123", 10).longValue() == 123L, "decimal parse failed");
        check(Long.valueOf("+5", 10).longValue() == 5L, "explicit plus sign failed");
        check(Long.valueOf("-255", 10).longValue() == -255L, "negative decimal failed");
        check(Long.valueOf("ff", 16).longValue() == 255L, "hex parse failed");
        check(Long.valueOf("-ff", 16).longValue() == -255L, "negative hex parse failed");
        check(Long.valueOf("1010", 2).longValue() == 10L, "binary parse failed");
        check(Long.valueOf("7fffffffffffffff", 16).longValue() == Long.MAX_VALUE, "MAX_VALUE hex parse failed");
        check(Long.valueOf("-8000000000000000", 16).longValue() == Long.MIN_VALUE, "MIN_VALUE hex parse failed");
        check(Long.valueOf("0", 16).equals(Long.valueOf(0L)), "zero parse failed");

        check(Long.valueOf("10", 16).equals(Long.valueOf(16L)), "equals contract for radix result failed");

        expectNFE("", 10, "empty string must throw NumberFormatException");
        expectNFE("9223372036854775808", 10, "decimal overflow must throw NumberFormatException");
        expectNFE("8000000000000000", 16, "hex overflow must throw NumberFormatException");
        expectNFE("g", 16, "invalid digit must throw NumberFormatException");
        expectNFE("12", 1, "radix below MIN_RADIX must throw NumberFormatException");
        expectNFE("12", 37, "radix above MAX_RADIX must throw NumberFormatException");
        expectNFE(null, 10, "null string must throw NumberFormatException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void expectNFE(String s, int radix, String message) {
        try {
            Long.valueOf(s, radix);
        } catch (NumberFormatException e) {
            return;
        }
        throw new RuntimeException(message);
    }
}
