/*
 * @test
 * @summary Witup-generated tests for ValueRange.of(long, long) argument validation
 * @run main ValueRangeWitupTest
 */

import java.time.temporal.ValueRange;

public class ValueRangeWitupTest {
    public static void main(String[] args) {
        testRejectsMaxLessThanMin();
        testRejectsExtremeMaxLessThanMin();
        testAcceptsEqualBounds();
        testAcceptsOrderedBounds();
        testAcceptsExtremeBounds();
    }

    static void testRejectsMaxLessThanMin() {
        try {
            ValueRange.of(5L, 1L);
            throw new RuntimeException("expected IllegalArgumentException for min > max");
        } catch (IllegalArgumentException expected) {
        }
    }

    static void testRejectsExtremeMaxLessThanMin() {
        try {
            ValueRange.of(Long.MAX_VALUE, Long.MIN_VALUE);
            throw new RuntimeException("expected IllegalArgumentException for extreme min > max");
        } catch (IllegalArgumentException expected) {
        }
    }

    static void testAcceptsEqualBounds() {
        ValueRange range = ValueRange.of(3L, 3L);
        if (range.getMinimum() != 3L || range.getMaximum() != 3L) {
            throw new RuntimeException("unexpected bounds for equal min and max: " + range);
        }
    }

    static void testAcceptsOrderedBounds() {
        ValueRange range = ValueRange.of(1L, 5L);
        if (range.getMinimum() != 1L || range.getMaximum() != 5L) {
            throw new RuntimeException("unexpected bounds: " + range);
        }
        if (!range.isValidValue(1L) || !range.isValidValue(5L) || range.isValidValue(6L)) {
            throw new RuntimeException("unexpected validity for range " + range);
        }
    }

    static void testAcceptsExtremeBounds() {
        ValueRange range = ValueRange.of(Long.MIN_VALUE, Long.MAX_VALUE);
        if (range.getMinimum() != Long.MIN_VALUE || range.getMaximum() != Long.MAX_VALUE) {
            throw new RuntimeException("unexpected extreme bounds: " + range);
        }
    }
}
