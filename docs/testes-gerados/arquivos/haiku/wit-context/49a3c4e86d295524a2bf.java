/*
 * @test
 * @summary Witup-derived tests for ThreadLocalRandom.nextLong(long, long) bound validation and range
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.concurrent.ThreadLocalRandom;

public class ThreadLocalRandomWitupTest {

    public static void main(String[] args) {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();

        expectIllegalArgument(rnd, 5L, 5L);
        expectIllegalArgument(rnd, 10L, 3L);
        expectIllegalArgument(rnd, Long.MAX_VALUE, Long.MIN_VALUE);

        for (int i = 0; i < 1000; i++) {
            long v = rnd.nextLong(-7L, -3L);
            if (v < -7L || v >= -3L) {
                throw new RuntimeException("value out of range [-7,-3): " + v);
            }
        }

        for (int i = 0; i < 1000; i++) {
            long v = rnd.nextLong(Long.MIN_VALUE, Long.MAX_VALUE);
            if (v < Long.MIN_VALUE || v >= Long.MAX_VALUE) {
                throw new RuntimeException("value out of full range: " + v);
            }
        }

        long single = rnd.nextLong(42L, 43L);
        if (single != 42L) {
            throw new RuntimeException("expected 42 for bound origin+1 but got " + single);
        }
    }

    private static void expectIllegalArgument(ThreadLocalRandom rnd, long origin, long bound) {
        try {
            rnd.nextLong(origin, bound);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("expected IllegalArgumentException for origin=" + origin + " bound=" + bound);
    }
}
