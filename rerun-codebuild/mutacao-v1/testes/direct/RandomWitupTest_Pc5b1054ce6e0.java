/* @test    @summary Test Random.nextInt(int) for various bounds to ensure correctness and exception handling    @run main RandomWitupTest_Pc5b1054ce6e0 */

import java.util.Random;

public class RandomWitupTest_Pc5b1054ce6e0 {
    public static void main(String[] args) {
        testNextIntWithPowerOfTwoBound();
        testNextIntWithNonPowerOfTwoBound();
        testNextIntWithZeroBound();
        testNextIntWithNegativeBound();
    }

    private static void testNextIntWithPowerOfTwoBound() {
        Random rand = new Random(12345L);
        int bound = 16; // power of two
        for (int i = 0; i < 100; i++) {
            int result = rand.nextInt(bound);
            if (result < 0 || result >= bound) {
                throw new AssertionError("Result out of bounds for power of two bound: " + result);
            }
        }
    }

    private static void testNextIntWithNonPowerOfTwoBound() {
        Random rand = new Random(54321L);
        int bound = 20; // not a power of two
        for (int i = 0; i < 100; i++) {
            int result = rand.nextInt(bound);
            if (result < 0 || result >= bound) {
                throw new AssertionError("Result out of bounds for non-power of two bound: " + result);
            }
        }
    }

    private static void testNextIntWithZeroBound() {
        Random rand = new Random(67890L);
        boolean exceptionThrown = false;
        try {
            rand.nextInt(0);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException for zero bound");
        }
    }

    private static void testNextIntWithNegativeBound() {
        Random rand = new Random(98765L);
        boolean exceptionThrown = false;
        try {
            rand.nextInt(-5);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException for negative bound");
        }
    }
}
