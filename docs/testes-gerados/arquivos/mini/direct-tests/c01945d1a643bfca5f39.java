/*
 * @test
 * @summary Test java.time.temporal.ValueRange.of(long min, long max) method
 * @run main ValueRangeWitupTest
 */

import java.time.temporal.ValueRange;

public class ValueRangeWitupTest {
    public static void main(String[] args) {
        testValidRange();
        testEqualMinMax();
        testInvalidRange();
    }

    private static void testValidRange() {
        ValueRange range = ValueRange.of(1L, 10L);
        if (range == null) {
            throw new AssertionError("ValueRange.of returned null for valid input");
        }
        if (range.getMinimum() != 1L) {
            throw new AssertionError("Expected minimum 1 but got " + range.getMinimum());
        }
        if (range.getLargestMinimum() != 1L) {
            throw new AssertionError("Expected largest minimum 1 but got " + range.getLargestMinimum());
        }
        if (range.getSmallestMaximum() != 10L) {
            throw new AssertionError("Expected smallest maximum 10 but got " + range.getSmallestMaximum());
        }
        if (range.getMaximum() != 10L) {
            throw new AssertionError("Expected maximum 10 but got " + range.getMaximum());
        }
    }

    private static void testEqualMinMax() {
        ValueRange range = ValueRange.of(5L, 5L);
        if (range == null) {
            throw new AssertionError("ValueRange.of returned null for equal min and max");
        }
        if (range.getMinimum() != 5L || range.getLargestMinimum() != 5L ||
            range.getSmallestMaximum() != 5L || range.getMaximum() != 5L) {
            throw new AssertionError("Expected all range values to be 5 but got: " +
                range.getMinimum() + ", " + range.getLargestMinimum() + ", " +
                range.getSmallestMaximum() + ", " + range.getMaximum());
        }
    }

    private static void testInvalidRange() {
        try {
            ValueRange.of(10L, 1L);
            throw new AssertionError("Expected IllegalArgumentException for min > max");
        } catch (IllegalArgumentException ex) {
            String msg = ex.getMessage();
            if (msg == null || !msg.contains("Minimum value must be less than maximum value")) {
                throw new AssertionError("Exception message unexpected: " + msg);
            }
        }
    }
}
