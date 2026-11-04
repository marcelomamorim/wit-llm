/*
 * @test
 * @summary Test ValueRange.of(long min, long max) method for valid and invalid inputs
 * @run main ValueRangeWitupTest
 */

import java.time.temporal.ValueRange;

public class ValueRangeWitupTest {
    public static void main(String[] args) {
        testValidRange();
        testInvalidRange();
    }

    private static void testValidRange() {
        // Test with min less than max
        ValueRange range = ValueRange.of(1, 10);
        if (range == null) {
            throw new AssertionError("ValueRange.of returned null for valid input");
        }
        if (range.getMinimum() != 1) {
            throw new AssertionError("Minimum value incorrect, expected 1 but got " + range.getMinimum());
        }
        if (range.getMaximum() != 10) {
            throw new AssertionError("Maximum value incorrect, expected 10 but got " + range.getMaximum());
        }

        // Test with min equal to max
        ValueRange rangeEqual = ValueRange.of(5, 5);
        if (rangeEqual.getMinimum() != 5) {
            throw new AssertionError("Minimum value incorrect for equal min and max, expected 5 but got " + rangeEqual.getMinimum());
        }
        if (rangeEqual.getMaximum() != 5) {
            throw new AssertionError("Maximum value incorrect for equal min and max, expected 5 but got " + rangeEqual.getMaximum());
        }
    }

    private static void testInvalidRange() {
        // Test with min greater than max should throw IllegalArgumentException
        try {
            ValueRange.of(10, 1);
            throw new AssertionError("Expected IllegalArgumentException for min > max");
        } catch (IllegalArgumentException expected) {
            // Expected exception
        } catch (Exception unexpected) {
            throw new AssertionError("Unexpected exception type: " + unexpected);
        }
    }
}
