/*
 * @test
 * @summary Deterministic tests for java.util.Arrays.copyOfRange(char[], int, int)
 * @run main ArraysWitupTest
 */

public class ArraysWitupTest {
    public static void main(String[] args) {
        testCopyOfRangeValid();
        testCopyOfRangeEmptyRange();
        testCopyOfRangeFromEqualsTo();
        testCopyOfRangeThrowsOnNegativeLength();
    }

    private static void testCopyOfRangeValid() {
        char[] original = {'a', 'b', 'c', 'd', 'e'};
        char[] result = java.util.Arrays.copyOfRange(original, 1, 4);
        if (result.length != 3) {
            throw new AssertionError("Expected length 3 but got " + result.length);
        }
        if (result[0] != 'b' || result[1] != 'c' || result[2] != 'd') {
            throw new AssertionError("Unexpected content in copied array");
        }
    }

    private static void testCopyOfRangeEmptyRange() {
        char[] original = {'x', 'y', 'z'};
        char[] result = java.util.Arrays.copyOfRange(original, 2, 2);
        if (result.length != 0) {
            throw new AssertionError("Expected empty array but got length " + result.length);
        }
    }

    private static void testCopyOfRangeFromEqualsTo() {
        char[] original = {'m', 'n', 'o'};
        char[] result = java.util.Arrays.copyOfRange(original, 0, 0);
        if (result.length != 0) {
            throw new AssertionError("Expected empty array when from == to");
        }
    }

    private static void testCopyOfRangeThrowsOnNegativeLength() {
        char[] original = {'a', 'b'};
        try {
            java.util.Arrays.copyOfRange(original, 3, 2);
            throw new AssertionError("Expected IllegalArgumentException for from > to");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
