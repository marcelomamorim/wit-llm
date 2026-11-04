/*
 * @test
 * @summary Test Long.valueOf(String s, int radix) for various inputs
 * @run main LongWitupTest
 */

public class LongWitupTest {
    public static void main(String[] args) {
        testValidDecimal();
        testValidHex();
        testValidBinary();
        testInvalidRadixFallsBackToDecimal();
        testInvalidStringThrows();
        testNegativeNumber();
        testZero();
    }

    private static void testValidDecimal() {
        Long l = Long.valueOf("12345", 10);
        if (l.longValue() != 12345L) {
            throw new Error("Failed decimal parse: expected 12345 but got " + l);
        }
    }

    private static void testValidHex() {
        Long l = Long.valueOf("7fff", 16);
        if (l.longValue() != 0x7fffl) {
            throw new Error("Failed hex parse: expected 0x7fff but got " + l);
        }
    }

    private static void testValidBinary() {
        Long l = Long.valueOf("1010", 2);
        if (l.longValue() != 10L) {
            throw new Error("Failed binary parse: expected 10 but got " + l);
        }
    }

    private static void testInvalidRadixFallsBackToDecimal() {
        // radix < Character.MIN_RADIX (2) or > Character.MAX_RADIX (36) falls back to 10
        Long l1 = Long.valueOf("123", 1); // invalid radix 1, fallback to 10
        if (l1.longValue() != 123L) {
            throw new Error("Failed fallback radix parse: expected 123 but got " + l1);
        }
        Long l2 = Long.valueOf("123", 37); // invalid radix 37, fallback to 10
        if (l2.longValue() != 123L) {
            throw new Error("Failed fallback radix parse: expected 123 but got " + l2);
        }
    }

    private static void testInvalidStringThrows() {
        try {
            Long.valueOf("xyz", 10);
            throw new Error("Expected NumberFormatException for invalid string");
        } catch (NumberFormatException e) {
            // expected
        }
    }

    private static void testNegativeNumber() {
        Long l = Long.valueOf("-42", 10);
        if (l.longValue() != -42L) {
            throw new Error("Failed negative number parse: expected -42 but got " + l);
        }
    }

    private static void testZero() {
        Long l = Long.valueOf("0", 10);
        if (l.longValue() != 0L) {
            throw new Error("Failed zero parse: expected 0 but got " + l);
        }
    }
}
