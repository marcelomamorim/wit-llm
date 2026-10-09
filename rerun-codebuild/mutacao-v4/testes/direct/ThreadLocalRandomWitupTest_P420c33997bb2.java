/* @test
   @summary Tests the nextLong(long origin, long bound) method of ThreadLocalRandom.
   @run main ThreadLocalRandomWitupTest_P420c33997bb2
 */
import java.util.concurrent.ThreadLocalRandom;
public class ThreadLocalRandomWitupTest_P420c33997bb2 {
    public static void main(String[] args) {
        testValidRange();
        testInvalidRange();
        testEdgeCases();
    }
    private static void testValidRange() {
        long origin = 10L;
        long bound = 20L;
        long result = ThreadLocalRandom.current().nextLong(origin, bound);
        if (result < origin || result >= bound) {
            throw new AssertionError("Result " + result + " not in range [" + origin + ", " + bound + ")");
        }
    }
    private static void testInvalidRange() {
        long origin = 30L;
        long bound = 20L;
        boolean exceptionThrown = false;
        try {
            ThreadLocalRandom.current().nextLong(origin, bound);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException for invalid range") ;
        }
    }
    private static void testEdgeCases() {
        long origin = Long.MIN_VALUE;
        long bound = Long.MIN_VALUE + 1;
        long result = ThreadLocalRandom.current().nextLong(origin, bound);
        if (result != origin) {
            throw new AssertionError("Edge case result should be " + origin + " but was " + result);
        }
        origin = Long.MAX_VALUE - 1;
        bound = Long.MAX_VALUE;
        long res = ThreadLocalRandom.current().nextLong(origin, bound);
        if (res != origin) {
            throw new AssertionError("Edge case result should be " + origin + " but was " + res);
        }
    }
}
