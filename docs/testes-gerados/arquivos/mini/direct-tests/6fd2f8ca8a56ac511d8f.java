/*
 * @test
 * @summary Test ValueRange.of(long, long, long, long) method for valid and invalid inputs
 * @run main ValueRangeWitupTest
 */

import java.time.temporal.ValueRange;

public class ValueRangeWitupTest {
    public static void main(String[] args) {
        testValidRangeCreation();
        testInvalidMinSmallestGreaterThanMinLargest();
        testInvalidMaxSmallestGreaterThanMaxLargest();
        testInvalidMinLargestGreaterThanMaxLargest();
    }

    private static void testValidRangeCreation() {
        ValueRange range = ValueRange.of(1L, 2L, 3L, 4L);
        if (range == null) {
            throw new AssertionError("ValueRange.of returned null for valid input");
        }
        if (range.getMinimum() != 1L) {
            throw new AssertionError("Minimum value incorrect");
        }
        if (range.getLargestMinimum() != 2L) {
            throw new AssertionError("Largest minimum value incorrect");
        }
        if (range.getSmallestMaximum() != 3L) {
            throw new AssertionError("Smallest maximum value incorrect");
        }
        if (range.getMaximum() != 4L) {
            throw new AssertionError("Maximum value incorrect");
        }
    }

    private static void testInvalidMinSmallestGreaterThanMinLargest() {
        try {
            ValueRange.of(5L, 4L, 6L, 7L);
            throw new AssertionError("Expected IllegalArgumentException for minSmallest > minLargest");
        } catch (IllegalArgumentException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Smallest minimum value must be less than largest minimum value")) {
                throw new AssertionError("Unexpected exception message: " + msg);
            }
        }
    }

    private static void testInvalidMaxSmallestGreaterThanMaxLargest() {
        try {
            ValueRange.of(1L, 2L, 5L, 4L);
            throw new AssertionError("Expected IllegalArgumentException for maxSmallest > maxLargest");
        } catch (IllegalArgumentException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Smallest maximum value must be less than largest maximum value")) {
                throw new AssertionError("Unexpected exception message: " + msg);
            }
        }
    }

    private static void testInvalidMinLargestGreaterThanMaxLargest() {
        try {
            ValueRange.of(1L, 5L, 3L, 4L);
            throw new AssertionError("Expected IllegalArgumentException for minLargest > maxLargest");
        } catch (IllegalArgumentException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Minimum value must be less than maximum value")) {
                throw new AssertionError("Unexpected exception message: " + msg);
            }
        }
    }
}
