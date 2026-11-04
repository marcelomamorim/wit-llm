/* @test    @summary Test the doubles() method of java.util.Random for observable behavior.    @run main RandomWitupTest */

import java.util.Random;
import java.util.stream.DoubleStream;

public class RandomWitupTest {

    public static void main(String[] args) {
        testDoublesReturnsStream();
        testDoublesDeterminism();
    }

    private static void testDoublesReturnsStream() {
        Random rand = new Random(12345L);
        DoubleStream ds = rand.doubles();
        if (ds == null) {
            throw new AssertionError("doubles() returned null");
        }
        // Consume some elements to verify stream is functional
        double[] sample = ds.limit(5).toArray();
        if (sample.length != 5) {
            throw new AssertionError("doubles() stream did not produce 5 elements");
        }
        // Check that values are within expected range (0.0 to 1.0)
        for (double d : sample) {
            if (d < 0.0 || d > 1.0) {
                throw new AssertionError("doubles() produced value out of range: " + d);
            }
        }
    }

    private static void testDoublesDeterminism() {
        Random rand1 = new Random(54321L);
        Random rand2 = new Random(54321L);
        DoubleStream ds1 = rand1.doubles();
        DoubleStream ds2 = rand2.doubles();
        double[] arr1 = ds1.limit(10).toArray();
        double[] arr2 = ds2.limit(10).toArray();
        if (arr1.length != arr2.length) {
            throw new AssertionError("Streams length mismatch");
        }
        for (int i = 0; i < arr1.length; i++) {
            if (Double.compare(arr1[i], arr2[i]) != 0) {
                throw new AssertionError("Determinism failed at index " + i + ": " + arr1[i] + " != " + arr2[i]);
            }
        }
    }
}
