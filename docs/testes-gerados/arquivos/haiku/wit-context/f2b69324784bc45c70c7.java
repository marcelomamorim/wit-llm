/*
 * @test
 * @summary WITUP-derived checks for ThreadLocalRandom.nextInt(int, int) bounds validation
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.concurrent.ThreadLocalRandom;

public class ThreadLocalRandomWitupTest {

    public static void main(String[] args) {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();

        checkIllegalArgument(rnd, 5, 5);
        checkIllegalArgument(rnd, 5, 4);
        checkIllegalArgument(rnd, 0, -1);
        checkIllegalArgument(rnd, Integer.MAX_VALUE, Integer.MIN_VALUE);

        for (int i = 0; i < 1000; i++) {
            int v = rnd.nextInt(-3, 4);
            if (v < -3 || v >= 4) {
                throw new RuntimeException("value out of range [-3,4): " + v);
            }
        }

        int single = rnd.nextInt(7, 8);
        if (single != 7) {
            throw new RuntimeException("expected 7 for range [7,8) but got " + single);
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
