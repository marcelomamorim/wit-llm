/*
 * @test
 * @summary Tests observable behavior of Arrays.copyOfRange(double[], int, int)
 * @run main ArraysWitupTest
 */

import java.util.Arrays;

public class ArraysWitupTest {

    public static void main(String[] args) {
        double[] src = {1.0, 2.0, 3.0, 4.0, 5.0};

        check(Arrays.equals(new double[]{2.0, 3.0, 4.0}, Arrays.copyOfRange(src, 1, 4)),
              "middle range copy");

        check(Arrays.equals(new double[]{1.0, 2.0, 3.0, 0.0, 0.0},
                            Arrays.copyOfRange(new double[]{1.0, 2.0, 3.0}, 0, 5)),
              "range extending past original length pads with 0.0");

        check(Arrays.copyOfRange(src, 2, 2).length == 0, "from == to yields empty array");

        double[] copy = Arrays.copyOfRange(src, 0, 5);
        copy[0] = 99.0;
        check(src[0] == 1.0, "result is an independent copy");

        double[] negZero = {-0.0};
        double[] negZeroCopy = Arrays.copyOfRange(negZero, 0, 1);
        check(Double.doubleToRawLongBits(negZeroCopy[0]) == Double.doubleToRawLongBits(-0.0),
              "negative zero bits preserved");

        try {
            Arrays.copyOfRange(src, 4, 2);
            throw new RuntimeException("expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            check("4 > 2".equals(e.getMessage()), "IAE message for from > to");
        }

        try {
            Arrays.copyOfRange(null, 0, 1);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Check failed: " + message);
        }
    }
}
