/*
 * @test
 * @summary Deterministic jtreg test for ThreadLocalRandom.doubles()
 * @run main ThreadLocalRandomWitupTest_P643cd09790b6
 */

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.DoubleStream;

public class ThreadLocalRandomWitupTest_P643cd09790b6 {
    public static void main(String[] args) {
        ThreadLocalRandom tlr = ThreadLocalRandom.current();

        // Test that doubles() returns a non-null DoubleStream
        DoubleStream ds = tlr.doubles();
        if (ds == null) {
            throw new AssertionError("doubles() returned null");
        }

        // Test that the stream produces values within expected bounds
        // The default doubles() uses origin=0.0 and bound=Double.MAX_VALUE
        // We test first 100 values to be >= 0.0 and < Double.MAX_VALUE
        final double origin = 0.0;
        final double bound = Double.MAX_VALUE;

        final int limit = 100;
        long count = ds.limit(limit).peek(d -> {
            if (d < origin) {
                throw new AssertionError("Value " + d + " less than origin " + origin);
            }
            if (!(d < bound)) {
                throw new AssertionError("Value " + d + " not less than bound " + bound);
            }
        }).count();

        if (count != limit) {
            throw new AssertionError("Stream did not produce expected number of elements: " + count);
        }
    }
}
