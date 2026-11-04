/*
 * @test
 * @summary Test java.util.SplittableRandom.doubles() method for basic functionality
 * @run main SplittableRandomWitupTest
 */

import java.util.SplittableRandom;
import java.util.stream.DoubleStream;

public class SplittableRandomWitupTest {
    public static void main(String[] args) {
        SplittableRandom sr = new SplittableRandom(12345L);
        DoubleStream ds = sr.doubles();
        // Test that stream is not null
        if (ds == null) {
            throw new AssertionError("doubles() returned null stream");
        }
        // Test that stream produces values within expected range [0.0, Double.MAX_VALUE)
        // We test first 100 values
        final int limit = 100;
        double[] values = ds.limit(limit).toArray();
        if (values.length != limit) {
            throw new AssertionError("Stream did not produce expected number of values");
        }
        for (int i = 0; i < values.length; i++) {
            double v = values[i];
            if (v < 0.0 || v >= Double.MAX_VALUE) {
                throw new AssertionError("Value out of range: " + v);
            }
            if (Double.isNaN(v)) {
                throw new AssertionError("Value is NaN");
            }
            if (Double.isInfinite(v)) {
                throw new AssertionError("Value is infinite");
            }
        }
        System.out.println("Test passed: doubles() produces valid stream of doubles");
    }
}
