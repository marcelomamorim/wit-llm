/*
 * @test
 * @summary Test java.util.Random.ints(int randomNumberOrigin, int randomNumberBound) method
 * @run main java.util.RandomWitupTest
 */

import java.util.Random;
import java.util.stream.IntStream;

public class RandomWitupTest {
    public static void main(String[] args) {
        testValidRange();
        testInvalidRange();
    }

    private static void testValidRange() {
        Random rnd = new Random(12345L);
        int origin = 10;
        int bound = 20;
        IntStream stream = rnd.ints(origin, bound);
        int[] values = stream.limit(1000).toArray();
        for (int v : values) {
            if (v < origin || v >= bound) {
                throw new AssertionError("Value " + v + " out of range [" + origin + ", " + bound + ")");
            }
        }
    }

    private static void testInvalidRange() {
        Random rnd = new Random();
        try {
            rnd.ints(5, 5);
            throw new AssertionError("Expected IllegalArgumentException for equal origin and bound");
        } catch (IllegalArgumentException e) {
            // expected
        }
        try {
            rnd.ints(10, 5);
            throw new AssertionError("Expected IllegalArgumentException for origin > bound");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
