/*
 * @test
 * @summary Test java.util.Arrays.copyOfRange(int[], int, int) for correctness and exceptions
 * @run main ArraysWitupTest
 */

public class ArraysWitupTest {
    public static void main(String[] args) {
        testCopyOfRangeNormal();
        testCopyOfRangeEmpty();
        testCopyOfRangeFromEqualsTo();
        testCopyOfRangeFromZeroToLength();
        testCopyOfRangeThrowsIllegalArgumentException();
    }

    private static void testCopyOfRangeNormal() {
        int[] original = {10, 20, 30, 40, 50};
        int[] copy = java.util.Arrays.copyOfRange(original, 1, 4);
        if (copy.length != 3) throw new RuntimeException("Length mismatch");
        if (copy[0] != 20 || copy[1] != 30 || copy[2] != 40) throw new RuntimeException("Content mismatch");
    }

    private static void testCopyOfRangeEmpty() {
        int[] original = {1, 2, 3};
        int[] copy = java.util.Arrays.copyOfRange(original, 2, 2);
        if (copy.length != 0) throw new RuntimeException("Expected empty array");
    }

    private static void testCopyOfRangeFromEqualsTo() {
        int[] original = {5, 6, 7};
        int[] copy = java.util.Arrays.copyOfRange(original, 0, 0);
        if (copy.length != 0) throw new RuntimeException("Expected empty array when from == to");
    }

    private static void testCopyOfRangeFromZeroToLength() {
        int[] original = {7, 8, 9};
        int[] copy = java.util.Arrays.copyOfRange(original, 0, original.length);
        if (copy.length != original.length) throw new RuntimeException("Length mismatch");
        for (int i = 0; i < copy.length; i++) {
            if (copy[i] != original[i]) throw new RuntimeException("Content mismatch at index " + i);
        }
    }

    private static void testCopyOfRangeThrowsIllegalArgumentException() {
        int[] original = {1, 2, 3};
        boolean thrown = false;
        try {
            java.util.Arrays.copyOfRange(original, 3, 2);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) throw new RuntimeException("Expected IllegalArgumentException for from > to");
    }
}
