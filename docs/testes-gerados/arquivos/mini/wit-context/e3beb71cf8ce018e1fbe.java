/*
 * @test
 * @summary Test java.util.Arrays.copyOfRange(double[], int, int) for valid and invalid ranges
 * @run main ArraysWitupTest
 */

public class ArraysWitupTest {
    public static void main(String[] args) {
        testCopyOfRangeValid();
        testCopyOfRangeInvalidRange();
    }

    private static void testCopyOfRangeValid() {
        double[] original = {1.0, 2.0, 3.0, 4.0, 5.0};
        // Copy full range
        double[] copyFull = java.util.Arrays.copyOfRange(original, 0, original.length);
        if (copyFull.length != original.length) {
            throw new RuntimeException("copyOfRange full length mismatch");
        }
        for (int i = 0; i < copyFull.length; i++) {
            if (copyFull[i] != original[i]) {
                throw new RuntimeException("copyOfRange full content mismatch at index " + i);
            }
        }

        // Copy subrange
        double[] copySub = java.util.Arrays.copyOfRange(original, 1, 4);
        if (copySub.length != 3) {
            throw new RuntimeException("copyOfRange subrange length mismatch");
        }
        if (copySub[0] != 2.0 || copySub[1] != 3.0 || copySub[2] != 4.0) {
            throw new RuntimeException("copyOfRange subrange content mismatch");
        }

        // Copy empty range
        double[] copyEmpty = java.util.Arrays.copyOfRange(original, 2, 2);
        if (copyEmpty.length != 0) {
            throw new RuntimeException("copyOfRange empty range length mismatch");
        }
    }

    private static void testCopyOfRangeInvalidRange() {
        double[] original = {1.0, 2.0, 3.0};
        boolean threw = false;
        try {
            // from > to triggers IllegalArgumentException
            java.util.Arrays.copyOfRange(original, 3, 2);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("copyOfRange did not throw IllegalArgumentException for from > to");
        }
    }
}
