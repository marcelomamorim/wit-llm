/*
 * @test
 * @summary Test for ThreadLocalRandom.ints(int, int) method.
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class ThreadLocalRandomWitupTest {

    public static void main(String[] args) {
        testValidRange();
        testInvalidRange();
    }

    private static void testValidRange() {
        int origin = 10;
        int bound = 20;
        IntStream stream = ThreadLocalRandom.current().ints(origin, bound);
        boolean allInRange = stream.allMatch(i -> i >= origin && i < bound);
        if (!allInRange) {
            throw new AssertionError("Generated values are out of the specified range.");
        }
    }

    private static void testInvalidRange() {
        int origin = 20;
        int bound = 10;
        try {
            ThreadLocalRandom.current().ints(origin, bound).count();
            throw new AssertionError("Expected IllegalArgumentException was not thrown.");
        } catch (IllegalArgumentException e) {
            // Expected exception, test passes.
        }
    }
}
