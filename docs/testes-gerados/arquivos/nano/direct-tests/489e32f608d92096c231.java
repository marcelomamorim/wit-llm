/* @test    @summary Test SplittableRandom.nextInt(int, int) for valid and invalid ranges    @run main SplittableRandomWitupTest */

import java.util.SplittableRandom;

public class SplittableRandomWitupTest {

    public static void main(String[] args) {
        testValidRange();
        testInvalidRange();
    }

    static void testValidRange() {
        SplittableRandom sr = new SplittableRandom(12345L);
        int origin = 10;
        int bound = 20;
        for (int i = 0; i < 100; i++) {
            int result = sr.nextInt(origin, bound);
            assert result >= origin && result < bound : "Result out of range: " + result;
        }
    }

    static void testInvalidRange() {
        SplittableRandom sr = new SplittableRandom(54321L);
        int origin = 30;
        int bound = 30;
        boolean exceptionThrown = false;
        try {
            sr.nextInt(origin, bound);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected IllegalArgumentException for origin == bound";

        exceptionThrown = false;
        try {
            sr.nextInt(50, 40);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected IllegalArgumentException for origin > bound";
    }
}
