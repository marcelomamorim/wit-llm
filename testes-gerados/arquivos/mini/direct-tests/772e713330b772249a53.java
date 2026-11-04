/*
 * @test
 * @summary Test ThreadLocalRandom.ints(int, int) method for valid and invalid ranges
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class ThreadLocalRandomWitupTest {
    public static void main(String[] args) {
        testValidRange();
        testInvalidRange();
    }

    static void testValidRange() {
        ThreadLocalRandom tlr = ThreadLocalRandom.current();
        int origin = 5;
        int bound = 10;
        IntStream stream = tlr.ints(origin, bound);
        int[] values = stream.limit(1000).toArray();
        for (int v : values) {
            if (v < origin || v >= bound) {
                throw new AssertionError("Value " + v + " out of range [" + origin + ", " + bound + ")");
            }
        }
    }

    static void testInvalidRange() {
        ThreadLocalRandom tlr = ThreadLocalRandom.current();
        int origin = 10;
        int bound = 5;
        try {
            tlr.ints(origin, bound);
            throw new AssertionError("Expected IllegalArgumentException for origin >= bound");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
