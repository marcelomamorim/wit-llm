/*
 * @test
 * @summary Test SplittableRandom.ints(int randomNumberOrigin, int randomNumberBound) for valid and invalid ranges
 * @run main SplittableRandomWitupTest
 */

import java.util.SplittableRandom;
import java.util.stream.IntStream;

public class SplittableRandomWitupTest {
    public static void main(String[] args) {
        testValidRange();
        testInvalidRange();
    }

    private static void testValidRange() {
        SplittableRandom sr = new SplittableRandom(12345L);
        int origin = 10;
        int bound = 20;
        IntStream stream = sr.ints(origin, bound);
        // Check that all generated ints are within [origin, bound)
        stream.limit(1000).forEach(i -> {
            if (i < origin || i >= bound) {
                throw new AssertionError("Generated int " + i + " out of bounds [" + origin + ", " + bound + ")");
            }
        });
    }

    private static void testInvalidRange() {
        SplittableRandom sr = new SplittableRandom(12345L);
        int origin = 20;
        int bound = 10;
        try {
            sr.ints(origin, bound);
            throw new AssertionError("Expected IllegalArgumentException for origin >= bound");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
        // Also test origin == bound
        origin = 15;
        bound = 15;
        try {
            sr.ints(origin, bound);
            throw new AssertionError("Expected IllegalArgumentException for origin == bound");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
