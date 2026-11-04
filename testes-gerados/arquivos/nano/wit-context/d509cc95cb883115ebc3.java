/* @test    @summary Test for ValueRange.of(long, long, long) method to verify behavior and exception handling    @run main ValueRangeWitupTest */

import java.time.temporal.ValueRange;

public class ValueRangeWitupTest {

    public static void main(String[] args) {
        testValidInputs();
        testMaxLargestLessThanMin();
        testMaxLargestGreaterOrEqualMin();
    }

    static void testValidInputs() {
        long min = 1L;
        long maxSmallest = 10L;
        long maxLargest = 20L;
        ValueRange vr = ValueRange.of(min, maxSmallest, maxLargest);
        if (vr == null) {
            throw new AssertionError("Expected non-null ValueRange for valid inputs");
        }
    }

    static void testMaxLargestLessThanMin() {
        long min = 5L;
        long maxSmallest = 10L;
        long maxLargest = 4L; // less than min, should throw
        try {
            ValueRange.of(min, maxSmallest, maxLargest);
            throw new AssertionError("Expected IllegalArgumentException for maxLargest < min");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testMaxLargestGreaterOrEqualMaxSmallest() {
        long min = 1L;
        long maxSmallest = 10L;
        long maxLargest = 10L; // equal to maxSmallest, valid
        try {
            ValueRange.of(min, maxSmallest, maxLargest);
        } catch (IllegalArgumentException e) {
            throw new AssertionError("Did not expect exception for maxLargest == maxSmallest");
        }
        long maxLargestInvalid = 9L; // less than maxSmallest, should throw
        try {
            ValueRange.of(min, maxSmallest, maxLargestInvalid);
            throw new AssertionError("Expected IllegalArgumentException for maxLargest < maxSmallest");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
