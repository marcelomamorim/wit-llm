/* @test
   @summary Verifies that the method ints() returns a non-null IntStream.
   @run main ThreadLocalRandomWitupTest_Pdd3aa0134de3
 */
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;
public class ThreadLocalRandomWitupTest_Pdd3aa0134de3 {
    public static void main(String[] args) {
        java.util.concurrent.ThreadLocalRandom r = java.util.concurrent.ThreadLocalRandom.current();
        IntStream stream = r.ints();
        if (stream == null) {
            throw new AssertionError("ints() returned null");
        }
        // Verify that the stream produces integers
        int count = 0;
        for (int val : stream.limit(10).toArray()) {
            if (val < Integer.MIN_VALUE || val > Integer.MAX_VALUE) {
                throw new AssertionError("Value out of int range: " + val);
            }
            count++;
        }
        if (count != 10) {
            throw new AssertionError("Stream did not produce 10 elements");
        }
    }
}
