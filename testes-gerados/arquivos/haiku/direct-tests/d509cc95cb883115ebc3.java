/*
 * @test
 * @summary Verifies ValueRange.of(min, maxSmallest, maxLargest) delegates to the four-argument factory with minLargest equal to min
 * @run main ValueRangeWitupTest
 */

import java.time.temporal.ValueRange;

public class ValueRangeWitupTest {

    public static void main(String[] args) {
        testDayOfMonthLikeRange();
        testFixedRange();
        testNegativeBounds();
        testValidValueBoundaries();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testDayOfMonthLikeRange() {
        ValueRange r = ValueRange.of(1, 28, 31);
        check(r.getMinimum() == 1, "minimum expected 1 but was " + r.getMinimum());
        check(r.getLargestMinimum() == 1, "largest minimum expected 1 but was " + r.getLargestMinimum());
        check(r.getSmallestMaximum() == 28, "smallest maximum expected 28 but was " + r.getSmallestMaximum());
        check(r.getMaximum() == 31, "maximum expected 31 but was " + r.getMaximum());
        check(!r.isFixed(), "range with differing maxima must not be fixed");
    }

    static void testFixedRange() {
        ValueRange r = ValueRange.of(0, 0, 0);
        check(r.isFixed(), "range of(0, 0, 0) must be fixed");
        check(r.getMinimum() == 0 && r.getMaximum() == 0, "fixed range bounds mismatch");
    }

    static void testNegativeBounds() {
        ValueRange r = ValueRange.of(-5, -2, 3);
        check(r.getMinimum() == -5, "minimum expected -5 but was " + r.getMinimum());
        check(r.getLargestMinimum() == -5, "largest minimum expected -5 but was " + r.getLargestMinimum());
        check(r.getSmallestMaximum() == -2, "smallest maximum expected -2 but was " + r.getSmallestMaximum());
        check(r.getMaximum() == 3, "maximum expected 3 but was " + r.getMaximum());
    }

    static void testValidValueBoundaries() {
        ValueRange r = ValueRange.of(1, 28, 31);
        check(r.isValidValue(1), "1 must be valid");
        check(r.isValidValue(28), "28 must be valid");
        check(r.isValidValue(31), "31 must be valid");
        check(!r.isValidValue(0), "0 must be invalid");
        check(!r.isValidValue(32), "32 must be invalid");
    }
}
