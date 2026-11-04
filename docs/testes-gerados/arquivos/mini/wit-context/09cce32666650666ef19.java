/*
 * @test
 * @summary Test java.lang.Long.valueOf(String s, int radix) for valid and invalid inputs
 * @run main LongWitupTest
 */

public class LongWitupTest {
    public static void main(String[] args) {
        testValidValueOf();
        testNullStringThrows();
        testInvalidRadixStringThrows();
        testRadixOutOfRangeDefaultsTo10();
    }

    private static void testValidValueOf() {
        // Valid conversions
        Long l1 = Long.valueOf("10", 10);
        if (l1.longValue() != 10L) {
            throw new Error("Failed: Long.valueOf(\"10\", 10) expected 10L but got " + l1);
        }
        Long l2 = Long.valueOf("a", 16);
        if (l2.longValue() != 10L) {
            throw new Error("Failed: Long.valueOf(\"a\", 16) expected 10L but got " + l2);
        }
        Long l3 = Long.valueOf("-1", 10);
        if (l3.longValue() != -1L) {
            throw new Error("Failed: Long.valueOf(\"-1\", 10) expected -1L but got " + l3);
        }
    }

    private static void testNullStringThrows() {
        try {
            Long.valueOf(null, 10);
            throw new Error("Failed: Long.valueOf(null, 10) did not throw NumberFormatException");
        } catch (NumberFormatException e) {
            // Expected
        } catch (NullPointerException e) {
            // Acceptable if implementation throws NPE instead of NFE
        }
    }

    private static void testInvalidRadixStringThrows() {
        try {
            Long.valueOf("xyz", 10);
            throw new Error("Failed: Long.valueOf(\"xyz\", 10) did not throw NumberFormatException");
        } catch (NumberFormatException e) {
            // Expected
        }
    }

    private static void testRadixOutOfRangeDefaultsTo10() {
        // Radix less than Character.MIN_RADIX (2) defaults to 10
        Long l1 = Long.valueOf("10", 1);
        if (l1.longValue() != 10L) {
            throw new Error("Failed: Long.valueOf(\"10\", 1) expected 10L but got " + l1);
        }
        // Radix greater than Character.MAX_RADIX (36) defaults to 10
        Long l2 = Long.valueOf("10", 37);
        if (l2.longValue() != 10L) {
            throw new Error("Failed: Long.valueOf(\"10\", 37) expected 10L but got " + l2);
        }
    }
}
