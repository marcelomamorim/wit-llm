/* @test    @summary Test for ThreadLocalRandom.nextInt(int, int) method that validates behavior with valid ranges and invalid input handling.    @run main ThreadLocalRandomWitupTest_Pad2788329a5c */

import java.util.concurrent.ThreadLocalRandom;

public class ThreadLocalRandomWitupTest_Pad2788329a5c {

    public static void main(String[] args) {
        testValidRanges();
        testInvalidRange();
    }

    private static void testValidRanges() {
        ThreadLocalRandom rng = ThreadLocalRandom.current();
        int origin = 10;
        int bound = 20;
        for (int i = 0; i < 100; i++) {
            int result = rng.nextInt(origin, bound);
            assert result >= origin && result < bound : "Result " + result + " not in range [" + origin + ", " + bound + ")";
        }
    }

    private static void testInvalidRange() {
        ThreadLocalRandom rng = ThreadLocalRandom.current();
        boolean exceptionThrown = false;
        try {
            rng.nextInt(30, 20);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected IllegalArgumentException for origin >= bound";
    }
}
