/*
 * @test
 * @summary Test java.util.Arrays.copyOfRange(byte[], int, int) for correctness and exceptions
 * @run main ArraysWitupTest
 */

public class ArraysWitupTest {
    public static void main(String[] args) {
        testCopyOfRangeNormal();
        testCopyOfRangeEmpty();
        testCopyOfRangeFromEqualsTo();
        testCopyOfRangeFromGreaterThanTo();
        testCopyOfRangeFromZeroToLength();
        testCopyOfRangePartialCopy();
    }

    private static void testCopyOfRangeNormal() {
        byte[] original = {10, 20, 30, 40, 50};
        byte[] copy = java.util.Arrays.copyOfRange(original, 1, 4);
        if (copy.length != 3) throw new RuntimeException("Length mismatch");
        if (copy[0] != 20 || copy[1] != 30 || copy[2] != 40) throw new RuntimeException("Content mismatch");
    }

    private static void testCopyOfRangeEmpty() {
        byte[] original = {1, 2, 3};
        byte[] copy = java.util.Arrays.copyOfRange(original, 2, 2);
        if (copy.length != 0) throw new RuntimeException("Expected empty array");
    }

    private static void testCopyOfRangeFromEqualsTo() {
        byte[] original = {5, 6, 7};
        byte[] copy = java.util.Arrays.copyOfRange(original, 0, 0);
        if (copy.length != 0) throw new RuntimeException("Expected empty array when from == to");
    }

    private static void testCopyOfRangeFromGreaterThanTo() {
        byte[] original = {1, 2, 3};
        try {
            java.util.Arrays.copyOfRange(original, 3, 2);
            throw new RuntimeException("Expected IllegalArgumentException for from > to");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testCopyOfRangeFromZeroToLength() {
        byte[] original = {7, 8, 9};
        byte[] copy = java.util.Arrays.copyOfRange(original, 0, 3);
        if (copy.length != 3) throw new RuntimeException("Length mismatch");
        for (int i = 0; i < copy.length; i++) {
            if (copy[i] != original[i]) throw new RuntimeException("Content mismatch at index " + i);
        }
    }

    private static void testCopyOfRangePartialCopy() {
        byte[] original = {1, 2, 3, 4, 5};
        byte[] copy = java.util.Arrays.copyOfRange(original, 2, 7);
        if (copy.length != 5) throw new RuntimeException("Length mismatch when to > original.length");
        if (copy[0] != 3 || copy[1] != 4 || copy[2] != 5) throw new RuntimeException("Content mismatch in partial copy");
        for (int i = 3; i < copy.length; i++) {
            if (copy[i] != 0) throw new RuntimeException("Expected zero padding at index " + i);
        }
    }
}
