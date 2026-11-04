/*
 * @test
 * @summary ThreadLocalRandom.nextInt(bound) rejects non-positive bounds and returns values within [0, bound)
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.concurrent.ThreadLocalRandom;

public class ThreadLocalRandomWitupTest {
    public static void main(String[] args) {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();

        int[] invalidBounds = {0, -1, Integer.MIN_VALUE};
        for (int bound : invalidBounds) {
            boolean thrown = false;
            try {
                rnd.nextInt(bound);
            } catch (IllegalArgumentException e) {
                thrown = true;
            }
            if (!thrown) {
                throw new AssertionError("expected IllegalArgumentException for bound " + bound);
            }
        }

        for (int i = 0; i < 1000; i++) {
            int v = rnd.nextInt(1);
            if (v != 0) {
                throw new AssertionError("nextInt(1) must return 0, got " + v);
            }
        }

        int[] bounds = {2, 3, 7, 16, 100, 1 << 30};
        for (int bound : bounds) {
            for (int i = 0; i < 1000; i++) {
                int v = rnd.nextInt(bound);
                if (v < 0 || v >= bound) {
                    throw new AssertionError("value " + v + " out of range for bound " + bound);
                }
            }
        }
    }
}
