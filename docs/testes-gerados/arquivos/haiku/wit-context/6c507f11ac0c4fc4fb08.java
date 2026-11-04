/*
 * @test
 * @summary Witup-derived tests for Arrays.copyOfRange(short[], int, int)
 * @run main ArraysWitupTest
 */

import java.util.Arrays;

public class ArraysWitupTest {

    public static void main(String[] args) {
        short[] src = {1, 2, 3, 4, 5, 6};

        check(Arrays.equals(new short[]{3, 4, 5}, Arrays.copyOfRange(src, 2, 5)), "middle range");
        check(Arrays.equals(new short[0], Arrays.copyOfRange(src, 3, 3)), "empty range when from == to");
        check(Arrays.equals(new short[]{2, 3, 0, 0, 0},
                Arrays.copyOfRange(new short[]{1, 2, 3}, 1, 6)), "range extending past original is zero-padded");
        check(Arrays.equals(new short[]{1, 2, 3, 4, 5, 6},
                Arrays.copyOfRange(src, 0, src.length)), "full range copy");

        expectIAE(src, 3, 1);
        expectIAE(src, 5, 0);
        expectIAE(src, Integer.MIN_VALUE, Integer.MAX_VALUE);

        boolean npe = false;
        try {
            Arrays.copyOfRange((short[]) null, 0, 2);
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "null array must throw NullPointerException");
    }

    private static void expectIAE(short[] original, int from, int to) {
        boolean thrown = false;
        try {
            Arrays.copyOfRange(original, from, to);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "expected IllegalArgumentException for from=" + from + ", to=" + to);
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Failed: " + message);
        }
    }
}
