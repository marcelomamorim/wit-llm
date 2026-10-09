/* @test    @summary Test nextLong with valid range in ThreadLocalRandom    @run main ThreadLocalRandomWitupTest_P78cd43d50163 */

import java.util.concurrent.ThreadLocalRandom;

public class ThreadLocalRandomWitupTest_P78cd43d50163 {

    public static void main(String[] args) {
        testNextLongInRange();
        testNextLongInvalidRange();
    }

    static void testNextLongInRange() {
        long origin = 10L;
        long bound = 20L;
        long result = ThreadLocalRandom.current().nextLong(origin, bound);
        assert result >= origin && result < bound : "Result should be within range [" + origin + ", " + bound + ") but was " + result;
    }

    static void testNextLongInvalidRange() {
        long origin = 30L;
        long bound = 20L;
        try {
            ThreadLocalRandom.current().nextLong(origin, bound);
            assert false : "Expected IllegalArgumentException for invalid range";
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
