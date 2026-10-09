/*
 * @test
 * @summary Tests the static factory method of java.time.temporal.ValueRange
 * @run main ValueRangeWitupTest_P060a73f6967e
 */

import java.time.temporal.ValueRange;

public class ValueRangeWitupTest_P060a73f6967e {
    public static void main(String[] args) {
        testValidRange();
        testInvalidRangeMinSmallestGreaterThanMinLargest();
        testInvalidRangeMaxSmallestGreaterThanMaxLargest();
        testInvalidRangeMinLargestGreaterThanMaxLargest();
    }

    static void testValidRange() {
        ValueRange vr = ValueRange.of(1L, 3L, 5L, 10L);
        if (vr == null) {
            throw new AssertionError("Expected non-null ValueRange");
        }
    }

    static void testInvalidRangeMinSmallestGreaterThanMinLargest() {
        try {
            ValueRange.of(5L, 3L, 10L, 15L);
            throw new AssertionError("Expected IllegalArgumentException for minSmallest > minLargest");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testInvalidRangeMaxSmallestGreaterThanMaxLargest() {
        try {
            ValueRange.of(1L, 3L, 20L, 15L);
            throw new AssertionError("Expected IllegalArgumentException for maxSmallest > maxLargest");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testInvalidRangeMinLargestGreaterThanMaxLargest() {
        try {
            ValueRange.of(1L, 5L, 10L, 8L);
            throw new AssertionError("Expected IllegalArgumentException for minLargest > maxLargest");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
