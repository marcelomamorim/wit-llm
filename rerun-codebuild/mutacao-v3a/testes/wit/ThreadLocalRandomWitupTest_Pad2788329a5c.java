/*
 * @test    @summary Test for ThreadLocalRandom.nextInt(int, int) method.
 * @run main ThreadLocalRandomWitupTest_Pad2788329a5c
 */

import java.util.concurrent.ThreadLocalRandom;

public class ThreadLocalRandomWitupTest_Pad2788329a5c {
    public static void main(String[] args) {
        testValidRange();
        testInvalidRange();
    }

    static void testValidRange() {
        ThreadLocalRandom rand = ThreadLocalRandom.current();
        int origin = 0;
        int bound = 10;
        for (int i = 0; i < 100; i++) {
            int result = rand.nextInt(origin, bound);
            if (result < origin || result >= bound) {
                throw new AssertionError("Result out of range: " + result);
            }
        }
    }

    static void testInvalidRange() {
        ThreadLocalRandom rand = ThreadLocalRandom.current();
        int origin = 10;
        int bound = 5;
        try {
            rand.nextInt(origin, bound);
            throw new AssertionError("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
