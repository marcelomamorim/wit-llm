/*
 * @test
 * @summary Test for SplittableRandom.nextLong(long, long) method, including exception path.
 * @run main SplittableRandomWitupTest
 */

import java.util.SplittableRandom;

public class SplittableRandomWitupTest {

    public static void main(String[] args) {
        testNextLongValidRange();
        testNextLongInvalidRange();
    }

    static void testNextLongValidRange() {
        SplittableRandom sr = new SplittableRandom();
        long origin = 10L;
        long bound = 20L;
        long result = sr.nextLong(origin, bound);
        if (result < origin || result >= bound) {
            throw new AssertionError("Result out of range: " + result);
        }
    }

    static void testNextLongInvalidRange() {
        SplittableRandom sr = new SplittableRandom();
        long origin = 20L;
        long bound = 10L;
        try {
            sr.nextLong(origin, bound);
            throw new AssertionError("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
