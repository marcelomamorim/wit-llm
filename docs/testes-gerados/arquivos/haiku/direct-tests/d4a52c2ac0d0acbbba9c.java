/*
 * @test
 * @summary Verifies ThreadLocalRandom.nextLong(long bound) rejects non-positive bounds and returns values in [0, bound)
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.concurrent.ThreadLocalRandom;

public class ThreadLocalRandomWitupTest {

    public static void main(String[] args) {
        ThreadLocalRandom tlr = ThreadLocalRandom.current();

        long[] badBounds = {0L, -1L, Long.MIN_VALUE};
        for (long b : badBounds) {
            boolean thrown = false;
            try {
                tlr.nextLong(b);
            } catch (IllegalArgumentException expected) {
                thrown = true;
            }
            if (!thrown) {
                throw new RuntimeException("Expected IllegalArgumentException for bound " + b);
            }
        }

        long[] goodBounds = {1L, 2L, 3L, 7L, 8L, 1000L, 1L << 40, Long.MAX_VALUE};
        for (long bound : goodBounds) {
            for (int i = 0; i < 10000; i++) {
                long r = tlr.nextLong(bound);
                if (r < 0L || r >= bound) {
                    throw new RuntimeException("Value " + r + " out of range for bound " + bound);
                }
            }
        }

        for (int i = 0; i < 1000; i++) {
            if (tlr.nextLong(1L) != 0L) {
                throw new RuntimeException("nextLong(1) must always return 0");
            }
        }
    }
}
