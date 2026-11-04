/*
 * @test
 * @summary Behavioral tests for Arrays.copyOfRange(short[], int, int)
 * @run main ArraysWitupTest
 */

import java.util.Arrays;

public class ArraysWitupTest {

    public static void main(String[] args) {
        short[] src = {1, 2, 3, 4, 5};

        // Range fully inside the source array
        short[] mid = Arrays.copyOfRange(src, 1, 4);
        check(Arrays.equals(new short[]{2, 3, 4}, mid), "inside range");

        // from == to yields an empty array
        short[] empty = Arrays.copyOfRange(src, 2, 2);
        check(empty.length == 0, "from == to must give empty array");

        // to beyond original length pads with zeros
        short[] padded = Arrays.copyOfRange(src, 3, 8);
        check(Arrays.equals(new short[]{4, 5, 0, 0, 0}, padded), "padding with zeros");

        // from > to throws IllegalArgumentException with message "from > to"
        try {
            Arrays.copyOfRange(src, 4, 2);
            throw new RuntimeException("expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            check("4 > 2".equals(e.getMessage()), "IAE message: " + e.getMessage());
        }

        // from beyond source length (from == to) fails in arraycopy
        try {
            Arrays.copyOfRange(src, 6, 6);
            throw new RuntimeException("expected ArrayIndexOutOfBoundsException");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }

        // Result must be a fresh array, not a view of the source
        short[] copy = Arrays.copyOfRange(src, 0, 5);
        copy[0] = 99;
        check(src[0] == 1, "source must not be modified");
        check(copy != src, "result must be a new array");

        // Empty source with empty range
        short[] emptySrc = Arrays.copyOfRange(new short[0], 0, 0);
        check(emptySrc.length == 0, "empty source with empty range");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
