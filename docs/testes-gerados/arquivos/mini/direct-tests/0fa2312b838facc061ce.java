/*
 * @test
 * @summary Test java.util.Arrays.copyOfRange(long[], int, int) method
 * @run main ArraysWitupTest
 */

public class ArraysWitupTest {
    public static void main(String[] args) {
        testCopyOfRangeNormal();
        testCopyOfRangeEmptyResult();
        testCopyOfRangeFromEqualsTo();
        testCopyOfRangeFromZeroToLength();
        testCopyOfRangeThrowsIllegalArgumentException();
    }

    private static void testCopyOfRangeNormal() {
        long[] original = {10L, 20L, 30L, 40L, 50L};
        long[] result = java.util.Arrays.copyOfRange(original, 1, 4);
        if (result.length != 3) throw new RuntimeException("Length mismatch");
        if (result[0] != 20L || result[1] != 30L || result[2] != 40L) {
            throw new RuntimeException("Content mismatch");
        }
    }

    private static void testCopyOfRangeEmptyResult() {
        long[] original = {1L, 2L, 3L};
        long[] result = java.util.Arrays.copyOfRange(original, 2, 2);
        if (result.length != 0) throw new RuntimeException("Expected empty array");
    }

    private static void testCopyOfRangeFromEqualsTo() {
        long[] original = {5L, 6L, 7L};
        long[] result = java.util.Arrays.copyOfRange(original, 0, 0);
        if (result.length != 0) throw new RuntimeException("Expected empty array when from == to");
    }

    private static void testCopyOfRangeFromZeroToLength() {
        long[] original = {100L, 200L, 300L};
        long[] result = java.util.Arrays.copyOfRange(original, 0, original.length);
        if (result.length != original.length) throw new RuntimeException("Length mismatch");
        for (int i = 0; i < original.length; i++) {
            if (result[i] != original[i]) {
                throw new RuntimeException("Content mismatch at index " + i);
            }
        }
    }

    private static void testCopyOfRangeThrowsIllegalArgumentException() {
        long[] original = {1L, 2L, 3L};
        try {
            java.util.Arrays.copyOfRange(original, 3, 2);
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
