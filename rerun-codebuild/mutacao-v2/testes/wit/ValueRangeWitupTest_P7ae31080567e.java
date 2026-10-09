/*
 * @test
 * @summary Deterministic tests for ValueRange.of(long, long, long, long) covering valid and invalid inputs
 * @run main ValueRangeWitupTest_P7ae31080567e
 */

import java.time.temporal.ValueRange;

public class ValueRangeWitupTest_P7ae31080567e {
    public static void main(String[] args) {
        testValidRange();
        testMinSmallestGreaterThanMinLargest();
        testMaxSmallestGreaterThanMaxLargest();
        testMinLargestGreaterThanMaxLargest();
        System.out.println("All tests passed");
    }

    private static void testValidRange() {
        // Normal valid range
        ValueRange vr = ValueRange.of(1L, 2L, 28L, 31L);
        if (vr == null) {
            throw new AssertionError("ValueRange.of returned null for valid input");
        }
        // Check that returned object has expected minSmallest and maxLargest
        if (vr.getMinimum() != 1L) {
            throw new AssertionError("Minimum expected 1 but got " + vr.getMinimum());
        }
        if (vr.getMaximum() != 31L) {
            throw new AssertionError("Maximum expected 31 but got " + vr.getMaximum());
        }
    }

    private static void testMinSmallestGreaterThanMinLargest() {
        // minSmallest > minLargest should throw IllegalArgumentException
        try {
            ValueRange.of(5L, 4L, 10L, 20L);
            throw new AssertionError("Expected IllegalArgumentException for minSmallest > minLargest");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }

    private static void testMaxSmallestGreaterThanMaxLargest() {
        // maxSmallest > maxLargest should throw IllegalArgumentException
        try {
            ValueRange.of(1L, 2L, 10L, 9L);
            throw new AssertionError("Expected IllegalArgumentException for maxSmallest > maxLargest");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }

    private static void testMinLargestGreaterThanMaxLargest() {
        // minLargest > maxLargest should throw IllegalArgumentException
        try {
            ValueRange.of(1L, 10L, 20L, 15L);
            throw new AssertionError("Expected IllegalArgumentException for minLargest > maxLargest");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }
}
