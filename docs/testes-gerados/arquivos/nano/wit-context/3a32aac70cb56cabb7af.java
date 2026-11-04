/*
 * @test
 * @summary Tests for java.util.SplittableRandom.ints(int, int)
 * @run main SplittableRandomWitupTest
 */

import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

public class SplittableRandomWitupTest {

    public static void main(String[] args) {
        testValidRange();
        testInvalidRange();
    }

    static void testValidRange() {
        java.util.SplittableRandom sr = new java.util.SplittableRandom();
        int origin = 10;
        int bound = 20;
        IntStream stream = sr.ints(origin, bound);
        boolean allInRange = stream.allMatch(i -> i >= origin && i < bound);
        if (!allInRange) {
            throw new AssertionError("Stream contains values out of range") ;
        }
        // Consume stream to avoid resource warnings
        stream.close();
    }

    static void testInvalidRange() {
        java.util.SplittableRandom sr = new java.util.SplittableRandom();
        int origin = 20;
        int bound = 10;
        try {
            sr.ints(origin, bound).count();
            throw new AssertionError("Expected IllegalArgumentException not thrown") ;
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
