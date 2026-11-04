/*
 * @test
 * @summary Tests Random.nextInt(int bound) argument validation and output range
 * @run main RandomWitupTest
 */

import java.util.Random;

public class RandomWitupTest {
    public static void main(String[] args) {
        testInvalidBoundsThrow();
        testValuesWithinRange();
        testBoundOneAlwaysZero();
        testPowerOfTwoBoundsWithinRange();
        testDeterministicSequenceForSameSeed();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testInvalidBoundsThrow() {
        int[] bounds = {0, -1, -100, Integer.MIN_VALUE};
        for (int bound : bounds) {
            Random random = new Random(1L);
            boolean thrown = false;
            try {
                random.nextInt(bound);
            } catch (IllegalArgumentException expected) {
                thrown = true;
            }
            check(thrown, "IllegalArgumentException expected for bound " + bound);
        }
    }

    static void testValuesWithinRange() {
        int[] bounds = {2, 3, 7, 10, 100, 1000, Integer.MAX_VALUE};
        for (int bound : bounds) {
            Random random = new Random(12345L + bound);
            for (int i = 0; i < 1000; i++) {
                int value = random.nextInt(bound);
                check(value >= 0 && value < bound,
                      "value " + value + " out of range for bound " + bound);
            }
        }
    }

    static void testBoundOneAlwaysZero() {
        Random random = new Random(7L);
        for (int i = 0; i < 100; i++) {
            check(random.nextInt(1) == 0, "nextInt(1) must return 0");
        }
    }

    static void testPowerOfTwoBoundsWithinRange() {
        int[] bounds = {1 << 1, 1 << 5, 1 << 16, 1 << 30};
        for (int bound : bounds) {
            Random random = new Random(99L);
            for (int i = 0; i < 1000; i++) {
                int value = random.nextInt(bound);
                check(value >= 0 && value < bound,
                      "power-of-two value " + value + " out of range for bound " + bound);
            }
        }
    }

    static void testDeterministicSequenceForSameSeed() {
        Random a = new Random(42L);
        Random b = new Random(42L);
        for (int i = 0; i < 500; i++) {
            int bound = 1 + (i % 97);
            check(a.nextInt(bound) == b.nextInt(bound),
                  "sequences diverged at step " + i);
        }
    }
}
