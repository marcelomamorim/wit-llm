/*
 * @test
 * @summary Deterministic tests for java.util.Random.nextInt(int bound) method
 * @run main java.util.RandomWitupTest
 */

import java.util.Random;

public class RandomWitupTest {
    public static void main(String[] args) {
        testNextIntWithValidBounds();
        testNextIntWithBoundOne();
        testNextIntWithPowerOfTwoBounds();
        testNextIntWithNegativeAndZeroBounds();
        System.out.println("All tests passed.");
    }

    private static void testNextIntWithValidBounds() {
        Random rnd = new Random(12345L);
        int bound = 10;
        for (int i = 0; i < 100; i++) {
            int val = rnd.nextInt(bound);
            if (val < 0 || val >= bound) {
                throw new AssertionError("nextInt returned value out of range: " + val);
            }
        }
    }

    private static void testNextIntWithBoundOne() {
        Random rnd = new Random(67890L);
        int val = rnd.nextInt(1);
        if (val != 0) {
            throw new AssertionError("nextInt(1) should always return 0 but returned: " + val);
        }
    }

    private static void testNextIntWithPowerOfTwoBounds() {
        Random rnd = new Random(13579L);
        int[] powersOfTwo = {2, 4, 8, 16, 32, 64, 128, 256};
        for (int bound : powersOfTwo) {
            for (int i = 0; i < 50; i++) {
                int val = rnd.nextInt(bound);
                if (val < 0 || val >= bound) {
                    throw new AssertionError("nextInt with power-of-two bound returned out of range value: " + val);
                }
            }
        }
    }

    private static void testNextIntWithNegativeAndZeroBounds() {
        Random rnd = new Random();
        try {
            rnd.nextInt(0);
            throw new AssertionError("Expected IllegalArgumentException for bound=0");
        } catch (IllegalArgumentException e) {
            // expected
        }
        try {
            rnd.nextInt(-1);
            throw new AssertionError("Expected IllegalArgumentException for bound=-1");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
