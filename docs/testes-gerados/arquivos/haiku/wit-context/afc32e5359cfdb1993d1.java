/*
 * @test
 * @summary Behavioral tests for Arrays.fill(double[], double)
 * @run main ArraysWitupTest
 */

import java.util.Arrays;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testFillsAllElements();
        testEmptyArrayIsNoOp();
        testOverwritesExistingValues();
        testNaNValueIsStored();
        testNegativeZeroIsPreserved();
        testNullArrayThrowsNullPointerException();
    }

    private static void testFillsAllElements() {
        double[] a = new double[5];
        Arrays.fill(a, 2.5);
        for (int i = 0; i < a.length; i++) {
            check(a[i] == 2.5, "element " + i + " expected 2.5 but was " + a[i]);
        }
    }

    private static void testEmptyArrayIsNoOp() {
        double[] a = new double[0];
        Arrays.fill(a, 1.0);
        check(a.length == 0, "empty array length changed");
    }

    private static void testOverwritesExistingValues() {
        double[] a = {1.0, -3.0, 7.5, 0.0};
        Arrays.fill(a, -9.25);
        for (int i = 0; i < a.length; i++) {
            check(a[i] == -9.25, "element " + i + " not overwritten, was " + a[i]);
        }
    }

    private static void testNaNValueIsStored() {
        double[] a = new double[3];
        Arrays.fill(a, Double.NaN);
        for (int i = 0; i < a.length; i++) {
            check(Double.isNaN(a[i]), "element " + i + " expected NaN");
        }
    }

    private static void testNegativeZeroIsPreserved() {
        double[] a = new double[4];
        Arrays.fill(a, -0.0);
        long negZeroBits = Double.doubleToRawLongBits(-0.0);
        for (int i = 0; i < a.length; i++) {
            check(Double.doubleToRawLongBits(a[i]) == negZeroBits,
                  "element " + i + " lost sign of negative zero");
        }
    }

    private static void testNullArrayThrowsNullPointerException() {
        boolean thrown = false;
        try {
            Arrays.fill((double[]) null, 1.0);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null array");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
