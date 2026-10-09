/* @test
   @summary Testa o método setSeed de ThreadLocalRandom para comportamento de exceção.
   @run main ThreadLocalRandomWitupTest_Pbfa1aecc72e8
 */
import java.util.concurrent.ThreadLocalRandom;
public class ThreadLocalRandomWitupTest_Pbfa1aecc72e8 {
    public static void main(String[] args) {
        ThreadLocalRandom r = ThreadLocalRandom.current();
        boolean exceptionThrown = false;
        try {
            r.setSeed(12345L);
        } catch (UnsupportedOperationException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected UnsupportedOperationException to be thrown");
        }
    }
}
