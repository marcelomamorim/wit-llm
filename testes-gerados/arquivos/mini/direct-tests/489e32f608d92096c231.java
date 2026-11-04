/*
 * @test
 * @summary Test java.util.SplittableRandom.nextInt(int origin, int bound) for valid and invalid ranges
 * @run main SplittableRandomWitupTest
 */

import java.util.SplittableRandom;

public class SplittableRandomWitupTest {
    public static void main(String[] args) {
        SplittableRandom rng = new SplittableRandom(12345L);

        // Test valid range: origin < bound
        int origin = 10;
        int bound = 20;
        for (int i = 0; i < 1000; i++) {
            int val = rng.nextInt(origin, bound);
            if (val < origin || val >= bound) {
                throw new AssertionError("Value " + val + " out of range [" + origin + ", " + bound + ")");
            }
        }

        // Test valid range with negative origin
        origin = -50;
        bound = -40;
        for (int i = 0; i < 1000; i++) {
            int val = rng.nextInt(origin, bound);
            if (val < origin || val >= bound) {
                throw new AssertionError("Value " + val + " out of range [" + origin + ", " + bound + ")");
            }
        }

        // Test valid range with origin = Integer.MIN_VALUE and bound = Integer.MIN_VALUE + 10
        origin = Integer.MIN_VALUE;
        bound = Integer.MIN_VALUE + 10;
        for (int i = 0; i < 1000; i++) {
            int val = rng.nextInt(origin, bound);
            if (val < origin || val >= bound) {
                throw new AssertionError("Value " + val + " out of range [" + origin + ", " + bound + ")");
            }
        }

        // Test valid range with bound = Integer.MAX_VALUE
        origin = Integer.MAX_VALUE - 10;
        bound = Integer.MAX_VALUE;
        for (int i = 0; i < 1000; i++) {
            int val = rng.nextInt(origin, bound);
            if (val < origin || val >= bound) {
                throw new AssertionError("Value " + val + " out of range [" + origin + ", " + bound + ")");
            }
        }

        // Test invalid range: origin == bound
        try {
            rng.nextInt(5, 5);
            throw new AssertionError("Expected IllegalArgumentException for origin == bound");
        } catch (IllegalArgumentException e) {
            // expected
        }

        // Test invalid range: origin > bound
        try {
            rng.nextInt(10, 5);
            throw new AssertionError("Expected IllegalArgumentException for origin > bound");
        } catch (IllegalArgumentException e) {
            // expected
        }

        System.out.println("All tests passed.");
    }
}
