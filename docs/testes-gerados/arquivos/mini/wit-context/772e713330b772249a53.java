/*
 * @test
 * @summary Test ThreadLocalRandom.ints(int, int) for bounds validation and stream generation
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class ThreadLocalRandomWitupTest {
    public static void main(String[] args) {
        testIllegalArgumentExceptionOnInvalidBounds();
        testIntStreamGenerationWithValidBounds();
    }

    static void testIllegalArgumentExceptionOnInvalidBounds() {
        ThreadLocalRandom tlr = ThreadLocalRandom.current();
        boolean thrown = false;
        try {
            tlr.ints(10, 10); // origin == bound, should throw
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IllegalArgumentException for origin == bound");
        }

        thrown = false;
        try {
            tlr.ints(20, 10); // origin > bound, should throw
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IllegalArgumentException for origin > bound");
        }
    }

    static void testIntStreamGenerationWithValidBounds() {
        ThreadLocalRandom tlr = ThreadLocalRandom.current();
        int origin = 5;
        int bound = 15;
        IntStream stream = tlr.ints(origin, bound);
        // Check that stream is not null and produces values in [origin, bound)
        int[] values = stream.limit(1000).toArray();
        if (values.length != 1000) {
            throw new AssertionError("Stream did not produce expected number of elements");
        }
        for (int v : values) {
            if (v < origin || v >= bound) {
                throw new AssertionError("Value " + v + " out of bounds [" + origin + ", " + bound + ")");
            }
        }
    }
}
