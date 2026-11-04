/*
 * @test
 * @summary ThreadLocalRandom.nextInt(int) rejects non-positive bounds and returns values in [0, bound)
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.concurrent.ThreadLocalRandom;

public class ThreadLocalRandomWitupTest {

    public static void main(String[] args) {
        ThreadLocalRandom tlr = ThreadLocalRandom.current();

        int[] badBounds = {0, -1, Integer.MIN_VALUE};
        for (int bound : badBounds) {
            boolean thrown = false;
            try {
                tlr.nextInt(bound);
            } catch (IllegalArgumentException e) {
                thrown = true;
            }
            if (!thrown) {
                throw new RuntimeException("Expected IllegalArgumentException for bound " + bound);
            }
        }

        if (tlr.nextInt(1) != 0) {
            throw new RuntimeException("nextInt(1) must return 0");
        }

        int[] bounds = {2, 3, 4, 5, 7, 8, 16, 100, 1 << 30, Integer.MAX_VALUE - 1, Integer.MAX_VALUE};
        for (int bound : bounds) {
            for (int i = 0; i < 1000; i++) {
                int r = tlr.nextInt(bound);
                if (r < 0 || r >= bound) {
                    throw new RuntimeException("Value " + r + " out of range for bound " + bound);
                }
            }
        }
    }
}
