/* @test    @summary Test for Arrays.copyOfRange(float[] original, int from, int to) method.    @run main ArraysWitupTest */

import java.util.Arrays;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testCopyOfRange_NormalCase();
        testCopyOfRange_FromNegative();
        testCopyOfRange_ToLessThanFrom();
        testCopyOfRange_ToExceedsLength();
        testCopyOfRange_FromEqualsTo();
    }

    static void testCopyOfRange_NormalCase() {
        float[] original = {1.0f, 2.0f, 3.0f, 4.0f, 5.0f};
        float[] result = Arrays.copyOfRange(original, 1, 4);
        float[] expected = {2.0f, 3.0f, 4.0f};
        if (!Arrays.equals(result, expected)) {
            throw new AssertionError("Normal case failed: " + java.util.Arrays.toString(result));
        }
    }

    static void testCopyOfRange_FromNegative() {
        float[] original = {1.0f, 2.0f, 3.0f};
        try {
            Arrays.copyOfRange(original, -1, 2);
            throw new AssertionError("Expected IllegalArgumentException for negative from index");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testCopyOfRange_ToLessThanFrom() {
        float[] original = {1.0f, 2.0f, 3.0f};
        try {
            Arrays.copyOfRange(original, 3, 2);
            throw new AssertionError("Expected IllegalArgumentException for to less than from");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testCopyOfRange_ToExceedsLength() {
        float[] original = {1.0f, 2.0f, 3.0f};
        float[] result = Arrays.copyOfRange(original, 1, 5);
        float[] expected = {2.0f, 3.0f, 0.0f, 0.0f};
        if (!Arrays.equals(result, expected)) {
            throw new AssertionError("To exceeds length case failed: " + java.util.Arrays.toString(result));
        }
    }

    static void testCopyOfRange_FromEqualsTo() {
        float[] original = {1.0f, 2.0f, 3.0f};
        float[] result = Arrays.copyOfRange(original, 2, 2);
        float[] expected = {};
        if (!Arrays.equals(result, expected)) {
            throw new AssertionError("From equals to case failed: " + java.util.Arrays.toString(result));
        }
    }
}
