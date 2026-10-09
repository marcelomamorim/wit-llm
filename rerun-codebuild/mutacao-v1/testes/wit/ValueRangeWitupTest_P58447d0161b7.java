/* @test    @summary Test ValueRange.of with invalid inputs to trigger IllegalArgumentException    @run main ValueRangeWitupTest_P58447d0161b7 */

import java.time.temporal.ValueRange;

public class ValueRangeWitupTest_P58447d0161b7 {

    public static void main(String[] args) {
        testMinLargestLessThanMinSmallest();
        testMaxLargestLessThanMaxSmallest();
        testMaxLargestLessThanMinLargest();
    }

    static void testMinLargestLessThanMinSmallest() {
        try {
            ValueRange.of(10L, 5L, 20L, 30L);
            throw new AssertionError("Expected IllegalArgumentException for minLargest < minSmallest");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    static void testMaxLargestLessThanMaxSmallest() {
        try {
            ValueRange.of(1L, 3L, 20L, 15L);
            throw new AssertionError("Expected IllegalArgumentException for maxLargest < maxSmallest");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    static void testMaxLargestLessThanMinLargest() {
        try {
            ValueRange.of(1L, 5L, 10L, 8L);
            throw new AssertionError("Expected IllegalArgumentException for maxLargest < minLargest");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
