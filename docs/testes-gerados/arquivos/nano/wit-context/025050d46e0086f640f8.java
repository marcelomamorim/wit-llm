/*
 * @test
 * @summary Test for the doubles() method in java.util.SplittableRandom
 * @run main SplittableRandomWitupTest
 */

import java.util.SplittableRandom;
import java.util.stream.DoubleStream;

public class SplittableRandomWitupTest {

    public static void main(String[] args) {
        testDoublesReturnsStream();
        testDoublesNotNull();
    }

    private static void testDoublesReturnsStream() {
        SplittableRandom sr = new SplittableRandom();
        DoubleStream ds = sr.doubles();
        if (ds == null) {
            throw new AssertionError("doubles() returned null");
        }
        // Consume some elements to verify stream is operational
        double sum = ds.limit(10).sum();
        if (Double.isNaN(sum)) {
            throw new AssertionError("Sum of doubles stream is NaN");
        }
    }

    private static void testDoublesNotNull() {
        SplittableRandom sr = new SplittableRandom();
        DoubleStream ds = sr.doubles();
        if (ds == null) {
            throw new AssertionError("doubles() returned null");
        }
    }
}
