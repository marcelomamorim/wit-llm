/* @test
   @summary Tests the nextDouble(double origin, double bound) method of ThreadLocalRandom.
   @run main ThreadLocalRandomWitupTest_P0ae458f3bbbc
 */
import java.util.concurrent.ThreadLocalRandom;
public class ThreadLocalRandomWitupTest_P0ae458f3bbbc {
    public static void main(String[] args) {
        testValidRange();
        testInvalidRange();
    }
    private static void testValidRange() {
        double origin = 1.0;
        double bound = 2.0;
        double result = ThreadLocalRandom.current().nextDouble(origin, bound);
        if (!(result >= origin && result < bound)) {
            throw new AssertionError("Result " + result + " not in range [" + origin + ", " + bound + ")");
        }
    }
    private static void testInvalidRange() {
        double origin = 5.0;
        double bound = 3.0;
        boolean exceptionThrown = false;
        try {
            ThreadLocalRandom.current().nextDouble(origin, bound);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException for invalid range")
        }
    }
}
