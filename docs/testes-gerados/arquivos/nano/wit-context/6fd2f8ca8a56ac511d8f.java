/* @test    @summary Test for ValueRange.of(long, long, long, long) with invalid inputs to verify exception throwing    @run main ValueRangeWitupTest */

import java.time.temporal.ValueRange;

public class ValueRangeWitupTest {

    public static void main(String[] args) {
        testMinLargestLessThanMinSmallest();
        testMaxLargestLessThanMaxSmallest();
        testMaxLargestLessThanMinLargest();
    }

    private static void testMinLargestLessThanMinSmallest() {
        try {
            ValueRange.of(10L, 5L, 20L, 30L);
            System.out.println("Failed: Expected IllegalArgumentException for minLargest < minSmallest");
        } catch (IllegalArgumentException e) {
            System.out.println("Passed: Caught expected exception for minLargest < minSmallest");
        }
    }

    private static void testMaxLargestLessThanMaxSmallest() {
        try {
            ValueRange.of(1L, 3L, 20L, 15L);
            System.out.println("Failed: Expected IllegalArgumentException for maxLargest < maxSmallest");
        } catch (IllegalArgumentException e) {
            System.out.println("Passed: Caught expected exception for maxLargest < maxSmallest");
        }
    }

    private static void testMaxLargestLessThanMinLargest() {
        try {
            ValueRange.of(1L, 10L, 20L, 5L);
            System.out.println("Failed: Expected IllegalArgumentException for maxLargest < minLargest");
        } catch (IllegalArgumentException e) {
            System.out.println("Passed: Caught expected exception for maxLargest < minLargest");
        }
    }
}
