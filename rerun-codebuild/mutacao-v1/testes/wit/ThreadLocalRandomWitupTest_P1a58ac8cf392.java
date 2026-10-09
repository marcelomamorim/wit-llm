/* @test    @summary Test for ThreadLocalRandom.longs() method to observe behavior and exception handling    @run main ThreadLocalRandomWitupTest_P1a58ac8cf392 */

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.LongStream;

public class ThreadLocalRandomWitupTest_P1a58ac8cf392 {

    public static void main(String[] args) {
        testLongsMethod();
    }

    private static void testLongsMethod() {
        try {
            LongStream stream = ThreadLocalRandom.current().longs();
            if (stream == null) {
                throw new AssertionError("longs() returned null");
            }
            // Consuming some elements to verify stream is operational
            long sum = stream.limit(10).sum();
            System.out.println("Sum of first 10 longs: " + sum);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception in longs()", e);
        }
    }
}
