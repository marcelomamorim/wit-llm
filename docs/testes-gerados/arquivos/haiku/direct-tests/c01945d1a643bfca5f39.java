/*
 * @test
 * @summary Tests ValueRange.of(long, long) bounds and argument validation
 * @run main ValueRangeWitupTest
 */

import java.time.temporal.ValueRange;

public class ValueRangeWitupTest {

    public static void main(String[] args) {
        testRegularRange();
        testSingleValueRange();
        testFullLongRange();
        testMinGreaterThanMaxThrows();
    }

    static void testRegularRange() {
        ValueRange r = ValueRange.of(1, 10);
        check(r.getMinimum() == 1, "minimum should be 1");
        check(r.getLargestMinimum() == 1, "largest minimum should be 1");
        check(r.getSmallestMaximum() == 10, "smallest maximum should be 10");
        check(r.getMaximum() == 10, "maximum should be 10");
    }

    static void testSingleValueRange() {
        ValueRange r = ValueRange.of(5, 5);
        check(r.getMinimum() == 5, "minimum should be 5");
        check(r.getMaximum() == 5, "maximum should be 5");
    }

    static void testFullLongRange() {
        ValueRange r = ValueRange.of(Long.MIN_VALUE, Long.MAX_VALUE);
        check(r.getMinimum() == Long.MIN_VALUE, "minimum should be Long.MIN_VALUE");
        check(r.getMaximum() == Long.MAX_VALUE, "maximum should be Long.MAX_VALUE");
    }

    static void testMinGreaterThanMaxThrows() {
        boolean thrown = false;
        try {
            ValueRange.of(10, 1);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "of(10, 1) should throw IllegalArgumentException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
