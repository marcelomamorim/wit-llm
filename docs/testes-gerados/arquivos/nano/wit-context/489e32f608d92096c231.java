/*
 * @test
 * @summary Test for SplittableRandom.nextInt(int, int) method, including exception behavior.
 * @run main SplittableRandomWitupTest
 */

import java.util.SplittableRandom;

public class SplittableRandomWitupTest {

    public static void main(String[] args) {
        testValidRange();
        testInvalidRange();
    }

    static void testValidRange() {
        SplittableRandom sr = new SplittableRandom();
        int origin = 10;
        int bound = 20;
        int result = sr.nextInt(origin, bound);
        if (result < origin || result >= bound) {
            throw new AssertionError("Result out of range: " + result);
        }
    }

    static void testInvalidRange() {
        SplittableRandom sr = new SplittableRandom();
        int origin = 30;
        int bound = 20;
        try {
            sr.nextInt(origin, bound);
            throw new AssertionError("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
