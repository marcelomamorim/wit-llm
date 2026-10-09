/* @test    @summary Test for ThreadLocalRandom.longs() method to observe behavior and exception handling    @run main ThreadLocalRandomWitupTest_Pe6b6cc9081b7 */

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.LongStream;

public class ThreadLocalRandomWitupTest_Pe6b6cc9081b7 {
    public static void main(String[] args) {
        testLongsReturnsStream();
        testLongsStreamDoesNotThrow();
    }

    public static void testLongsReturnsStream() {
        LongStream result = ThreadLocalRandom.current().longs();
        if (result == null) {
            throw new AssertionError("longs() returned null");
        }
        // Optional: Consume some elements to verify stream is functional
        long sum = result.limit(10).sum();
        if (sum < 0) {
            throw new AssertionError("Stream sum should be non-negative");
        }
    }

    public static void testLongsStreamDoesNotThrow() {
        try {
            LongStream stream = ThreadLocalRandom.current().longs();
            stream.close();
        } catch (Throwable t) {
            throw new AssertionError("longs() should not throw", t);
        }
    }
}
