/* @test    @summary Test for Arrays.copyOfRange(long[], int, int) method.    @run main ArraysWitupTest */

import java.util.Arrays;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testCopyOfRangeNormal();
        testCopyOfRangeEmpty();
        testCopyOfRangeFromZero();
        testCopyOfRangeToZero();
        testCopyOfRangeInvalidArguments();
    }

    static void testCopyOfRangeNormal() {
        long[] original = {10L, 20L, 30L, 40L, 50L};
        long[] result = Arrays.copyOfRange(original, 1, 4);
        long[] expected = {20L, 30L, 40L};
        if (!Arrays.equals(result, expected)) {
            throw new AssertionError("Failed testCopyOfRangeNormal: " + java.util.Arrays.toString(result));
        }
    }

    static void testCopyOfRangeEmpty() {
        long[] original = {1L, 2L, 3L};
        long[] result = Arrays.copyOfRange(original, 2, 2);
        long[] expected = {};
        if (!Arrays.equals(result, expected)) {
            throw new AssertionError("Failed testCopyOfRangeEmpty: " + java.util.Arrays.toString(result));
        }
    }

    static void testCopyOfRangeFromZero() {
        long[] original = {5L, 6L, 7L};
        long[] result = Arrays.copyOfRange(original, 0, 2);
        long[] expected = {5L, 6L};
        if (!Arrays.equals(result, expected)) {
            throw new AssertionError("Failed testCopyOfRangeFromZero: " + java.util.Arrays.toString(result));
        }
    }

    static void testCopyOfRangeToZero() {
        long[] original = {8L, 9L};
        long[] result = Arrays.copyOfRange(original, 1, 1);
        long[] expected = {};
        if (!Arrays.equals(result, expected)) {
            throw new AssertionError("Failed testCopyOfRangeToZero: " + java.util.Arrays.toString(result));
        }
    }

    static void testCopyOfRangeInvalidArguments() {
        long[] original = {1L, 2L};
        boolean exceptionThrown = false;
        try {
            Arrays.copyOfRange(original, 3, 2);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Failed testCopyOfRangeInvalidArguments: exception not thrown");
        }
    }
}
