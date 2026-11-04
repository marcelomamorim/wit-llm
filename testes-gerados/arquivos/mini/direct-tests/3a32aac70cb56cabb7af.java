/*
 * @test
 * @summary Test java.util.SplittableRandom.ints(int,int) method for range validation and stream generation
 * @run main SplittableRandomWitupTest
 */

import java.util.SplittableRandom;
import java.util.stream.IntStream;

public class SplittableRandomWitupTest {
    public static void main(String[] args) {
        testValidRange();
        testInvalidRangeThrows();
    }

    private static void testValidRange() {
        SplittableRandom sr = new SplittableRandom(12345L);
        int origin = 10;
        int bound = 20;
        IntStream stream = sr.ints(origin, bound);
        // Check that stream is not null and produces values in [origin, bound)
        int[] values = stream.limit(1000).toArray();
        for (int v : values) {
            if (v < origin || v >= bound) {
                throw new AssertionError("Value " + v + " out of expected range [" + origin + ", " + bound + ")");
            }
        }
    }

    private static void testInvalidRangeThrows() {
        SplittableRandom sr = new SplittableRandom();
        int origin = 5;
        int bound = 5;
        boolean thrown = false;
        try {
            sr.ints(origin, bound);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IllegalArgumentException for origin >= bound");
        }

        thrown = false;
        try {
            sr.ints(10, 5);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IllegalArgumentException for origin > bound");
        }
    }
}
