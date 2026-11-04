/*
 * @test
 * @summary Witup-derived tests for Random.doubles(double, double) range validation and determinism
 * @run main RandomWitupTest
 */
import java.util.Arrays;
import java.util.Random;
import java.util.stream.DoubleStream;

public class RandomWitupTest {
    public static void main(String[] args) {
        testInvalidRangeThrows(5.0, 5.0);
        testInvalidRangeThrows(10.0, 5.0);
        testInvalidRangeThrows(Double.NaN, 1.0);
        testInvalidRangeThrows(0.0, Double.NaN);
        testValidRangeValuesWithinBounds(2.0, 5.0);
        testValidRangeValuesWithinBounds(-1.0, 1.0);
        testSameSeedSameSequence();
    }

    static void testInvalidRangeThrows(double origin, double bound) {
        boolean thrown = false;
        try {
            new Random(1L).doubles(origin, bound);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException for origin=" + origin + " bound=" + bound);
        }
    }

    static void testValidRangeValuesWithinBounds(double origin, double bound) {
        DoubleStream s = new Random(42L).doubles(origin, bound);
        double[] values = s.limit(1000).toArray();
        if (values.length != 1000) {
            throw new RuntimeException("Unexpected count: " + values.length);
        }
        for (double v : values) {
            if (v < origin || v >= bound) {
                throw new RuntimeException("Value out of range: " + v);
            }
        }
    }

    static void testSameSeedSameSequence() {
        double[] a = new Random(7L).doubles(-1.0, 1.0).limit(100).toArray();
        double[] b = new Random(7L).doubles(-1.0, 1.0).limit(100).toArray();
        if (!Arrays.equals(a, b)) {
            throw new RuntimeException("Same seed produced different sequences");
        }
    }
}
