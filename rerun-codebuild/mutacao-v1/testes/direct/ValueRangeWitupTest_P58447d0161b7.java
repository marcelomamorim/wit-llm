/* @test    @summary Test ValueRange.of with valid parameters    @run main ValueRangeWitupTest_P58447d0161b7 */

import java.time.temporal.ValueRange;

public class ValueRangeWitupTest_P58447d0161b7 {
    public static void main(String[] args) {
        testValidRange();
        testInvalidRangeMinGreaterThanMaxSmallest();
        testInvalidRangeMaxSmallestGreaterThanMaxLargest();
        testInvalidRangeMinLargestGreaterThanMaxLargest();
    }

    static void testValidRange() {
        try {
            ValueRange vr = ValueRange.of(1, 3, 5, 10);
            if (vr == null) {
                throw new AssertionError("Expected non-null ValueRange");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testValidRange: " + e);
        }
    }

    static void testInvalidRangeMinGreaterThanMaxSmallest() {
        try {
            ValueRange.of(5, 3, 7, 10);
            throw new AssertionError("Expected IllegalArgumentException for minSmallest > minLargest");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }

    static void testInvalidRangeMaxSmallestGreaterThanMaxLargest() {
        try {
            ValueRange.of(1, 3, 10, 7);
            throw new AssertionError("Expected IllegalArgumentException for maxSmallest > maxLargest");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }

    static void testInvalidRangeMinLargestGreaterThanMaxLargest() {
        try {
            ValueRange.of(1, 5, 7, 6);
            throw new AssertionError("Expected IllegalArgumentException for minLargest > maxLargest");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }
}
