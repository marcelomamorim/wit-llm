/*
 * @test
 * @summary Test ThreadLocalRandom.nextInt(int origin, int bound) for valid and invalid ranges
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.concurrent.ThreadLocalRandom;

public class ThreadLocalRandomWitupTest {
    public static void main(String[] args) {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();

        // Test valid range: origin < bound
        int origin = 5;
        int bound = 10;
        for (int i = 0; i < 1000; i++) {
            int val = rnd.nextInt(origin, bound);
            if (val < origin || val >= bound) {
                throw new AssertionError("Value " + val + " out of range [" + origin + ", " + bound + ")");
            }
        }

        // Test valid range: origin == bound - 1 (smallest range)
        origin = 7;
        bound = 8;
        for (int i = 0; i < 100; i++) {
            int val = rnd.nextInt(origin, bound);
            if (val != origin) {
                throw new AssertionError("Value " + val + " not equal to origin " + origin + " for range size 1");
            }
        }

        // Test invalid range: origin == bound
        try {
            rnd.nextInt(10, 10);
            throw new AssertionError("Expected IllegalArgumentException for origin == bound");
        } catch (IllegalArgumentException e) {
            // expected
        }

        // Test invalid range: origin > bound
        try {
            rnd.nextInt(15, 10);
            throw new AssertionError("Expected IllegalArgumentException for origin > bound");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
