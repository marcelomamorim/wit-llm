/*
 * @test
 * @summary Tests SplittableRandom.longs(origin, bound): eager range validation, value range, determinism with seed and extreme bounds
 * @run main SplittableRandomWitupTest
 */

import java.util.SplittableRandom;

public class SplittableRandomWitupTest {

    public static void main(String[] args) {
        testEqualBoundsThrowsEagerly();
        testOriginGreaterThanBoundThrowsEagerly();
        testValuesWithinRange();
        testSameSeedProducesSameSequence();
        testNarrowRangeAtMaxValueEdge();
        testStreamLengthHonored();
    }

    static void fail(String msg) {
        throw new RuntimeException(msg);
    }

    static void testEqualBoundsThrowsEagerly() {
        SplittableRandom rnd = new SplittableRandom(1L);
        boolean thrown = false;
        try {
            rnd.longs(5L, 5L);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            fail("longs(5,5) must throw IllegalArgumentException");
        }
    }

    static void testOriginGreaterThanBoundThrowsEagerly() {
        SplittableRandom rnd = new SplittableRandom(2L);
        boolean thrown = false;
        try {
            rnd.longs(10L, -10L);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            fail("longs(10,-10) must throw IllegalArgumentException");
        }
    }

    static void testValuesWithinRange() {
        SplittableRandom rnd = new SplittableRandom(3L);
        long[] values = rnd.longs(-10L, 10L).limit(1000).toArray();
        for (long v : values) {
            if (v < -10L || v >= 10L) {
                fail("value out of range [-10,10): " + v);
            }
        }
    }

    static void testSameSeedProducesSameSequence() {
        long[] a = new SplittableRandom(42L).longs(0L, 1000L).limit(100).toArray();
        long[] b = new SplittableRandom(42L).longs(0L, 1000L).limit(100).toArray();
        if (a.length != 100 || b.length != 100) {
            fail("unexpected length");
        }
        for (int i = 0; i < a.length; i++) {
            if (a[i] != b[i]) {
                fail("sequences differ at index " + i);
            }
        }
    }

    static void testNarrowRangeAtMaxValueEdge() {
        SplittableRandom rnd = new SplittableRandom(7L);
        long origin = Long.MAX_VALUE - 1L;
        long[] values = rnd.longs(origin, Long.MAX_VALUE).limit(50).toArray();
        for (long v : values) {
            if (v != origin) {
                fail("expected only " + origin + " in range [MAX-1, MAX), got " + v);
            }
        }
    }

    static void testStreamLengthHonored() {
        SplittableRandom rnd = new SplittableRandom(11L);
        long count = rnd.longs(0L, 5L).limit(256).count();
        if (count != 256L) {
            fail("expected 256 elements, got " + count);
        }
    }
}
