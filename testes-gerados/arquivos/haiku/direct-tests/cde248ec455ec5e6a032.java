/*
 * @test
 * @summary Behavioral tests for Arrays.equals(float[], float[])
 * @run main ArraysWitupTest
 */

import java.util.Arrays;

public class ArraysWitupTest {

    static int failures = 0;

    static void check(boolean condition, String message) {
        if (!condition) {
            failures++;
            System.err.println("FAILED: " + message);
        }
    }

    public static void main(String[] args) {
        float[] a = {1.0f, 2.5f, -3.0f};
        float[] same = a;
        float[] copy = {1.0f, 2.5f, -3.0f};

        check(Arrays.equals(a, same), "same reference must be equal");
        check(Arrays.equals(a, copy), "equal contents must be equal");
        check(Arrays.equals(copy, a), "equals must be symmetric");

        check(Arrays.equals(null, null), "null, null must be equal");
        check(!Arrays.equals(a, null), "array vs null must not be equal");
        check(!Arrays.equals(null, a), "null vs array must not be equal");

        check(Arrays.equals(new float[0], new float[0]), "empty arrays must be equal");
        check(!Arrays.equals(new float[0], a), "empty vs non-empty must not be equal");

        float[] shorter = {1.0f, 2.5f};
        check(!Arrays.equals(a, shorter), "different lengths must not be equal");
        check(!Arrays.equals(shorter, a), "different lengths (reversed) must not be equal");

        float[] lastDiff = {1.0f, 2.5f, -3.5f};
        check(!Arrays.equals(a, lastDiff), "difference in last element must be detected");

        float[] firstDiff = {0.0f, 2.5f, -3.0f};
        check(!Arrays.equals(a, firstDiff), "difference in first element must be detected");

        float[] nan1 = {Float.NaN};
        float[] nan2 = {Float.NaN};
        check(Arrays.equals(nan1, nan2), "NaN must equal NaN per floatToIntBits contract");

        float[] posZero = {0.0f};
        float[] negZero = {-0.0f};
        check(!Arrays.equals(posZero, negZero), "0.0f and -0.0f must not be equal per floatToIntBits contract");

        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
