/*
 * @test
 * @summary Test java.util.Random.doubles() method for basic deterministic behavior
 * @run main RandomWitupTest
 */

import java.util.Random;
import java.util.stream.DoubleStream;

public class RandomWitupTest {
    public static void main(String[] args) {
        Random rnd = new Random(12345L);
        DoubleStream ds = rnd.doubles();

        // Test that the stream is not null
        if (ds == null) {
            throw new AssertionError("doubles() returned null stream");
        }

        // Test that the stream produces expected first few values deterministically
        double[] expected = new double[] {
            0.7239910419592857,
            0.5965169276955123,
            0.15837492709589052,
            0.9272609277858737,
            0.9272609277858737
        };

        double[] actual = ds.limit(5).toArray();

        for (int i = 0; i < expected.length; i++) {
            if (Double.compare(expected[i], actual[i]) != 0) {
                throw new AssertionError("Mismatch at index " + i + ": expected " + expected[i] + ", got " + actual[i]);
            }
        }

        System.out.println("Random.doubles() basic deterministic test passed.");
    }
}
