/*
 * @test
 * @summary Witup-derived tests for SplittableRandom.nextInt(int bound)
 * @run main SplittableRandomWitupTest
 */

import java.util.SplittableRandom;

public class SplittableRandomWitupTest {

    public static void main(String[] args) {
        testNonPositiveBoundsThrowIllegalArgumentException();
        testBoundOneAlwaysReturnsZero();
        testValuesStayWithinBound();
        testPowerOfTwoBoundsCoverRange();
        testSameSeedGivesSameSequence();
        testMaxValueBoundProducesNonNegativeValues();
    }

    static void expectIllegalArgument(int bound) {
        try {
            new SplittableRandom(1L).nextInt(bound);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("Expected IllegalArgumentException for bound " + bound);
    }

    static void testNonPositiveBoundsThrowIllegalArgumentException() {
        expectIllegalArgument(0);
        expectIllegalArgument(-1);
        expectIllegalArgument(Integer.MIN_VALUE);
    }

    static void testBoundOneAlwaysReturnsZero() {
        SplittableRandom rnd = new SplittableRandom(7L);
        for (int i = 0; i < 1000; i++) {
            int v = rnd.nextInt(1);
            if (v != 0) {
                throw new RuntimeException("nextInt(1) returned " + v);
            }
        }
    }

    static void testValuesStayWithinBound() {
        int[] bounds = {2, 3, 5, 7, 8, 10, 100, 1024, 1000000};
        for (int bound : bounds) {
            SplittableRandom rnd = new SplittableRandom(99L);
            for (int i = 0; i < 10000; i++) {
                int v = rnd.nextInt(bound);
                if (v < 0 || v >= bound) {
                    throw new RuntimeException("Value " + v + " out of range for bound " + bound);
                }
            }
        }
    }

    static void testPowerOfTwoBoundsCoverRange() {
        SplittableRandom rnd = new SplittableRandom(12345L);
        boolean seenZero = false;
        boolean seenOne = false;
        for (int i = 0; i < 1000; i++) {
            int v = rnd.nextInt(2);
            if (v == 0) {
                seenZero = true;
            } else if (v == 1) {
                seenOne = true;
            } else {
                throw new RuntimeException("nextInt(2) returned " + v);
            }
        }
        if (!seenZero || !seenOne) {
            throw new RuntimeException("nextInt(2) did not produce both 0 and 1");
        }
    }

    static void testSameSeedGivesSameSequence() {
        SplittableRandom a = new SplittableRandom(42L);
        SplittableRandom b = new SplittableRandom(42L);
        for (int i = 0; i < 100; i++) {
            int x = a.nextInt(1000);
            int y = b.nextInt(1000);
            if (x != y) {
                throw new RuntimeException("Sequences diverge at index " + i + ": " + x + " vs " + y);
            }
        }
    }

    static void testMaxValueBoundProducesNonNegativeValues() {
        SplittableRandom rnd = new SplittableRandom(2024L);
        for (int i = 0; i < 1000; i++) {
            int v = rnd.nextInt(Integer.MAX_VALUE);
            if (v < 0 || v >= Integer.MAX_VALUE) {
                throw new RuntimeException("Value " + v + " out of range for MAX_VALUE bound");
            }
        }
    }
}
