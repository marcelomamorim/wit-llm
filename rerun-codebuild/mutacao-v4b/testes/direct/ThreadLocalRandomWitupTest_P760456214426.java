/* @test
   @summary Tests the behavior of ThreadLocalRandom.nextInt(int, int).
   @run main ThreadLocalRandomWitupTest_P760456214426
 */
import java.util.concurrent.ThreadLocalRandom;
public class ThreadLocalRandomWitupTest_P760456214426 {
    public static void main(String[] args) {
        testValidRange();
        testInvalidRange();
    }
    private static void testValidRange() {
        int origin = 10;
        int bound = 20;
        int result = ThreadLocalRandom.current().nextInt(origin, bound);
        if (result < origin || result >= bound) {
            throw new AssertionError("Result " + result + " not in range [" + origin + ", " + bound + ")");
        }
    }
    private static void testInvalidRange() {
        int origin = 30;
        int bound = 20;
        boolean exceptionThrown = false;
        try {
            ThreadLocalRandom.current().nextInt(origin, bound);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException for invalid range") ;
        }
    }
}
