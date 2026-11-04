/*
 * @test
 * @summary Test ThreadLocalRandom.longs() returns a LongStream with expected properties
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.LongStream;

public class ThreadLocalRandomWitupTest {
    public static void main(String[] args) {
        ThreadLocalRandom tlr = ThreadLocalRandom.current();
        LongStream stream = tlr.longs();

        // Check stream is not null
        if (stream == null) {
            throw new AssertionError("LongStream returned by longs() is null");
        }

        // Check stream is infinite by trying to limit and count
        long count = stream.limit(1000).count();
        if (count != 1000) {
            throw new AssertionError("LongStream does not produce expected number of elements");
        }

        // Check values are within expected range [0, Long.MAX_VALUE]
        // We test a sample of 1000 values
        boolean outOfRange = stream.limit(1000).anyMatch(l -> l < 0 || l > Long.MAX_VALUE);
        if (outOfRange) {
            throw new AssertionError("LongStream produced values out of expected range");
        }

        System.out.println("Test passed: longs() produces a valid LongStream of values in [0, Long.MAX_VALUE]");
    }
}
