/*
 * @test
 * @summary Witup-generated checks for SplittableRandom.nextLong(long, long) range validation and bounds
 * @run main SplittableRandomWitupTest
 */

public class SplittableRandomWitupTest {

    public static void main(String[] args) {
        testBoundLessThanOriginThrowsIAE();
        testEqualBoundsThrowsIAE();
        testNegativeRangeInvalidThrowsIAE();
        testValidNegativeRangeWithinBounds();
        testValidPositiveRangeWithinBounds();
        testAdjacentBoundsReturnsOrigin();
        testSameSeedProducesSameSequence();
    }

    static void testBoundLessThanOriginThrowsIAE() {
        SplittableRandom r = new SplittableRandom(1L);
        boolean thrown = false;
        try {
            r.nextLong(100L, 50L);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected IllegalArgumentException for origin=100, bound=50");
        }
    }

    static void testEqualBoundsThrowsIAE() {
        SplittableRandom r = new SplittableRandom(2L);
        boolean thrown = false;
        try {
            r.nextLong(7L, 7L);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected IllegalArgumentException for origin == bound");
        }
    }

    static void testNegativeRangeInvalidThrowsIAE() {
        SplittableRandom r = new SplittableRandom(3L);
        boolean thrown = false;
        try {
            r.nextLong(-5L, -10L);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected IllegalArgumentException for origin=-5, bound=-10");
        }
    }

    static void testValidNegativeRangeWithinBounds() {
        SplittableRandom r = new SplittableRandom(4L);
        for (int i = 0; i < 1000; i++) {
            long v = r.nextLong(-10L, -5L);
            if (v < -10L || v >= -5L) {
                throw new RuntimeException("value out of range [-10, -5): " + v);
            }
        }
    }

    static void testValidPositiveRangeWithinBounds() {
        SplittableRandom r = new SplittableRandom(5L);
        for (int i = 0; i < 1000; i++) {
            long v = r.nextLong(100L, 200L);
            if (v < 100L || v >= 200L) {
                throw new RuntimeException("value out of range [100, 200): " + v);
            }
        }
    }

    static void testAdjacentBoundsReturnsOrigin() {
        SplittableRandom r = new SplittableRandom(6L);
        for (int i = 0; i < 100; i++) {
            long v = r.nextLong(42L, 43L);
            if (v != 42L) {
                throw new RuntimeException("expected 42 for range [42, 43), got " + v);
            }
        }
    }

    static void testSameSeedProducesSameSequence() {
        SplittableRandom a = new SplittableRandom(12345L);
        SplittableRandom b = new SplittableRandom(12345L);
        for (int i = 0; i < 100; i++) {
            long va = a.nextLong(-1000L, 1000L);
            long vb = b.nextLong(-1000L, 1000L);
            if (va != vb) {
                throw new RuntimeException("sequences diverge at index " + i + ": " + va + " != " + vb);
            }
        }
    }
}
