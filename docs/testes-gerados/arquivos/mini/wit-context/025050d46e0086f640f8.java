/*
 * @test
 * @summary Test deterministic behavior of SplittableRandom.doubles()
 * @run main SplittableRandomWitupTest
 */

import java.util.SplittableRandom;
import java.util.stream.DoubleStream;

public class SplittableRandomWitupTest {
    public static void main(String[] args) {
        SplittableRandom sr = new SplittableRandom(12345L);

        // Test that doubles() returns a DoubleStream
        DoubleStream ds = sr.doubles();
        if (ds == null) {
            throw new AssertionError("doubles() returned null");
        }

        // Test that the stream is finite or infinite as expected
        // According to source, it should be effectively infinite (Long.MAX_VALUE)
        // We test that it can produce at least some values without error
        double[] values = ds.limit(5).toArray();
        if (values.length != 5) {
            throw new AssertionError("doubles() stream did not produce 5 values");
        }

        // Test that all values are within the expected range [0.0, Double.MAX_VALUE)
        for (double v : values) {
            if (v < 0.0 || v >= Double.MAX_VALUE) {
                throw new AssertionError("Value out of expected range: " + v);
            }
        }

        // Test that multiple calls produce different sequences
        SplittableRandom sr2 = new SplittableRandom(12345L);
        double[] values2 = sr2.doubles().limit(5).toArray();

        boolean allEqual = true;
        for (int i = 0; i < 5; i++) {
            if (values[i] != values2[i]) {
                allEqual = false;
                break;
            }
        }

        if (!allEqual) {
            throw new AssertionError("Two SplittableRandom instances with same seed produced different sequences");
        }

        // Test that different seeds produce different sequences
        SplittableRandom sr3 = new SplittableRandom(54321L);
        double[] values3 = sr3.doubles().limit(5).toArray();

        boolean allEqualSeedDiff = true;
        for (int i = 0; i < 5; i++) {
            if (values[i] != values3[i]) {
                allEqualSeedDiff = false;
                break;
            }
        }

        if (allEqualSeedDiff) {
            throw new AssertionError("Different seeds produced identical sequences");
        }

        System.out.println("All tests passed.");
    }
}
