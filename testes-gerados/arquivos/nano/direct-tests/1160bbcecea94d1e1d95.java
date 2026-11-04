/*
 * @test
 * @summary Test ThreadLocalRandom.nextInt(int) for valid and invalid bounds
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.concurrent.ThreadLocalRandom;

public class ThreadLocalRandomWitupTest {

    public static void main(String[] args) {
        testNextIntWithValidBound();
        testNextIntWithZeroBound();
        testNextIntWithNegativeBound();
    }

    static void testNextIntWithValidBound() {
        int bound = 10;
        int result = ThreadLocalRandom.current().nextInt(bound);
        assert result >= 0 && result < bound : "Result should be within [0, " + bound + ") but was " + result;
    }

    static void testNextIntWithZeroBound() {
        try {
            ThreadLocalRandom.current().nextInt(0);
            assert false : "Expected IllegalArgumentException for zero bound";
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testNextIntWithNegativeBound() {
        try {
            ThreadLocalRandom.current().nextInt(-5);
            assert false : "Expected IllegalArgumentException for negative bound";
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
