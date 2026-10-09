/* @test
   @summary Verifies that ThreadLocalRandom.longs() returns a LongStream and does not throw exceptions under normal conditions.
   @run main ThreadLocalRandomWitupTest_Pdc360aaec7bc
 */
import java.util.concurrent.ThreadLocalRandom;
public class ThreadLocalRandomWitupTest_Pdc360aaec7bc {
    public static void main(String[] args) {
        ThreadLocalRandom r = ThreadLocalRandom.current();
        try {
            r.longs();
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e, e);
        }
    }
}
