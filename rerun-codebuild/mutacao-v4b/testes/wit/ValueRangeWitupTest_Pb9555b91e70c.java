/* @test
   @summary Tests for ValueRange.of(long, long, long, long) method.
   @run main ValueRangeWitupTest_Pb9555b91e70c
 */
import java.time.temporal.ValueRange;
public class ValueRangeWitupTest_Pb9555b91e70c {
    public static void main(String[] args) {
        testValidRange();
        testMinLargestLessThanMinSmallest();
        testMaxLargestLessThanMaxSmallest();
        testMaxLargestLessThanMinLargest();
    }
    static void testValidRange() {
        try {
            ValueRange vr = ValueRange.of(1L, 5L, 10L, 20L);
            if (vr == null) throw new AssertionError("Expected non-null ValueRange");
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in valid range", e);
        }
    }
    static void testMinLargestLessThanMinSmallest() {
        try {
            ValueRange.of(5L, 3L, 10L, 20L);
            throw new AssertionError("Expected IllegalArgumentException for minLargest < minSmallest");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
    static void testMaxLargestLessThanMaxSmallest() {
        try {
            ValueRange.of(1L, 5L, 20L, 15L);
            throw new AssertionError("Expected IllegalArgumentException for maxLargest < maxSmallest");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
    static void testMaxLargestLessThanMinLargest() {
        try {
            ValueRange.of(1L, 10L, 15L, 12L);
            throw new AssertionError("Expected IllegalArgumentException for maxLargest < minLargest");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
