/*
 * @test
 * @summary Test for Arrays.copyOfRange handling of invalid range
 * @run main ArraysWitupTest
 */

import java.util.Arrays;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testCopyOfRangeWithInvalidRange();
    }

    public static void testCopyOfRangeWithInvalidRange() {
        Integer[] original = {1, 2, 3, 4, 5};
        int from = 4;
        int to = 2; // invalid range: to < from
        try {
            Integer[] result = Arrays.copyOfRange(original, from, to);
            // If no exception, fail the test
            throw new AssertionError("Expected IllegalArgumentException was not thrown.");
        } catch (IllegalArgumentException e) {
            // Expected exception, test passes
        }
    }
}
