/*
 * @test
 * @summary Basic observable behavior of ThreadLocalRandom.longs() unbounded stream
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.LongStream;

public class ThreadLocalRandomWitupTest {

    public static void main(String[] args) {
        LongStream stream = ThreadLocalRandom.current().longs();
        if (stream == null) {
            throw new RuntimeException("longs() returned null");
        }
        if (stream.isParallel()) {
            throw new RuntimeException("longs() stream should be sequential");
        }

        long[] values = ThreadLocalRandom.current().longs().limit(16).toArray();
        if (values.length != 16) {
            throw new RuntimeException("Expected 16 values but got " + values.length);
        }

        long count = ThreadLocalRandom.current().longs().limit(1000).count();
        if (count != 1000L) {
            throw new RuntimeException("Expected 1000 elements but got " + count);
        }
    }
}
