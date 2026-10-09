/*
 * @test
 * @summary Deterministic tests for java.util.Random
 * @run main RandomWitupTest_P1f70e29fca90
 */

import java.util.Random;

public class RandomWitupTest_P1f70e29fca90 {
    public static void main(String[] args) {
        testNextIntWithPositiveBound();
        testNextIntWithZeroBound();
        testNextIntWithNegativeBound();
    }

    static void testNextIntWithPositiveBound() {
        Random rnd = new Random(12345L);
        int result = rnd.nextInt(10);
        if (result < 0 || result >= 10) {
            throw new AssertionError("nextInt(10) out of range: " + result);
        }
    }

    static void testNextIntWithZeroBound() {
        Random rnd = new Random(12345L);
        boolean exceptionThrown = false;
        try {
            rnd.nextInt(0);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException for nextInt(0)");
        }
    }

    static void testNextIntWithNegativeBound() {
        Random rnd = new Random(12345L);
        boolean exceptionThrown = false;
        try {
            rnd.nextInt(-5);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException for nextInt(-5)");
        }
    }
}
