/*
 * @test
 * @summary Test for ValueRange.of(long, long) method, covering normal and exceptional cases.
 * @run main ValueRangeWitupTest
 */

import java.time.temporal.ValueRange;

public class ValueRangeWitupTest {

    public static void main(String[] args) {
        testValidRange();
        testInvalidRange();
    }

    static void testValidRange() {
        try {
            ValueRange vr = ValueRange.of(1L, 10L);
            if (vr == null) {
                throw new AssertionError("Expected non-null ValueRange for valid input");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid input: " + e);
        }
    }

    static void testInvalidRange() {
        try {
            ValueRange.of(10L, 5L);
            throw new AssertionError("Expected IllegalArgumentException for max < min");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }
}
