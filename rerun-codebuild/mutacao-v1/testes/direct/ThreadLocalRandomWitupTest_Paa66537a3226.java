/* @test    @summary Test the ints() method of ThreadLocalRandom for correct stream generation    @run main ThreadLocalRandomWitupTest_Paa66537a3226 */

import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

public class ThreadLocalRandomWitupTest_Paa66537a3226 {
    public static void main(String[] args) {
        testIntsMethod();
    }

    static void testIntsMethod() {
        try {
            IntStream stream = java.util.concurrent.ThreadLocalRandom.current().ints();
            if (stream == null) {
                throw new AssertionError("ints() returned null");
            }
            // Collect a small number of elements to verify stream works
            int[] sample = stream.limit(10).toArray();
            if (sample.length != 10) {
                throw new AssertionError("Stream did not produce 10 elements");
            }
            // Verify that all elements are within Integer bounds (trivially true)
            for (int val : sample) {
                if (val < Integer.MIN_VALUE || val > Integer.MAX_VALUE) {
                    throw new AssertionError("Value out of int bounds: " + val);
                }
            }
        } catch (Exception e) {
            throw new AssertionError("Exception during ints() test: " + e, e);
        }
    }
}
