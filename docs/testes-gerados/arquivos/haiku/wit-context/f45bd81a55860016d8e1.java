/*
 * @test
 * @summary WITUP-derived checks for SplittableRandom.nextLong(long bound) argument validation and bounded output
 * @run main SplittableRandomWitupTest
 */

import java.util.SplittableRandom;

public class SplittableRandomWitupTest {

    public static void main(String[] args) {
        checkIllegalBound(0L);
        checkIllegalBound(-1L);
        checkIllegalBound(Long.MIN_VALUE);
        checkBoundOne();
        checkBoundedRange();
        checkDeterminism();
    }

    private static void checkIllegalBound(long bound) {
        SplittableRandom r = new SplittableRandom(42L);
        boolean thrown = false;
        try {
            r.nextLong(bound);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException for bound " + bound);
        }
    }

    private static void checkBoundOne() {
        SplittableRandom r = new SplittableRandom(7L);
        for (int i = 0; i < 100; i++) {
            if (r.nextLong(1L) != 0L) {
                throw new RuntimeException("nextLong(1) must return 0");
            }
        }
    }

    private static void checkBoundedRange() {
        long[] bounds = {2L, 3L, 8L, 10L, 1000L, 1L << 40, Long.MAX_VALUE};
        SplittableRandom r = new SplittableRandom(12345L);
        for (long b : bounds) {
            for (int i = 0; i < 1000; i++) {
                long v = r.nextLong(b);
                if (v < 0L || v >= b) {
                    throw new RuntimeException("Value " + v + " out of range for bound " + b);
                }
            }
        }
    }

    private static void checkDeterminism() {
        SplittableRandom a = new SplittableRandom(99L);
        SplittableRandom b = new SplittableRandom(99L);
        for (int i = 0; i < 200; i++) {
            long x = a.nextLong(1000L);
            long y = b.nextLong(1000L);
            if (x != y) {
                throw new RuntimeException("Same seed produced different sequences at step " + i);
            }
        }
    }
}
