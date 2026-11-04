/*
 * @test
 * @summary Test ValueRange.of(long min, long maxSmallest, long maxLargest) for valid and invalid inputs
 * @run main ValueRangeWitupTest
 */

import java.time.temporal.ValueRange;

public class ValueRangeWitupTest {
    public static void main(String[] args) {
        testValidRange();
        testInvalidRangeMaxLargestLessThanMaxSmallest();
        testInvalidRangeMaxLargestLessThanMin();
        System.out.println("All tests passed.");
    }

    private static void testValidRange() {
        // Normal case: min <= maxSmallest <= maxLargest
        ValueRange vr = ValueRange.of(1L, 28L, 31L);
        if (vr == null) {
            throw new AssertionError("ValueRange.of returned null for valid input");
        }
        // Check that the returned object has expected min and max values
        // Since ValueRange.of(min, maxSmallest, maxLargest) calls of(min, min, maxSmallest, maxLargest),
        // minLargest == min, maxSmallest and maxLargest are as passed
        if (vr.getMinimum() != 1L) {
            throw new AssertionError("Minimum value incorrect: " + vr.getMinimum());
        }
        if (vr.getMaximum() != 31L) {
            throw new AssertionError("Maximum value incorrect: " + vr.getMaximum());
        }
    }

    private static void testInvalidRangeMaxLargestLessThanMaxSmallest() {
        // According to WIT, if maxLargest < maxSmallest, IllegalArgumentException is expected
        // The current method calls of(min, min, maxSmallest, maxLargest), so this triggers the deeper check
        try {
            ValueRange.of(1L, 31L, 30L); // maxLargest < maxSmallest
            throw new AssertionError("Expected IllegalArgumentException for maxLargest < maxSmallest");
        } catch (IllegalArgumentException expected) {
            // Expected exception
        }
    }

    private static void testInvalidRangeMaxLargestLessThanMin() {
        // According to WIT, if maxLargest >= maxSmallest && maxLargest < min, IllegalArgumentException is expected
        // We test a case where maxLargest >= maxSmallest but maxLargest < min
        // Because of the call of(min, min, maxSmallest, maxLargest), minLargest == min
        // So test with min=10, maxSmallest=5, maxLargest=9 (maxLargest >= maxSmallest is false here, so adjust)
        // To satisfy maxLargest >= maxSmallest, set maxSmallest=5, maxLargest=9, min=10
        // maxLargest < min is true (9 < 10), maxLargest >= maxSmallest is true (9 >= 5)
        try {
            ValueRange.of(10L, 5L, 9L);
            throw new AssertionError("Expected IllegalArgumentException for maxLargest >= maxSmallest && maxLargest < min");
        } catch (IllegalArgumentException expected) {
            // Expected exception
        }
    }
}
