/*
 * @test
 * @summary Test for Arrays.copyOfRange(double[], int, int) method.
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
        double[] original = {1.0, 2.0, 3.0, 4.0, 5.0};
        double[] result = Arrays.copyOfRange(original, 1, 4);
        if (!Arrays.equals(result, new double[]{2.0, 3.0, 4.0})) {
            throw new AssertionError("Failed: Normal case");
        }
    }

    static void testCopyOfRange_NegativeLength() {
        double[] original = {1.0, 2.0, 3.0};
        try {
            Arrays.copyOfRange(original, 0, -1);
            throw new AssertionError("Expected IllegalArgumentException for negative length");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testCopyOfRange_FromEqualsTo() {
        double[] original = {1.0, 2.0, 3.0};
        double[] result = Arrays.copyOfRange(original, 2, 2);
        if (result.length != 0) {
            throw new AssertionError("Failed: from equals to should produce empty array");
        }
    }

    static void testCopyOfRange_FromGreaterThanTo() {
        double[] original = {1.0, 2.0, 3.0};
        try {
            Arrays.copyOfRange(original, 3, 2);
            throw new AssertionError("Expected IllegalArgumentException for from > to");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testCopyOfRange_NullOriginal() {
        try {
            Arrays.copyOfRange(null, 0, 1);
            throw new AssertionError("Expected NullPointerException for null array");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
