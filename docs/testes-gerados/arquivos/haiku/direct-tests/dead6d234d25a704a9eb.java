/*
 * @test
 * @summary Verify Arrays.equals(double[], double[]) for identity, null, length, content and double bitwise semantics
 * @run main ArraysWitupTest
 */

import java.util.Arrays;

public class ArraysWitupTest {
    static int failures = 0;

    static void check(boolean cond, String msg) {
        if (!cond) {
            failures++;
            System.err.println("FAIL: " + msg);
        }
    }

    public static void main(String[] args) {
        double[] a = {1.0, 2.5, -3.0};

        check(Arrays.equals(a, a), "same reference must be equal");
        check(Arrays.equals(a, new double[]{1.0, 2.5, -3.0}), "equal contents must be equal");
        check(!Arrays.equals(a, new double[]{1.0, 2.5, -3.1}), "different last element must not be equal");
        check(!Arrays.equals(a, new double[]{1.0, 2.5}), "different length must not be equal");
        check(!Arrays.equals(null, a), "null first argument must not be equal");
        check(!Arrays.equals(a, null), "null second argument must not be equal");
        check(Arrays.equals((double[]) null, (double[]) null), "two null references must be equal");
        check(Arrays.equals(new double[0], new double[0]), "two empty arrays must be equal");
        check(Arrays.equals(new double[]{Double.NaN}, new double[]{Double.NaN}), "NaN must equal NaN");
        check(!Arrays.equals(new double[]{0.0}, new double[]{-0.0}), "0.0 and -0.0 must not be equal");
        check(!Arrays.equals(new double[]{1.0, 2.0}, new double[]{2.0, 1.0}), "element order must matter");

        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
