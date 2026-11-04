/*
 * @test
 * @summary Test ThreadLocalRandom.doubles() produces a DoubleStream with expected properties
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.DoubleStream;

public class ThreadLocalRandomWitupTest {
    public static void main(String[] args) {
        ThreadLocalRandom tlr = ThreadLocalRandom.current();
        DoubleStream ds = tlr.doubles();

        // Test that the stream is not null
        if (ds == null) {
            throw new AssertionError("doubles() returned null stream");
        }

        // Test that the stream produces values within [0.0, Double.MAX_VALUE]
        // We test a small sample of values to keep test fast and deterministic
        final int sampleSize = 1000;
        final double max = Double.MAX_VALUE;
        final double min = 0.0;

        // Use try-with-resources to ensure stream is closed
        try (DoubleStream stream = ds.limit(sampleSize)) {
            final boolean[] valid = {true};
            stream.forEach(d -> {
                if (d < min || d > max || Double.isNaN(d) || Double.isInfinite(d)) {
                    valid[0] = false;
                }
            });
            if (!valid[0]) {
                throw new AssertionError("doubles() produced value out of range or invalid");
            }
        }

        // Test that the stream is sequential (not parallel)
        if (ds.isParallel()) {
            throw new AssertionError("doubles() stream should not be parallel");
        }
    }
}
