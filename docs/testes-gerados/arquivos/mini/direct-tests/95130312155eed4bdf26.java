/*
 * @test
 * @summary Test java.util.Arrays.copyOfRange(float[], int, int) method
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
        float[] original = {1.0f, 2.0f, 3.0f, 4.0f, 5.0f};
        float[] copy = java.util.Arrays.copyOfRange(original, 1, 4);
        if (copy.length != 3) throw new RuntimeException("Length mismatch");
        if (copy[0] != 2.0f || copy[1] != 3.0f || copy[2] != 4.0f) {
            throw new RuntimeException("Content mismatch");
        }
    }

    private static void testCopyOfRangeEmpty() {
        float[] original = {1.0f, 2.0f};
        float[] copy = java.util.Arrays.copyOfRange(original, 2, 2);
        if (copy.length != 0) throw new RuntimeException("Expected empty array");
    }

    private static void testCopyOfRangeFromEqualsTo() {
        float[] original = {1.0f, 2.0f, 3.0f};
        float[] copy = java.util.Arrays.copyOfRange(original, 0, 0);
        if (copy.length != 0) throw new RuntimeException("Expected empty array when from == to");
    }

    private static void testCopyOfRangeFromZeroToLength() {
        float[] original = {1.0f, 2.0f, 3.0f};
        float[] copy = java.util.Arrays.copyOfRange(original, 0, 3);
        if (copy.length != 3) throw new RuntimeException("Length mismatch");
        for (int i = 0; i < copy.length; i++) {
            if (copy[i] != original[i]) {
                throw new RuntimeException("Content mismatch at index " + i);
            }
        }
    }

    private static void testCopyOfRangeThrowsIllegalArgumentException() {
        float[] original = {1.0f, 2.0f};
        try {
            java.util.Arrays.copyOfRange(original, 2, 1);
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("2 > 1")) {
                throw new RuntimeException("Exception message incorrect: " + msg);
            }
        }
    }
}
