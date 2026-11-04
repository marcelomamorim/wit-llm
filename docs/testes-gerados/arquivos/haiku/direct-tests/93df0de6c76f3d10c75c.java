/*
 * @test
 * @summary Tests ThreadLocalRandom.nextDouble(origin, bound) range results and argument validation
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.concurrent.ThreadLocalRandom;

public class ThreadLocalRandomWitupTest {

    public static void main(String[] args) {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();

        testRange(rnd, 1.5, 2.5);
        testRange(rnd, -10.0, -5.0);
        testRange(rnd, -1.0, 1.0);

        expectIllegalArgument(rnd, 2.0, 2.0);
        expectIllegalArgument(rnd, 3.0, 2.0);
        expectIllegalArgument(rnd, Double.NaN, 1.0);
        expectIllegalArgument(rnd, 1.0, Double.NaN);
    }

    static void testRange(ThreadLocalRandom rnd, double origin, double bound) {
        for (int i = 0; i < 1000; i++) {
            double v = rnd.nextDouble(origin, bound);
            if (v < origin || v >= bound) {
                throw new RuntimeException("Value " + v + " outside [" + origin + ", " + bound + ")");
            }
        }
    }

    static void expectIllegalArgument(ThreadLocalRandom rnd, double origin, double bound) {
        try {
            rnd.nextDouble(origin, bound);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("Expected IllegalArgumentException for origin=" + origin + ", bound=" + bound);
    }
}
