/* @test    @summary Test for ValueRange.of() method with invalid arguments to verify exception throwing and behavior.    @run main ValueRangeWitupTest_P060a73f6967e */

import java.time.temporal.ValueRange;

public class ValueRangeWitupTest_P060a73f6967e {

    public static void main(String[] args) {
        testMinLargestLessThanMinSmallest();
        testMaxLargestLessThanMaxSmallest();
        testMaxLargestLessThanMinLargest();
    }

    private static void testMinLargestLessThanMinSmallest() {
        boolean threw = false;
        try {
            ValueRange.of(10L, 5L, 20L, 30L);
        } catch (IllegalArgumentException e) {
            threw = true;
            assert e.getMessage().contains("Smallest minimum value must be less than largest minimum value") : "Unexpected message: " + e.getMessage();
        }
        if (!threw) {
            throw new AssertionError("Expected IllegalArgumentException for minLargest < minSmallest");
        }
    }

    private static void testMaxLargestLessThanMaxSmallest() {
        boolean threw = false;
        try {
            ValueRange.of(1L, 3L, 20L, 15L);
        } catch (IllegalArgumentException e) {
            threw = true;
            assert e.getMessage().contains("Smallest maximum value must be less than largest maximum value") : "Unexpected message: " + e.getMessage();
        }
        if (!threw) {
            throw new AssertionError("Expected IllegalArgumentException for maxLargest < maxSmallest");
        }
    }

    private static void testMaxLargestLessThanMinLargest() {
        boolean threw = false;
        try {
            ValueRange.of(1L, 10L, 20L, 5L);
        } catch (IllegalArgumentException e) {
            threw = true;
            assert e.getMessage().contains("Minimum value must be less than maximum value") : "Unexpected message: " + e.getMessage();
        }
        if (!threw) {
            throw new AssertionError("Expected IllegalArgumentException for maxLargest < minLargest");
        }
    }
}
