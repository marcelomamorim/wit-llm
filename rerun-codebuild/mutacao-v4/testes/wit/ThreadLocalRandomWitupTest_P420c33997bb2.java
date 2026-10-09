/* @test
   @summary Testa o método nextLong(long, long) de ThreadLocalRandom para entradas válidas e inválidas.
   @run main ThreadLocalRandomWitupTest_P420c33997bb2
 */
import java.util.concurrent.ThreadLocalRandom;
public class ThreadLocalRandomWitupTest_P420c33997bb2 {
    public static void main(String[] args) {
        testValidRange();
        testInvalidRange();
    }
    private static void testValidRange() {
        long origin = 10L;
        long bound = 20L;
        long result = ThreadLocalRandom.current().nextLong(origin, bound);
        if (result < origin || result >= bound) {
            throw new AssertionError("Result out of range: " + result);
        }
    }
    private static void testInvalidRange() {
        long origin = 20L;
        long bound = 10L;
        boolean exceptionThrown = false;
        try {
            ThreadLocalRandom.current().nextLong(origin, bound);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException for bound <= origin");
        }
    }
}
