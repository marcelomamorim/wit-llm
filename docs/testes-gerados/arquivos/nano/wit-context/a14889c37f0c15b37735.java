/*
 * @test
 * @summary Tests for java.util.SplittableRandom.longs(long, long)
 * @run main SplittableRandomWitupTest
 */

import java.util.SplittableRandom;
import java.util.stream.LongStream;

public class SplittableRandomWitupTest {

    public static void main(String[] args) {
        testValidRange();
        testInvalidRange();
    }

    static void testValidRange() {
        SplittableRandom sr = new SplittableRandom(12345L);
        long origin = 10L;
        long bound = 20L;
        LongStream stream = sr.longs(origin, bound);
        boolean allInRange = stream.allMatch(n -> n >= origin && n < bound);
        if (!allInRange) {
            throw new AssertionError("Generated values are out of the specified range.");
        }
    }

    static void testInvalidRange() {
        SplittableRandom sr = new SplittableRandom(54321L);
        long origin = 30L;
        long bound = 20L;
        try {
            sr.longs(origin, bound).count();
            throw new AssertionError("Expected IllegalArgumentException was not thrown.");
        } catch (IllegalArgumentException e) {
            // Expected exception, test passes
        }
    }
}
