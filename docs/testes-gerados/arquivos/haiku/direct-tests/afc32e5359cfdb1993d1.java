/*
 * @test
 * @summary Verifies Arrays.fill(double[], double) assigns val to every element
 * @run main ArraysWitupTest
 */

import java.util.Arrays;

public class ArraysWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    public static void main(String[] args) {
        double[] a = new double[5];
        Arrays.fill(a, 2.5);
        for (double d : a) {
            check(Double.compare(d, 2.5) == 0, "all elements must equal 2.5");
        }

        double[] empty = new double[0];
        Arrays.fill(empty, 1.0);
        check(empty.length == 0, "empty array must remain empty");

        double[] nan = new double[3];
        Arrays.fill(nan, Double.NaN);
        for (double d : nan) {
            check(Double.isNaN(d), "all elements must be NaN");
        }

        double[] negZero = new double[2];
        Arrays.fill(negZero, -0.0);
        for (double d : negZero) {
            check(Double.compare(d, -0.0) == 0, "negative zero must be stored as -0.0");
        }

        double[] overwrite = {1.0, 2.0, 3.0};
        Arrays.fill(overwrite, 9.0);
        check(Double.compare(overwrite[0], 9.0) == 0 && Double.compare(overwrite[1], 9.0) == 0
                && Double.compare(overwrite[2], 9.0) == 0, "existing values must be overwritten");

        boolean npe = false;
        try {
            Arrays.fill((double[]) null, 1.0);
        } catch (NullPointerException expected) {
            npe = true;
        }
        check(npe, "null array must throw NullPointerException");
    }
}
