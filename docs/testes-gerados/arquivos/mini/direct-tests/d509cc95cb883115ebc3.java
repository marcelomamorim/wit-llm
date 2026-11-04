/*
 * @test
 * @summary Test ValueRange.of(long min, long maxSmallest, long maxLargest) factory method
 * @run main ValueRangeWitupTest
 */

import java.time.temporal.ValueRange;

public class ValueRangeWitupTest {
    public static void main(String[] args) {
        // Test with min == maxSmallest == maxLargest
        ValueRange vr1 = ValueRange.of(5L, 5L, 5L);
        if (vr1 == null) throw new AssertionError("ValueRange.of returned null");
        if (vr1.getMinimum() != 5L) throw new AssertionError("Minimum expected 5 but was " + vr1.getMinimum());
        if (vr1.getLargestMinimum() != 5L) throw new AssertionError("Largest minimum expected 5 but was " + vr1.getLargestMinimum());
        if (vr1.getSmallestMaximum() != 5L) throw new AssertionError("Smallest maximum expected 5 but was " + vr1.getSmallestMaximum());
        if (vr1.getMaximum() != 5L) throw new AssertionError("Maximum expected 5 but was " + vr1.getMaximum());

        // Test with min < maxSmallest < maxLargest
        ValueRange vr2 = ValueRange.of(1L, 10L, 20L);
        if (vr2 == null) throw new AssertionError("ValueRange.of returned null");
        if (vr2.getMinimum() != 1L) throw new AssertionError("Minimum expected 1 but was " + vr2.getMinimum());
        if (vr2.getLargestMinimum() != 1L) throw new AssertionError("Largest minimum expected 1 but was " + vr2.getLargestMinimum());
        if (vr2.getSmallestMaximum() != 10L) throw new AssertionError("Smallest maximum expected 10 but was " + vr2.getSmallestMaximum());
        if (vr2.getMaximum() != 20L) throw new AssertionError("Maximum expected 20 but was " + vr2.getMaximum());

        // Test with min == maxSmallest < maxLargest
        ValueRange vr3 = ValueRange.of(3L, 3L, 7L);
        if (vr3 == null) throw new AssertionError("ValueRange.of returned null");
        if (vr3.getMinimum() != 3L) throw new AssertionError("Minimum expected 3 but was " + vr3.getMinimum());
        if (vr3.getLargestMinimum() != 3L) throw new AssertionError("Largest minimum expected 3 but was " + vr3.getLargestMinimum());
        if (vr3.getSmallestMaximum() != 3L) throw new AssertionError("Smallest maximum expected 3 but was " + vr3.getSmallestMaximum());
        if (vr3.getMaximum() != 7L) throw new AssertionError("Maximum expected 7 but was " + vr3.getMaximum());
    }
}
