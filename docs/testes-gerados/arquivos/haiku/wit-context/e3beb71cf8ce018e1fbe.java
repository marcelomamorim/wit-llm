/*
 * @test
 * @summary Witup-derived tests for Arrays.copyOfRange(double[], int, int)
 * @run main ArraysWitupTest
 */

import java.util.Arrays;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testNegativeLengthThrows();
        testNormalRange();
        testEmptyRange();
        testPaddingWhenFromAtEnd();
        testCopyIsIndependent();
        testPreservesSpecialValues();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNegativeLengthThrows() {
        double[] a = {1.0, 2.0, 3.0};
        try {
            Arrays.copyOfRange(a, 3, 1);
            throw new RuntimeException("expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testNormalRange() {
        double[] a = {1.0, 2.0, 3.0};
        double[] r = Arrays.copyOfRange(a, 1, 3);
        check(r.length == 2, "length");
        check(Double.compare(r[0], 2.0) == 0, "r[0]");
        check(Double.compare(r[1], 3.0) == 0, "r[1]");
    }

    static void testEmptyRange() {
        double[] a = {1.0, 2.0, 3.0};
        double[] r = Arrays.copyOfRange(a, 2, 2);
        check(r.length == 0, "empty length");
    }

    static void testPaddingWhenFromAtEnd() {
        double[] a = {1.0, 2.0, 3.0};
        double[] r = Arrays.copyOfRange(a, 3, 5);
        check(r.length == 2, "padded length");
        check(Double.compare(r[0], 0.0) == 0, "pad r[0]");
        check(Double.compare(r[1], 0.0) == 0, "pad r[1]");
    }

    static void testCopyIsIndependent() {
        double[] a = {1.0, 2.0, 3.0};
        double[] r = Arrays.copyOfRange(a, 0, 3);
        r[0] = 99.0;
        check(Double.compare(a[0], 1.0) == 0, "original modified");
    }

    static void testPreservesSpecialValues() {
        double[] b = {Double.NaN, -0.0, Double.POSITIVE_INFINITY};
        double[] r = Arrays.copyOfRange(b, 0, 3);
        check(Double.compare(r[0], Double.NaN) == 0, "NaN");
        check(Double.compare(r[1], -0.0) == 0, "-0.0");
        check(r[2] == Double.POSITIVE_INFINITY, "+Infinity");
    }
}
