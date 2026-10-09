/* @test    @summary Test the longs() method of ThreadLocalRandom for correct stream generation    @run main ThreadLocalRandomWitupTest_P1a58ac8cf392 */

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.LongStream;
import java.util.stream.StreamSupport;

public class ThreadLocalRandomWitupTest_P1a58ac8cf392 {
    public static void main(String[] args) {
        testLongsMethod();
    }

    static void testLongsMethod() {
        LongStream stream = ThreadLocalRandom.current().longs();
        if (stream == null) {
            throw new AssertionError("longs() returned null");
        }
        long count = stream.limit(10).count();
        if (count != 10) {
            throw new AssertionError("Expected 10 elements, got " + count);
        }
        long min = Long.MAX_VALUE;
        long max = Long.MIN_VALUE;
        for (long val : stream.limit(100).toArray()) {
            if (val < min) min = val;
            if (val > max) max = val;
        }
        if (min < 0 || max > Long.MAX_VALUE) {
            throw new AssertionError("Values out of expected range: min=" + min + ", max=" + max);
        }
        System.out.println("PASS: longs() stream generates values within expected range and size.");
    }
}
