/*
 * @test
 * @summary Behavioral tests for Arrays.copyOfRange(byte[], int, int)
 * @run main ArraysWitupTest
 */

import java.util.Arrays;

public class ArraysWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testCopyWithinBounds() {
        byte[] original = {1, 2, 3, 4, 5};
        byte[] result = Arrays.copyOfRange(original, 0, 3);
        check(Arrays.equals(result, new byte[] {1, 2, 3}), "prefix copy failed");
        check(result != original, "result must be a new array");
    }

    static void testMiddleRange() {
        byte[] original = {10, 20, 30, 40, 50};
        byte[] result = Arrays.copyOfRange(original, 1, 4);
        check(Arrays.equals(result, new byte[] {20, 30, 40}), "middle range copy failed");
    }

    static void testEmptyRange() {
        byte[] original = {1, 2, 3};
        byte[] result = Arrays.copyOfRange(original, 2, 2);
        check(result.length == 0, "empty range must yield zero length");
    }

    static void testToBeyondLengthPadsWithZeros() {
        byte[] original = {7, 8, 9};
        byte[] result = Arrays.copyOfRange(original, 1, 5);
        check(Arrays.equals(result, new byte[] {8, 9, 0, 0}), "padding with zeros failed");
    }

    static void testFromEqualsLengthPadsWithZeros() {
        byte[] original = {7, 8, 9};
        byte[] result = Arrays.copyOfRange(original, 3, 5);
        check(Arrays.equals(result, new byte[] {0, 0}), "copy starting at length must be zeros");
    }

    static void testFromGreaterThanToThrowsIAE() {
        byte[] original = {1, 2, 3, 4, 5};
        try {
            Arrays.copyOfRange(original, 5, 2);
            throw new RuntimeException("expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            check("5 > 2".equals(e.getMessage()), "unexpected message: " + e.getMessage());
        }
    }

    static void testNullArrayThrowsNPE() {
        try {
            Arrays.copyOfRange((byte[]) null, 0, 1);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }
    }

    public static void main(String[] args) {
        testCopyWithinBounds();
        testMiddleRange();
        testEmptyRange();
        testToBeyondLengthPadsWithZeros();
        testFromEqualsLengthPadsWithZeros();
        testFromGreaterThanToThrowsIAE();
        testNullArrayThrowsNPE();
    }
}
