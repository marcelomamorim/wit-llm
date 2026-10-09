/* @test
   @summary Testa o método ints() de ThreadLocalRandom.
   @run main ThreadLocalRandomWitupTest_Pdd3aa0134de3
 */
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class ThreadLocalRandomWitupTest_Pdd3aa0134de3 {
    public static void main(String[] args) {
        testIntsMethod();
    }

    public static void testIntsMethod() {
        try {
            IntStream stream = ThreadLocalRandom.current().ints();
            if (stream == null) {
                throw new AssertionError("Stream should not be null");
            }
            // Consome alguns elementos para garantir que o stream funciona
            int sum = stream.limit(10).sum();
            if (sum < 0) {
                throw new AssertionError("Sum of first 10 ints should be >= 0");
            }
        } catch (Exception e) {
            throw new AssertionError("Exception during ints() test: " + e);
        }
    }
}
