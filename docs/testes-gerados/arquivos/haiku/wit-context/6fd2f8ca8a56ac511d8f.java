/*
 * @test
 * @summary Checks argument validation and returned bounds of ValueRange.of(long, long, long, long)
 * @run main ValueRangeWitupTest
 */

import java.time.temporal.ValueRange;

public class ValueRangeWitupTest {

    public static void main(String[] args) {
        testSmallestMinGreaterThanLargestMin();
        testSmallestMaxGreaterThanLargestMax();
        testLargestMinGreaterThanLargestMax();
        testValidRangeBounds();
        testEqualBoundsAccepted();
    }

    static void testSmallestMinGreaterThanLargestMin() {
        boolean thrown = false;
        try {
            ValueRange.of(1, 0, 5, 10);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException when minSmallest > minLargest");
        }
    }

    static void testSmallestMaxGreaterThanLargestMax() {
        boolean thrown = false;
        try {
            ValueRange.of(1, 2, 10, 5);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException when maxSmallest > maxLargest");
        }
    }

    static void testLargestMinGreaterThanLargestMax() {
        boolean thrown = false;
        try {
            ValueRange.of(1, 20, 5, 10);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException when minLargest > maxLargest");
        }
    }

    static void testValidRangeBounds() {
        ValueRange range = ValueRange.of(1, 2, 3, 4);
        if (range.getMinimum() != 1) {
            throw new RuntimeException("Unexpected minimum: " + range.getMinimum());
        }
        if (range.getLargestMinimum() != 2) {
            throw new RuntimeException("Unexpected largest minimum: " + range.getLargestMinimum());
        }
        if (range.getSmallestMaximum() != 3) {
            throw new RuntimeException("Unexpected smallest maximum: " + range.getSmallestMaximum());
        }
        if (range.getMaximum() != 4) {
            throw new RuntimeException("Unexpected maximum: " + range.getMaximum());
        }
    }

    static void testEqualBoundsAccepted() {
        ValueRange range = ValueRange.of(5, 5, 5, 5);
        if (range.getMinimum() != 5 || range.getMaximum() != 5) {
            throw new RuntimeException("Unexpected bounds for equal-value range: " + range);
        }
    }
}
