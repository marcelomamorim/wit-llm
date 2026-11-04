/*
 * @test
 * @summary Witup-generated tests for SplittableRandom.nextInt(int bound)
 * @run main SplittableRandomWitupTest
 */

import java.util.SplittableRandom;

public class SplittableRandomWitupTest {

    public static void main(String[] args) {
        testNonPositiveBoundThrows();
        testZeroBoundThrows();
        testValidBoundsInRange();
        testBoundOneReturnsZero();
        testDeterministicWithSeed();
    }

    static void testNonPositiveBoundThrows() {
        SplittableRandom r = new SplittableRandom(42L);
        int[] bounds = {-1, -100, Integer.MIN_VALUE};
        for (int b : bounds) {
            try {
                r.nextInt(b);
                throw new AssertionError("Expected IllegalArgumentException for bound " + b);
            } catch (IllegalArgumentException expected) {
                // expected
            }
        }
    }

    static void testZeroBoundThrows() {
        SplittableRandom r = new SplittableRandom(7L);
        try {
            r.nextInt(0);
            throw new AssertionError("Expected IllegalArgumentException for bound 0");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    static void testValidBoundsInRange() {
        SplittableRandom r = new SplittableRandom(12345L);
        int[] bounds = {1, 2, 3, 7, 10, 16, 100, 1000, 1 << 20, Integer.MAX_VALUE};
        for (int bound : bounds) {
            for (int i = 0; i < 200; i++) {
                int v = r.nextInt(bound);
                if (v < 0 || v >= bound) {
                    throw new AssertionError("Value " + v + " out of range [0," + bound + ")");
                }
            }
        }
    }

    static void testBoundOneReturnsZero() {
        SplittableRandom r = new SplittableRandom(99L);
        for (int i = 0; i < 100; i++) {
            if (r.nextInt(1) != 0) {
                throw new AssertionError("nextInt(1) must return 0");
            }
        }
    }

    static void testDeterministicWithSeed() {
        SplittableRandom a = new SplittableRandom(2024L);
        SplittableRandom b = new SplittableRandom(2024L);
        for (int i = 0; i < 500; i++) {
            int bound = (i % 97) + 1;
            int va = a.nextInt(bound);
            int vb = b.nextInt(bound);
            if (va != vb) {
                throw new AssertionError("Same seed produced different values at step " + i);
            }
        }
    }
}
