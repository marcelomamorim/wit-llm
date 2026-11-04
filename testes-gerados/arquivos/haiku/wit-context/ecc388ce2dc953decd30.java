/*
 * @test
 * @summary ThreadLocalRandom.setSeed throws UnsupportedOperationException once the instance is initialized
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.concurrent.ThreadLocalRandom;

public class ThreadLocalRandomWitupTest {
    public static void main(String[] args) {
        ThreadLocalRandom tlr = ThreadLocalRandom.current();

        boolean thrown = false;
        try {
            tlr.setSeed(42L);
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected UnsupportedOperationException from setSeed(42L)");
        }

        thrown = false;
        try {
            tlr.setSeed(Long.MIN_VALUE);
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected UnsupportedOperationException from setSeed(Long.MIN_VALUE)");
        }

        int v = tlr.nextInt(1, 2);
        if (v != 1) {
            throw new RuntimeException("instance should remain usable after rejected setSeed, got " + v);
        }
    }
}
