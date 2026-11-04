/*
 * @test
 * @summary Test java.util.Arrays.copyOfRange(char[], int, int) method
 * @run main ArraysWitupTest
 */
public class ArraysWitupTest {
    public static void main(String[] args) {
        testCopyOfRangeNormal();
        testCopyOfRangeEmptyResult();
        testCopyOfRangeFromEqualsTo();
        testCopyOfRangeFromZeroToLength();
        testCopyOfRangeThrowsOnNegativeLength();
    }

    private static void testCopyOfRangeNormal() {
        char[] original = {'a', 'b', 'c', 'd', 'e'};
        char[] result = java.util.Arrays.copyOfRange(original, 1, 4);
        if (result.length != 3) throw new RuntimeException("Length mismatch");
        if (result[0] != 'b' || result[1] != 'c' || result[2] != 'd')
            throw new RuntimeException("Content mismatch");
    }

    private static void testCopyOfRangeEmptyResult() {
        char[] original = {'x', 'y', 'z'};
        char[] result = java.util.Arrays.copyOfRange(original, 2, 2);
        if (result.length != 0) throw new RuntimeException("Expected empty array");
    }

    private static void testCopyOfRangeFromEqualsTo() {
        char[] original = {'m', 'n', 'o'};
        char[] result = java.util.Arrays.copyOfRange(original, 3, 3);
        if (result.length != 0) throw new RuntimeException("Expected empty array when from == to at end");
    }

    private static void testCopyOfRangeFromZeroToLength() {
        char[] original = {'p', 'q', 'r', 's'};
        char[] result = java.util.Arrays.copyOfRange(original, 0, original.length);
        if (result.length != original.length) throw new RuntimeException("Length mismatch when copying full array");
        for (int i = 0; i < original.length; i++) {
            if (result[i] != original[i]) throw new RuntimeException("Content mismatch at index " + i);
        }
    }

    private static void testCopyOfRangeThrowsOnNegativeLength() {
        char[] original = {'a', 'b'};
        try {
            java.util.Arrays.copyOfRange(original, 2, 1);
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
