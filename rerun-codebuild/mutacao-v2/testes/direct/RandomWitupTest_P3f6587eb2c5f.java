/*
 * @test
 * @summary Test java.util.Random.doubles() produces a DoubleStream with expected properties
 * @run main RandomWitupTest_P3f6587eb2c5f
 */

import java.util.Random;
import java.util.stream.DoubleStream;

public class RandomWitupTest_P3f6587eb2c5f {
    public static void main(String[] args) {
        Random rng = new Random(12345L);
        DoubleStream ds = rng.doubles();

        // Check that the stream is not null
        if (ds == null) {
            throw new AssertionError("doubles() returned null stream");
        }

        // Take first 5 elements and check they are in [0.0, 1.0)
        double[] values = ds.limit(5).toArray();
        if (values.length != 5) {
            throw new AssertionError("Expected 5 doubles, got " + values.length);
        }
        for (int i = 0; i < values.length; i++) {
            double v = values[i];
            if (v < 0.0 || v >= 1.0) {
                throw new AssertionError("Value out of range [0.0,1.0): " + v);
            }
        }

        // Check determinism: same seed produces same first value
        Random rng2 = new Random(12345L);
        double firstValue1 = values[0];
        double firstValue2 = rng2.doubles().findFirst().getAsDouble();
        if (firstValue1 != firstValue2) {
            throw new AssertionError("Streams with same seed produce different first values: " + firstValue1 + " vs " + firstValue2);
        }

        System.out.println("Test passed.");
    }
}
