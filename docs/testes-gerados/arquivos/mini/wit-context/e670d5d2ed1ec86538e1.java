/*
 * @test
 * @summary Test java.util.Arrays.copyOfRange(byte[], int, int) for valid and invalid ranges
 * @run main ArraysWitupTest
 */

import java.util.Arrays;

public class ArraysWitupTest {
    public static void main(String[] args) {
        testCopyOfRangeValid();
        testCopyOfRangeEmptyRange();
        testCopyOfRangeInvalidRange();
        testCopyOfRangeFromEqualsTo();
        testCopyOfRangeFromZeroToLength();
    }

    private static void testCopyOfRangeValid() {
        byte[] original = {1, 2, 3, 4, 5};
        byte[] result = Arrays.copyOfRange(original, 1, 4);
        if (result.length != 3) {
            throw new RuntimeException("Expected length 3 but got " + result.length);
        }
        if (result[0] != 2 || result[1] != 3 || result[2] != 4) {
            throw new RuntimeException("Unexpected content in copied array");
        }
    }

    private static void testCopyOfRangeEmptyRange() {
        byte[] original = {10, 20, 30};
        byte[] result = Arrays.copyOfRange(original, 2, 2);
        if (result.length != 0) {
            throw new RuntimeException("Expected empty array but got length " + result.length);
        }
    }

    private static void testCopyOfRangeInvalidRange() {
        byte[] original = {1, 2, 3};
        try {
            Arrays.copyOfRange(original, 3, 2);
            throw new RuntimeException("Expected IllegalArgumentException for from > to");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testCopyOfRangeFromEqualsTo() {
        byte[] original = {7, 8, 9};
        byte[] result = Arrays.copyOfRange(original, 1, 1);
        if (result.length != 0) {
            throw new RuntimeException("Expected empty array when from == to");
        }
    }

    private static void testCopyOfRangeFromZeroToLength() {
        byte[] original = {5, 6, 7, 8};
        byte[] result = Arrays.copyOfRange(original, 0, original.length);
        if (result.length != original.length) {
            throw new RuntimeException("Expected full copy length " + original.length + " but got " + result.length);
        }
        for (int i = 0; i < original.length; i++) {
            if (result[i] != original[i]) {
                throw new RuntimeException("Mismatch at index " + i);
            }
        }
    }
}
