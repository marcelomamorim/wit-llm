/*
 * @test
 * @summary WITUP-derived checks for ThreadLocalRandom.nextDouble(double bound) argument validation and bounded range
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.concurrent.ThreadLocalRandom;

public class ThreadLocalRandomWitupTest {

    public static void main(String[] args) {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();

        double[] invalidBounds = {
            0.0, -0.0, -1.0, -Double.MIN_VALUE,
            Double.NEGATIVE_INFINITY, Double.NaN
        };
        for (double bound : invalidBounds) {
            expectIllegalArgument(rnd, bound);
        }

        double[] validBounds = {
            Double.MIN_VALUE, 1e-300, 1.0, 2.5, 1e300
        };
        for (double bound : validBounds) {
            for (int i = 0; i < 1000; i++) {
                double r = rnd.nextDouble(bound);
                if (!(r >= 0.0 && r < bound)) {
                    throw new AssertionError("nextDouble(" + bound + ") returned out of range: " + r);
                }
            }
        }
    }

    private static void expectIllegalArgument(ThreadLocalRandom rnd, double bound) {
        try {
            rnd.nextDouble(bound);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new AssertionError("Expected IllegalArgumentException for bound " + bound);
    }
}
