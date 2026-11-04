/*
 * @test
 * @summary ThreadLocalRandom.nextLong(long) rejects non-positive bounds and returns values within [0, bound)
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.concurrent.ThreadLocalRandom;

public class ThreadLocalRandomWitupTest {
    public static void main(String[] args) {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();

        checkIllegalArgument(rnd, 0L);
        checkIllegalArgument(rnd, -1L);
        checkIllegalArgument(rnd, Long.MIN_VALUE);

        long[] bounds = {1L, 2L, 3L, 7L, 16L, 1000L, 1L << 40, Long.MAX_VALUE};
        for (long bound : bounds) {
            for (int i = 0; i < 1000; i++) {
                long v = rnd.nextLong(bound);
                if (v < 0L || v >= bound) {
                    throw new RuntimeException("value " + v + " out of range for bound " + bound);
                }
            }
        }

        if (rnd.nextLong(1L) != 0L) {
            throw new RuntimeException("nextLong(1) must return 0");
        }
    }

    static void checkIllegalArgument(ThreadLocalRandom rnd, long bound) {
        try {
            rnd.nextLong(bound);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("expected IllegalArgumentException for bound " + bound);
    }
}
