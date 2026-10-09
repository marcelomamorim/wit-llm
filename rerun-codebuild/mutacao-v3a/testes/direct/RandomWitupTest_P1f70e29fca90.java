/*
 * @test
 * @summary Test Random.nextInt(int) for correctness and edge cases
 * @run main RandomWitupTest_P1f70e29fca90
 */

import java.util.Random;

public class RandomWitupTest_P1f70e29fca90 {

    public static void main(String[] args) {
        testNextIntPositiveBound();
        testNextIntZeroBound();
        testNextIntNegativeBound();
        testNextIntPowerOfTwoBound();
        testNextIntLargeBound();
    }

    static void testNextIntPositiveBound() {
        Random rnd = new Random(12345L);
        int bound = 10;
        for (int i = 0; i < 100; i++) {
            int result = rnd.nextInt(bound);
            assert result >= 0 && result < bound : "nextInt(" + bound + ") out of range: " + result;
        }
    }

    static void testNextIntZeroBound() {
        Random rnd = new Random(54321L);
        try {
            rnd.nextInt(0);
            assert false : "Expected IllegalArgumentException for bound=0";
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testNextIntNegativeBound() {
        Random rnd = new Random(67890L);
        try {
            rnd.nextInt(-5);
            assert false : "Expected IllegalArgumentException for negative bound";
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testNextIntPowerOfTwoBound() {
        Random rnd = new Random(98765L);
        int bound = 8; // power of two
        for (int i = 0; i < 50; i++) {
            int result = rnd.nextInt(bound);
            assert result >= 0 && result < bound : "nextInt(" + bound + ") out of range: " + result;
        }
    }

    static void testNextIntLargeBound() {
        Random rnd = new Random(13579L);
        int bound = Integer.MAX_VALUE;
        int result = rnd.nextInt(bound);
        assert result >= 0 && result < bound : "nextInt(" + bound + ") out of range: " + result;
    }
}
