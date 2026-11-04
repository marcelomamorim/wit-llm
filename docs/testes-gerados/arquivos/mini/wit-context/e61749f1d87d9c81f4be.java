/*
 * @test
 * @summary Test java.util.Arrays.copyOfRange(T[] original, int from, int to) for basic correctness and exception
 * @run main ArraysWitupTest
 */

import java.util.Arrays;

public class ArraysWitupTest {
    public static void main(String[] args) {
        testCopyOfRangeBasic();
        testCopyOfRangeEmptyResult();
        testCopyOfRangeNegativeLengthThrows();
        testCopyOfRangeFromEqualsTo();
    }

    private static void testCopyOfRangeBasic() {
        Integer[] original = {1, 2, 3, 4, 5};
        Integer[] copy = Arrays.copyOfRange(original, 1, 4);
        if (copy.length != 3) {
            throw new AssertionError("Expected length 3 but got " + copy.length);
        }
        if (copy[0] != 2 || copy[1] != 3 || copy[2] != 4) {
            throw new AssertionError("Copied elements do not match expected values");
        }
    }

    private static void testCopyOfRangeEmptyResult() {
        String[] original = {"a", "b", "c"};
        String[] copy = Arrays.copyOfRange(original, 2, 2);
        if (copy.length != 0) {
            throw new AssertionError("Expected empty array but got length " + copy.length);
        }
    }

    private static void testCopyOfRangeNegativeLengthThrows() {
        String[] original = {"x", "y"};
        try {
            Arrays.copyOfRange(original, 3, 2);
            throw new AssertionError("Expected IllegalArgumentException for to - from < 0");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testCopyOfRangeFromEqualsTo() {
        Double[] original = {1.0, 2.0, 3.0};
        Double[] copy = Arrays.copyOfRange(original, 1, 1);
        if (copy.length != 0) {
            throw new AssertionError("Expected empty array when from == to");
        }
    }
}
