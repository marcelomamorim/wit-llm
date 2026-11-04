/*
 * @test
 * @summary Test java.util.Arrays.copyOfRange(long[], int, int) for normal and exceptional cases
 * @run main ArraysWitupTest
 */

public class ArraysWitupTest {
    public static void main(String[] args) {
        testCopyOfRangeNormal();
        testCopyOfRangeEmptyRange();
        testCopyOfRangeIllegalArgumentException();
    }

    private static void testCopyOfRangeNormal() {
        long[] original = {10L, 20L, 30L, 40L, 50L};
        int from = 1;
        int to = 4;
        long[] result = java.util.Arrays.copyOfRange(original, from, to);
        if (result.length != (to - from)) {
            throw new AssertionError("Length mismatch: expected " + (to - from) + " but got " + result.length);
        }
        for (int i = 0; i < result.length; i++) {
            if (result[i] != original[from + i]) {
                throw new AssertionError("Element mismatch at index " + i + ": expected " + original[from + i] + " but got " + result[i]);
            }
        }
    }

    private static void testCopyOfRangeEmptyRange() {
        long[] original = {1L, 2L, 3L};
        int from = 2;
        int to = 2;
        long[] result = java.util.Arrays.copyOfRange(original, from, to);
        if (result.length != 0) {
            throw new AssertionError("Expected empty array but got length " + result.length);
        }
    }

    private static void testCopyOfRangeIllegalArgumentException() {
        long[] original = {1L, 2L, 3L};
        int from = 3;
        int to = 2; // to < from triggers IllegalArgumentException
        boolean thrown = false;
        try {
            java.util.Arrays.copyOfRange(original, from, to);
        } catch (IllegalArgumentException e) {
            thrown = true;
            String msg = e.getMessage();
            if (!msg.contains("3") || !msg.contains("2")) {
                throw new AssertionError("Exception message does not contain indices: " + msg);
            }
        }
        if (!thrown) {
            throw new AssertionError("Expected IllegalArgumentException was not thrown");
        }
    }
}
