/* @test    @summary Test for ThreadLocalRandom.nextInt(int, int) method to verify correct range behavior and exception handling.    @run main ThreadLocalRandomWitupTest_P744965ea1631 */

import java.util.concurrent.ThreadLocalRandom;

public class ThreadLocalRandomWitupTest_P744965ea1631 {

    public static void main(String[] args) {
        testValidRange();
        testInvalidRange();
        testEdgeCases();
    }

    static void testValidRange() {
        for (int i = 0; i < 10; i++) {
            int origin = i;
            int bound = i + 5;
            int result = ThreadLocalRandom.current().nextInt(origin, bound);
            if (result < origin || result >= bound) {
                throw new AssertionError("Result " + result + " not in range [" + origin + ", " + bound + ")");
            }
        }
    }

    static void testInvalidRange() {
        int[] invalidPairs = { {5, 5}, {10, 5}, {0, -1} };
        for (int[] pair : invalidPairs) {
            int origin = pair[0];
            int bound = pair[1];
            try {
                ThreadLocalRandom.current().nextInt(origin, bound);
                throw new AssertionError("Expected IllegalArgumentException for range [" + origin + ", " + bound + ")");
            } catch (IllegalArgumentException e) {
                // Expected exception
            }
        }
    }

    static void testEdgeCases() {
        // Test with origin == Integer.MIN_VALUE and bound == Integer.MIN_VALUE + 1
        int origin = Integer.MIN_VALUE;
        int bound = Integer.MIN_VALUE + 1;
        int result = ThreadLocalRandom.current().nextInt(origin, bound);
        if (result != origin) {
            throw new AssertionError("Result " + result + " not equal to origin " + origin);
        }
        // Test with origin == Integer.MAX_VALUE - 1 and bound == Integer.MAX_VALUE
        origin = Integer.MAX_VALUE - 1;
        bound = Integer.MAX_VALUE;
        result = ThreadLocalRandom.current().nextInt(origin, bound);
        if (result != origin) {
            throw new AssertionError("Result " + result + " not equal to origin " + origin);
        }
    }
}
