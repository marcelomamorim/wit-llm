/*
 * @test
 * @summary Test java.util.SplittableRandom.nextLong(long origin, long bound) method
 * @run main SplittableRandomWitupTest
 */

import java.util.SplittableRandom;

public class SplittableRandomWitupTest {
    public static void main(String[] args) {
        SplittableRandom sr = new SplittableRandom(12345L);

        // Test valid range: origin < bound
        long origin = 10L;
        long bound = 20L;
        for (int i = 0; i < 100; i++) {
            long val = sr.nextLong(origin, bound);
            if (val < origin || val >= bound) {
                throw new AssertionError("Value " + val + " out of range [" + origin + ", " + bound + ")");
            }
        }

        // Test edge case: origin and bound differ by 1
        origin = 5L;
        bound = 6L;
        for (int i = 0; i < 10; i++) {
            long val = sr.nextLong(origin, bound);
            if (val != origin) {
                throw new AssertionError("Value " + val + " not equal to origin " + origin + " when bound = origin + 1");
            }
        }

        // Test exception when origin >= bound
        try {
            sr.nextLong(10L, 10L);
            throw new AssertionError("Expected IllegalArgumentException for origin == bound");
        } catch (IllegalArgumentException e) {
            // expected
        }

        try {
            sr.nextLong(20L, 10L);
            throw new AssertionError("Expected IllegalArgumentException for origin > bound");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
