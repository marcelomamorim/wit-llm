/*
 * @test
 * @summary Test DoubleStream.iterate method behavior
 * @run main DoubleStreamWitupTest
 */

import java.util.stream.DoubleStream;
import java.util.function.DoubleUnaryOperator;
import java.util.NoSuchElementException;

public class DoubleStreamWitupTest {
    public static void main(String[] args) {
        testIterateBasicSequence();
        testIterateWithIdentityFunction();
        testIterateWithNegativeSeed();
        testIterateWithNullFunction();
    }

    private static void testIterateBasicSequence() {
        DoubleUnaryOperator op = x -> x + 1.0;
        DoubleStream ds = DoubleStream.iterate(0.0, op);
        double[] results = ds.limit(5).toArray();
        double[] expected = {0.0, 1.0, 2.0, 3.0, 4.0};
        for (int i = 0; i < expected.length; i++) {
            if (Double.compare(results[i], expected[i]) != 0) {
                throw new AssertionError("testIterateBasicSequence failed at index " + i + ": expected " + expected[i] + ", got " + results[i]);
            }
        }
    }

    private static void testIterateWithIdentityFunction() {
        DoubleUnaryOperator op = x -> x;
        DoubleStream ds = DoubleStream.iterate(42.0, op);
        double[] results = ds.limit(3).toArray();
        for (double d : results) {
            if (Double.compare(d, 42.0) != 0) {
                throw new AssertionError("testIterateWithIdentityFunction failed: expected 42.0, got " + d);
            }
        }
    }

    private static void testIterateWithNegativeSeed() {
        DoubleUnaryOperator op = x -> x - 2.0;
        DoubleStream ds = DoubleStream.iterate(-10.0, op);
        double[] results = ds.limit(4).toArray();
        double[] expected = {-10.0, -12.0, -14.0, -16.0};
        for (int i = 0; i < expected.length; i++) {
            if (Double.compare(results[i], expected[i]) != 0) {
                throw new AssertionError("testIterateWithNegativeSeed failed at index " + i + ": expected " + expected[i] + ", got " + results[i]);
            }
        }
    }

    private static void testIterateWithNullFunction() {
        try {
            DoubleStream.iterate(0.0, null);
            throw new AssertionError("testIterateWithNullFunction failed: expected NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
