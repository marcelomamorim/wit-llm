/* @test    @summary Test Random.doubles() method for deterministic output and behavior    @run main RandomWitupTest_P8813c67b9191 */

import java.util.Random;
import java.util.stream.DoubleStream;
import java.util.stream.StreamSupport;

public class RandomWitupTest_P8813c67b9191 {
    public static void main(String[] args) {
        testDoublesReturnsStream();
        testDoublesDeterminism();
        testDoublesEmptyStream();
        System.out.println("All tests passed.");
    }

    private static void testDoublesReturnsStream() {
        Random rand = new Random(12345L);
        DoubleStream stream = rand.doubles();
        if (stream == null) {
            throw new AssertionError("doubles() returned null");
        }
        // Consume some elements to ensure stream is functional
        double[] sample = stream.limit(5).toArray();
        if (sample.length != 5) {
            throw new AssertionError("doubles() stream did not produce 5 elements");
        }
        for (double d : sample) {
            if (Double.isNaN(d) || Double.isInfinite(d)) {
                throw new AssertionError("doubles() produced NaN or Infinite value");
            }
        }
    }

    private static void testDoublesDeterminism() {
        long seed = 98765L;
        Random rand1 = new Random(seed);
        Random rand2 = new Random(seed);
        DoubleStream stream1 = rand1.doubles();
        DoubleStream stream2 = rand2.doubles();
        double[] arr1 = stream1.limit(100).toArray();
        double[] arr2 = stream2.limit(100).toArray();
        if (arr1.length != arr2.length) {
            throw new AssertionError("Streams produced different lengths");
        }
        for (int i = 0; i < arr1.length; i++) {
            if (Double.compare(arr1[i], arr2[i]) != 0) {
                throw new AssertionError("Streams differ at index " + i);
            }
        }
    }

    private static void testDoublesEmptyStream() {
        Random rand = new Random(0L);
        DoubleStream stream = rand.doubles();
        DoubleStream limited = stream.limit(0);
        if (limited.count() != 0) {
            throw new AssertionError("Empty stream should have count 0");
        }
    }
}
