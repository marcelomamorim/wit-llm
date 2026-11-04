/*
 * @test
 * @summary Behavioral checks for Arrays.equals(float[], float[]) including null, length, NaN and signed zero handling
 * @run main ArraysWitupTest
 */

import java.util.Arrays;

public class ArraysWitupTest {

    static int failures = 0;

    static void check(boolean cond, int id) {
        if (!cond) {
            failures++;
            System.err.println("case " + id + " failed");
        }
    }

    public static void main(String[] args) {
        float[] nul = null;
        check(Arrays.equals(nul, nul), 1);
        check(!Arrays.equals(nul, new float[0]), 2);
        check(!Arrays.equals(new float[0], nul), 3);

        float[] same = {1.0f, 2.0f, 3.0f};
        check(Arrays.equals(same, same), 4);

        check(Arrays.equals(new float[0], new float[0]), 5);
        check(Arrays.equals(new float[]{1.0f, 2.0f, 3.0f}, new float[]{1.0f, 2.0f, 3.0f}), 6);
        check(!Arrays.equals(new float[]{1.0f, 2.0f, 3.0f}, new float[]{1.0f, 9.0f, 3.0f}), 7);
        check(!Arrays.equals(new float[]{1.0f, 2.0f, 3.0f}, new float[]{4.0f, 2.0f, 3.0f}), 8);
        check(!Arrays.equals(new float[]{1.0f, 2.0f}, new float[]{1.0f, 2.0f, 3.0f}), 9);
        check(!Arrays.equals(new float[]{1.0f, 2.0f, 3.0f}, new float[]{1.0f, 2.0f}), 10);

        check(Arrays.equals(new float[]{Float.NaN}, new float[]{Float.NaN}), 11);
        check(!Arrays.equals(new float[]{0.0f}, new float[]{-0.0f}), 12);
        check(!Arrays.equals(new float[]{-0.0f}, new float[]{0.0f}), 13);

        if (failures > 0) {
            throw new RuntimeException("failures: " + failures);
        }
    }
}
