/*
 * @test
 * @summary Test java.util.Arrays.copyOfRange(short[], int, int) method
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
        short[] original = {1, 2, 3, 4, 5};
        short[] result = java.util.Arrays.copyOfRange(original, 1, 4);
        if (result.length != 3) throw new RuntimeException("Length mismatch");
        if (result[0] != 2 || result[1] != 3 || result[2] != 4) throw new RuntimeException("Content mismatch");
    }

    private static void testCopyOfRangeEmpty() {
        short[] original = {10, 20, 30};
        short[] result = java.util.Arrays.copyOfRange(original, 2, 2);
        if (result.length != 0) throw new RuntimeException("Expected empty array");
    }

    private static void testCopyOfRangeFromEqualsTo() {
        short[] original = {7, 8, 9};
        short[] result = java.util.Arrays.copyOfRange(original, 0, 0);
        if (result.length != 0) throw new RuntimeException("Expected empty array when from == to");
    }

    private static void testCopyOfRangeFromZeroToLength() {
        short[] original = {5, 6, 7, 8};
        short[] result = java.util.Arrays.copyOfRange(original, 0, original.length);
        if (result.length != original.length) throw new RuntimeException("Length mismatch");
        for (int i = 0; i < original.length; i++) {
            if (result[i] != original[i]) throw new RuntimeException("Content mismatch at index " + i);
        }
    }

    private static void testCopyOfRangeThrowsIllegalArgumentException() {
        short[] original = {1, 2, 3};
        try {
            java.util.Arrays.copyOfRange(original, 3, 2);
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
