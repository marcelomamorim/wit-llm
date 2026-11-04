/*
 * @test
 * @summary Test java.util.Random.ints(int randomNumberOrigin, int randomNumberBound) for valid and invalid ranges
 * @run main RandomWitupTest
 */

import java.util.Random;
import java.util.stream.IntStream;

public class RandomWitupTest {
    public static void main(String[] args) {
        testIntsValidRange();
        testIntsInvalidRange();
    }

    private static void testIntsValidRange() {
        Random rnd = new Random(12345L);
        int origin = 10;
        int bound = 20;
        IntStream stream = rnd.ints(origin, bound);
        // Check that the stream produces values within [origin, bound)
        int[] values = stream.limit(1000).toArray();
        for (int v : values) {
            if (v < origin || v >= bound) {
                throw new AssertionError("Value out of range: " + v);
            }
        }
    }

    private static void testIntsInvalidRange() {
        Random rnd = new Random(12345L);
        int origin = 20;
        int bound = 10;
        try {
            rnd.ints(origin, bound);
            throw new AssertionError("Expected IllegalArgumentException for origin >= bound");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
        // Also test origin == bound
        origin = 10;
        bound = 10;
        try {
            rnd.ints(origin, bound);
            throw new AssertionError("Expected IllegalArgumentException for origin == bound");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
