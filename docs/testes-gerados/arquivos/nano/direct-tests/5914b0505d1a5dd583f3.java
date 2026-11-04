/* @test    @summary Test SplittableRandom.nextLong(long, long) for valid and invalid ranges    @run main SplittableRandomWitupTest */

import java.util.SplittableRandom;

public class SplittableRandomWitupTest {

    public static void main(String[] args) {
        testValidRange();
        testInvalidRange();
    }

    static void testValidRange() {
        SplittableRandom sr = new SplittableRandom(12345L);
        long origin = 10L;
        long bound = 20L;
        long result = sr.nextLong(origin, bound);
        if (result < origin || result >= bound) {
            throw new AssertionError("Result " + result + " not in range [" + origin + ", " + bound + ")");
        }
    }

    static void testInvalidRange() {
        SplittableRandom sr = new SplittableRandom(54321L);
        long origin = 30L;
        long bound = 30L;
        try {
            sr.nextLong(origin, bound);
            throw new AssertionError("Expected IllegalArgumentException for equal bounds");
        } catch (IllegalArgumentException e) {
            // expected
        }
        origin = 50L;
        bound = 40L;
        try {
            sr.nextLong(origin, bound);
            throw new AssertionError("Expected IllegalArgumentException for origin > bound");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
