/*
 * @test
 * @summary Test java.util.Arrays.copyOfRange(U[] original, int from, int to, Class<? extends T[]> newType) for correct behavior and exception
 * @run main ArraysWitupTest
 */

import java.lang.reflect.Array;
import java.util.Arrays;

public class ArraysWitupTest {
    public static void main(String[] args) {
        testCopyOfRangeNormal();
        testCopyOfRangeEmptyRange();
        testCopyOfRangeThrowsIllegalArgumentException();
    }

    private static void testCopyOfRangeNormal() {
        String[] original = new String[] {"a", "b", "c", "d", "e"};
        String[] copy = Arrays.copyOfRange(original, 1, 4, String[].class);
        if (copy.length != 3) {
            throw new AssertionError("Expected length 3 but got " + copy.length);
        }
        if (!"b".equals(copy[0]) || !"c".equals(copy[1]) || !"d".equals(copy[2])) {
            throw new AssertionError("Copied elements do not match expected values");
        }
    }

    private static void testCopyOfRangeEmptyRange() {
        Integer[] original = new Integer[] {1, 2, 3};
        Integer[] copy = Arrays.copyOfRange(original, 2, 2, Integer[].class);
        if (copy.length != 0) {
            throw new AssertionError("Expected empty array but got length " + copy.length);
        }
    }

    private static void testCopyOfRangeThrowsIllegalArgumentException() {
        try {
            Integer[] original = new Integer[] {1, 2, 3};
            Arrays.copyOfRange(original, 3, 2, Integer[].class);
            throw new AssertionError("Expected IllegalArgumentException for from > to");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
