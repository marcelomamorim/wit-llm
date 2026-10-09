/* @test
   @summary Verify that calling setSeed on ThreadLocalRandom throws UnsupportedOperationException after initialization.
   @run main ThreadLocalRandomWitupTest_Pbfa1aecc72e8
 */
import java.util.concurrent.ThreadLocalRandom;
public class ThreadLocalRandomWitupTest_Pbfa1aecc72e8 {
    public static void main(String[] args) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        try {
            random.setSeed(12345L);
            throw new AssertionError("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            // expected
        }
    }
}
