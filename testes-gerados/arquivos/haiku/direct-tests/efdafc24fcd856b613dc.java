/*
 * @test
 * @summary Verify Random.nextInt(int bound) range, determinism and IllegalArgumentException for non-positive bounds
 * @run main RandomWitupTest
 */

import java.util.Random;

public class RandomWitupTest {
    public static void main(String[] args) {
        testNonPositiveBoundThrows();
        testBoundOneAlwaysZero();
        testPowerOfTwoBoundRange();
        testNonPowerOfTwoBoundRange();
        testMaxBoundRange();
        testDeterministicSequence();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNonPositiveBoundThrows() {
        int[] bounds = {0, -1, Integer.MIN_VALUE};
        Random r = new Random(1L);
        for (int b : bounds) {
            try {
                r.nextInt(b);
                throw new RuntimeException("No exception for bound " + b);
            } catch (IllegalArgumentException expected) {
            }
        }
    }

    static void testBoundOneAlwaysZero() {
        Random r = new Random(7L);
        for (int i = 0; i < 100; i++) {
            check(r.nextInt(1) == 0, "nextInt(1) must return 0");
        }
    }

    static void testPowerOfTwoBoundRange() {
        int[] bounds = {2, 4, 8, 1024, 1 << 30};
        Random r = new Random(11L);
        for (int bound : bounds) {
            for (int i = 0; i < 1000; i++) {
                int v = r.nextInt(bound);
                check(v >= 0 && v < bound, "out of range for power-of-two bound " + bound + ": " + v);
            }
        }
    }

    static void testNonPowerOfTwoBoundRange() {
        int[] bounds = {3, 5, 6, 7, 100, 1000000007};
        Random r = new Random(13L);
        for (int bound : bounds) {
            for (int i = 0; i < 1000; i++) {
                int v = r.nextInt(bound);
                check(v >= 0 && v < bound, "out of range for bound " + bound + ": " + v);
            }
        }
    }

    static void testMaxBoundRange() {
        Random r = new Random(17L);
        for (int i = 0; i < 1000; i++) {
            int v = r.nextInt(Integer.MAX_VALUE);
            check(v >= 0 && v < Integer.MAX_VALUE, "out of range for MAX_VALUE bound: " + v);
        }
    }

    static void testDeterministicSequence() {
        int[] bounds = {1000, 6, 16};
        Random a = new Random(42L);
        Random b = new Random(42L);
        for (int i = 0; i < 1000; i++) {
            int bound = bounds[i % bounds.length];
            int va = a.nextInt(bound);
            int vb = b.nextInt(bound);
            check(va == vb, "sequences diverge at index " + i + ": " + va + " vs " + vb);
        }
    }
}
