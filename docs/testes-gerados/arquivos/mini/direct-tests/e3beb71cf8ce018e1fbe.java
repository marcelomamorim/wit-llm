/*
 * @test
 * @summary Test java.util.Arrays.copyOfRange(double[], int, int) method
 * @run main ArraysWitupTest
 */

public class ArraysWitupTest {
    public static void main(String[] args) {
        testCopyOfRangeNormal();
        testCopyOfRangeEmpty();
        testCopyOfRangeFromEqualsTo();
        testCopyOfRangeFromGreaterThanTo();
        testCopyOfRangeFromZeroToLength();
    }

    private static void testCopyOfRangeNormal() {
        double[] original = {1.0, 2.0, 3.0, 4.0, 5.0};
        double[] copy = java.util.Arrays.copyOfRange(original, 1, 4);
        if (copy.length != 3) throw new RuntimeException("Length mismatch");
        if (copy[0] != 2.0 || copy[1] != 3.0 || copy[2] != 4.0) throw new RuntimeException("Content mismatch");
    }

    private static void testCopyOfRangeEmpty() {
        double[] original = {1.0, 2.0, 3.0};
        double[] copy = java.util.Arrays.copyOfRange(original, 2, 2);
        if (copy.length != 0) throw new RuntimeException("Expected empty array");
    }

    private static void testCopyOfRangeFromEqualsTo() {
        double[] original = {1.0, 2.0};
        double[] copy = java.util.Arrays.copyOfRange(original, 0, 0);
        if (copy.length != 0) throw new RuntimeException("Expected empty array when from == to");
    }

    private static void testCopyOfRangeFromGreaterThanTo() {
        double[] original = {1.0, 2.0, 3.0};
        try {
            java.util.Arrays.copyOfRange(original, 3, 2);
            throw new RuntimeException("Expected IllegalArgumentException for from > to");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testCopyOfRangeFromZeroToLength() {
        double[] original = {10.0, 20.0, 30.0};
        double[] copy = java.util.Arrays.copyOfRange(original, 0, original.length);
        if (copy.length != original.length) throw new RuntimeException("Length mismatch");
        for (int i = 0; i < copy.length; i++) {
            if (copy[i] != original[i]) throw new RuntimeException("Content mismatch at index " + i);
        }
    }
}
