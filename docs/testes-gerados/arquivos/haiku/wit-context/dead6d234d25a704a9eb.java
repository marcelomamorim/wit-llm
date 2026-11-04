/*
 * @test
 * @summary Witup-derived checks for Arrays.equals(double[], double[])
 * @run main ArraysWitupTest
 */

import java.util.Arrays;

public class ArraysWitupTest {

    private static int failures = 0;

    private static void check(boolean cond, String msg) {
        if (!cond) {
            failures++;
            System.err.println("FAILED: " + msg);
        }
    }

    public static void main(String[] args) {
        double[] a = {1.0, 2.0, 3.0};
        double[] same = {1.0, 2.0, 3.0};
        double[] diffLast = {1.0, 2.0, 4.0};
        double[] shorter = {1.0, 2.0};
        double[] empty1 = {};
        double[] empty2 = {};
        double[] nan1 = {Double.NaN};
        double[] nan2 = {Double.NaN};
        double[] posZero = {0.0};
        double[] negZero = {-0.0};

        check(Arrays.equals((double[]) null, (double[]) null), "null,null should be true");
        check(!Arrays.equals(a, null), "array,null should be false");
        check(!Arrays.equals(null, a), "null,array should be false");
        check(Arrays.equals(a, a), "same reference should be true");
        check(Arrays.equals(a, same), "equal contents should be true");
        check(!Arrays.equals(a, diffLast), "differing last element should be false");
        check(!Arrays.equals(a, shorter), "different lengths should be false");
        check(Arrays.equals(empty1, empty2), "two empty arrays should be true");
        check(Arrays.equals(nan1, nan2), "NaN should equal NaN via bitwise comparison");
        check(!Arrays.equals(posZero, negZero), "0.0 and -0.0 should be different");

        if (failures > 0) {
            throw new RuntimeException(failures + " failures");
        }
    }
}
