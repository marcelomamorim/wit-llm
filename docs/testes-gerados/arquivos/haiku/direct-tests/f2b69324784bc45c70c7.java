/*
 * @test
 * @summary Tests ThreadLocalRandom.nextInt(origin, bound) range results and argument validation
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.concurrent.ThreadLocalRandom;

public class ThreadLocalRandomWitupTest {

    public static void main(String[] args) {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();

        checkIllegalArgument(rnd, 5, 5);
        checkIllegalArgument(rnd, 10, 3);
        checkIllegalArgument(rnd, 0, Integer.MIN_VALUE);

        for (int i = 0; i < 10000; i++) {
            int v = rnd.nextInt(-7, 3);
            if (v < -7 || v >= 3) {
                throw new RuntimeException("value out of range [-7,3): " + v);
            }
        }

        for (int i = 0; i < 1000; i++) {
            int v = rnd.nextInt(41, 42);
            if (v != 41) {
                throw new RuntimeException("expected 41 for range [41,42): " + v);
            }
        }

        for (int i = 0; i < 10000; i++) {
            int v = rnd.nextInt(Integer.MIN_VALUE, 0);
            if (v >= 0) {
                throw new RuntimeException("value out of range [MIN,0): " + v);
            }
        }
    }

    private static void checkIllegalArgument(ThreadLocalRandom rnd, int origin, int bound) {
        try {
            rnd.nextInt(origin, bound);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new RuntimeException("IllegalArgumentException not thrown for origin="
                + origin + ", bound=" + bound);
    }
}
