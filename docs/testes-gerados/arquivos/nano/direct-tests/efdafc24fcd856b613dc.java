/* @test    @summary Test Random.nextInt(int) for various bounds    @run main RandomWitupTest */

import java.util.Random;

public class RandomWitupTest {

    public static void main(String[] args) {
        testNextIntWithPositiveBound();
        testNextIntWithPowerOfTwoBound();
        testNextIntWithInvalidBound();
    }

    static void testNextIntWithPositiveBound() {
        Random rand = new Random(12345L);
        int bound = 10;
        int result = rand.nextInt(bound);
        if (result < 0 || result >= bound) {
            throw new AssertionError("Result out of bounds: " + result);
        }
    }

    static void testNextIntWithPowerOfTwoBound() {
        Random rand = new Random(54321L);
        int bound = 8; // power of two
        int result = rand.nextInt(bound);
        if (result < 0 || result >= bound) {
            throw new AssertionError("Result out of bounds for power of two bound: " + result);
        }
    }

    static void testNextIntWithInvalidBound() {
        Random rand = new Random(67890L);
        boolean exceptionThrown = false;
        try {
            rand.nextInt(0);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException for bound=0") ;
        }
    }
}
