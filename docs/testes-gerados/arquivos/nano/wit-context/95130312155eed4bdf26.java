/*
 * @test
 * @summary Test for Arrays.copyOfRange(float[], int, int)
 * @run main ArraysWitupTest
 */

import java.util.Arrays;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testCopyOfRange_Normal();
        testCopyOfRange_NegativeLength();
        testCopyOfRange_FromEqualsTo();
        testCopyOfRange_FromGreaterThanTo();
        testCopyOfRange_NullOriginal();
    }

    static void testCopyOfRange_Normal() {
        float[] original = {1.0f, 2.0f, 3.0f, 4.0f, 5.0f};
        float[] result = Arrays.copyOfRange(original, 1, 4);
        if (!Arrays.equals(result, new float[]{2.0f, 3.0f, 4.0f})) {
            throw new AssertionError("Failed: Normal case");
        }
    }

    static void testCopyOfRange_NegativeLength() {
        float[] original = {1.0f, 2.0f, 3.0f};
        try {
            Arrays.copyOfRange(original, 0, -1);
            throw new AssertionError("Expected IllegalArgumentException for negative length");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }

    static void testCopyOfRange_FromEqualsTo() {
        float[] original = {1.0f, 2.0f, 3.0f};
        float[] result = Arrays.copyOfRange(original, 2, 2);
        if (result.length != 0) {
            throw new AssertionError("Failed: from equals to should produce empty array");
        }
    }

    static void testCopyOfRange_FromGreaterThanTo() {
        float[] original = {1.0f, 2.0f, 3.0f};
        try {
            Arrays.copyOfRange(original, 3, 2);
            throw new AssertionError("Expected IllegalArgumentException for from > to");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }

    static void testCopyOfRange_NullOriginal() {
        try {
            Arrays.copyOfRange(null, 0, 1);
            throw new AssertionError("Expected NullPointerException for null array");
        } catch (NullPointerException e) {
            // Expected
        }
    }
}
