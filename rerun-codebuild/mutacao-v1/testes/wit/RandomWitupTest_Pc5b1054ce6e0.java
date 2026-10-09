/* @test    @summary Test Random.nextInt(int) for valid and invalid bounds    @run main RandomWitupTest_Pc5b1054ce6e0 */

import java.util.Random;

public class RandomWitupTest_Pc5b1054ce6e0 {
    public static void main(String[] args) {
        testNextIntWithValidBounds();
        testNextIntWithZeroBound();
        testNextIntWithNegativeBound();
    }

    private static void testNextIntWithValidBounds() {
        Random rand = new Random(12345L);
        int bound = 10;
        int result = rand.nextInt(bound);
        if (result < 0 || result >= bound) {
            throw new AssertionError("Result out of bounds: " + result);
        }
    }

    private static void testNextIntWithZeroBound() {
        Random rand = new Random(54321L);
        try {
            rand.nextInt(0);
            throw new AssertionError("Expected IllegalArgumentException for bound=0");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    private static void testNextIntWithNegativeBound() {
        Random rand = new Random(67890L);
        try {
            rand.nextInt(-5);
            throw new AssertionError("Expected IllegalArgumentException for negative bound");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
