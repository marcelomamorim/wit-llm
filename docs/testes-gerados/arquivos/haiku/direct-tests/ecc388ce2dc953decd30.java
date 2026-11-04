/*
 * @test
 * @summary ThreadLocalRandom.setSeed must throw UnsupportedOperationException after initialization
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.concurrent.ThreadLocalRandom;

public class ThreadLocalRandomWitupTest {

    static void expectUnsupported(ThreadLocalRandom tlr, long seed) {
        try {
            tlr.setSeed(seed);
            throw new AssertionError("setSeed(" + seed + ") should have thrown UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
            // expected: instance is already initialized
        }
    }

    public static void main(String[] args) {
        ThreadLocalRandom tlr = ThreadLocalRandom.current();

        expectUnsupported(tlr, 42L);
        expectUnsupported(tlr, 0L);
        expectUnsupported(tlr, Long.MIN_VALUE);

        // The same thread-local instance must remain usable after the rejected calls.
        ThreadLocalRandom again = ThreadLocalRandom.current();
        expectUnsupported(again, 7L);
        int v = again.nextInt(1, 11);
        if (v < 1 || v >= 11) {
            throw new AssertionError("nextInt(1, 11) out of range: " + v);
        }
    }
}
