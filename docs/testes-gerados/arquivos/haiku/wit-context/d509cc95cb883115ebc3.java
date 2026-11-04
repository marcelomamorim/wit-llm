/*
 * @test
 * @summary Witup-generated checks for ValueRange.of(long min, long maxSmallest, long maxLargest)
 * @run main ValueRangeWitupTest
 */

import java.time.temporal.ValueRange;

public class ValueRangeWitupTest {

    public static void main(String[] args) {
        testValidRangeGetters();
        testBoundaryEquality();
        testEquivalentToFourArgumentOverload();
        expectIllegalArgument(1, 5, 4);
        expectIllegalArgument(10, 1, 5);
        expectIllegalArgument(5, 3, 10);
    }

    static void testValidRangeGetters() {
        ValueRange r = ValueRange.of(1, 5, 10);
        check(r.getMinimum() == 1, "minimum");
        check(r.getLargestMinimum() == 1, "largest minimum");
        check(r.getSmallestMaximum() == 5, "smallest maximum");
        check(r.getMaximum() == 10, "maximum");
    }

    static void testBoundaryEquality() {
        ValueRange r = ValueRange.of(2, 2, 2);
        check(r.getMinimum() == 2, "fixed minimum");
        check(r.getMaximum() == 2, "fixed maximum");
        check(r.isFixed(), "should be fixed");
    }

    static void testEquivalentToFourArgumentOverload() {
        ValueRange a = ValueRange.of(2, 7, 9);
        ValueRange b = ValueRange.of(2, 2, 7, 9);
        check(a.equals(b), "three-arg overload must equal four-arg overload with minSmallest=minLargest=min");
    }

    static void expectIllegalArgument(long min, long maxSmallest, long maxLargest) {
        try {
            ValueRange.of(min, maxSmallest, maxLargest);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("expected IllegalArgumentException for "
                + min + ", " + maxSmallest + ", " + maxLargest);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
