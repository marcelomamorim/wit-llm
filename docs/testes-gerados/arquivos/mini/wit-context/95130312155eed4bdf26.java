/*
 * @test
 * @summary Test java.util.Arrays.copyOfRange(float[], int, int) for normal and exceptional cases
 * @run main ArraysWitupTest
 */

public class ArraysWitupTest {
    public static void main(String[] args) {
        testCopyOfRangeNormal();
        testCopyOfRangeEmptyRange();
        testCopyOfRangeFromEqualsTo();
        testCopyOfRangeThrowsIllegalArgumentException();
        System.out.println("All tests passed.");
    }

    private static void testCopyOfRangeNormal() {
        float[] original = {1.0f, 2.0f, 3.0f, 4.0f, 5.0f};
        float[] copy = java.util.Arrays.copyOfRange(original, 1, 4);
        if (copy.length != 3) {
            throw new AssertionError("Expected length 3 but got " + copy.length);
        }
        if (copy[0] != 2.0f || copy[1] != 3.0f || copy[2] != 4.0f) {
            throw new AssertionError("Copied elements do not match expected values");
        }
    }

    private static void testCopyOfRangeEmptyRange() {
        float[] original = {1.0f, 2.0f, 3.0f};
        float[] copy = java.util.Arrays.copyOfRange(original, 2, 2);
        if (copy.length != 0) {
            throw new AssertionError("Expected empty array but got length " + copy.length);
        }
    }

    private static void testCopyOfRangeFromEqualsTo() {
        float[] original = {1.0f, 2.0f};
        float[] copy = java.util.Arrays.copyOfRange(original, 0, 0);
        if (copy.length != 0) {
            throw new AssertionError("Expected empty array but got length " + copy.length);
        }
    }

    private static void testCopyOfRangeThrowsIllegalArgumentException() {
        float[] original = {1.0f, 2.0f, 3.0f};
        boolean thrown = false;
        try {
            java.util.Arrays.copyOfRange(original, 3, 2); // to - from < 0 triggers IllegalArgumentException
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IllegalArgumentException for negative length range");
        }
    }
}
