/* @test    @summary Test for ThreadLocalRandom.nextInt(int, int) method    @run main ThreadLocalRandomWitupTest */

import java.util.concurrent.ThreadLocalRandom;

public class ThreadLocalRandomWitupTest {

    public static void main(String[] args) {
        testValidRange();
        testInvalidRange();
        testEdgeCases();
    }

    static void testValidRange() {
        ThreadLocalRandom rand = ThreadLocalRandom.current();
        int origin = 10;
        int bound = 20;
        for (int i = 0; i < 100; i++) {
            int result = rand.nextInt(origin, bound);
            assert result >= origin && result < bound : "Result out of range: " + result;
        }
    }

    static void testInvalidRange() {
        ThreadLocalRandom rand = ThreadLocalRandom.current();
        int origin = 20;
        int bound = 10;
        boolean exceptionThrown = false;
        try {
            rand.nextInt(origin, bound);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected IllegalArgumentException for invalid range";
    }

    static void testEdgeCases() {
        ThreadLocalRandom rand = ThreadLocalRandom.current();
        int origin = 5;
        int bound = 5;
        boolean exceptionThrown = false;
        try {
            rand.nextInt(origin, bound);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected IllegalArgumentException when origin == bound";

        origin = Integer.MIN_VALUE;
        bound = Integer.MAX_VALUE;
        for (int i = 0; i < 50; i++) {
            int result = rand.nextInt(origin, bound);
            assert result >= origin && result < bound : "Result out of range: " + result;
        }
    }
}
