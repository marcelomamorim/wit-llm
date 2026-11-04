/* @test    @summary Test ValueRange.of(long, long) method for valid and invalid inputs    @run main ValueRangeWitupTest */

import java.time.temporal.ValueRange;

public class ValueRangeWitupTest {

    public static void main(String[] args) {
        testValidRange();
        testInvalidRange();
    }

    static void testValidRange() {
        ValueRange range = ValueRange.of(1L, 10L);
        assert range != null : "Range should not be null";
        // Check that min and max are as expected
        if (range.getMinimum() != 1L || range.getMaximum() != 10L) {
            throw new AssertionError("Unexpected range bounds: " + range);
        }
    }

    static void testInvalidRange() {
        boolean exceptionThrown = false;
        try {
            ValueRange.of(10L, 1L);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException for min > max");
        }
    }
}
