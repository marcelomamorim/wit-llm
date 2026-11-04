/*
 * @test
 * @summary Verifies ThreadLocalRandom.nextDouble(double bound) argument validation and result range
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.concurrent.ThreadLocalRandom;

public class ThreadLocalRandomWitupTest {

    public static void main(String[] args) {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();

        checkRejects(rnd, 0.0);
        checkRejects(rnd, -0.0);
        checkRejects(rnd, -1.0);
        checkRejects(rnd, Double.NEGATIVE_INFINITY);
        checkRejects(rnd, Double.NaN);

        for (int i = 0; i < 10000; i++) {
            double r = rnd.nextDouble(5.0);
            if (!(r >= 0.0 && r < 5.0)) {
                throw new RuntimeException("out of range for bound 5.0: " + r);
            }
        }

        for (int i = 0; i < 10000; i++) {
            double r = rnd.nextDouble(Double.MIN_VALUE);
            if (!(r >= 0.0 && r < Double.MIN_VALUE)) {
                throw new RuntimeException("out of range for bound MIN_VALUE: " + r);
            }
        }

        for (int i = 0; i < 1000; i++) {
            double r = rnd.nextDouble(Double.POSITIVE_INFINITY);
            if (r != Double.MAX_VALUE) {
                throw new RuntimeException("expected MAX_VALUE for infinite bound but got: " + r);
            }
        }
    }

    private static void checkRejects(ThreadLocalRandom rnd, double bound) {
        try {
            rnd.nextDouble(bound);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new RuntimeException("IllegalArgumentException not thrown for bound: " + bound);
    }
}
