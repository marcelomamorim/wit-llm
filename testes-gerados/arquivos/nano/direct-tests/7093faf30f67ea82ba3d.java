/* @test    @summary Test for ThreadLocalRandom.longs() method to ensure it returns a LongStream with expected properties.    @run main ThreadLocalRandomWitupTest */

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.LongStream;
import java.util.stream.StreamSupport;

public class ThreadLocalRandomWitupTest {

    public static void main(String[] args) {
        testLongsReturnsStream();
    }

    public static void testLongsReturnsStream() {
        LongStream stream = ThreadLocalRandom.current().longs();
        // Verify that the stream is not null
        if (stream == null) {
            throw new AssertionError("longs() returned null");
        }
        // Verify that the stream has expected size (by default, unlimited, so test a limited subset)
        long count = stream.limit(10).count();
        if (count != 10) {
            throw new AssertionError("Expected stream to produce 10 elements, but got " + count);
        }
        // Verify that the stream elements are within the expected range
        stream = ThreadLocalRandom.current().longs();
        long min = Long.MAX_VALUE;
        long max = Long.MIN_VALUE;
        for (long val : stream.limit(100).toArray()) {
            if (val < min) min = val;
            if (val > max) max = val;
        }
        if (min < 0 || max > Long.MAX_VALUE) {
            throw new AssertionError("Stream elements out of expected range: min=" + min + ", max=" + max);
        }
    }
}
