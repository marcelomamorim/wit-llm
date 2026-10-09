/* @test    @summary Test Random.doubles() method for deterministic output and behavior    @run main RandomWitupTest_Pdfa1a531a11c */

import java.util.Random;
import java.util.stream.DoubleStream;
import java.util.stream.StreamSupport;

public class RandomWitupTest_Pdfa1a531a11c {

    public static void main(String[] args) {
        testDoublesMethod();
    }

    private static void testDoublesMethod() {
        Random random1 = new Random(12345L);
        Random random2 = new Random(12345L);

        DoubleStream stream1 = random1.doubles();
        DoubleStream stream2 = random2.doubles();

        double[] array1 = stream1.limit(10).toArray();
        double[] array2 = stream2.limit(10).toArray();

        for (int i = 0; i < array1.length; i++) {
            if (Double.compare(array1[i], array2[i]) != 0) {
                throw new AssertionError("Mismatch at index " + i + ": " + array1[i] + " != " + array2[i]);
            }
        }

        // Test that multiple calls produce consistent results for the same seed
        Random random3 = new Random(12345L);
        DoubleStream stream3 = random3.doubles();
        double[] array3 = stream3.limit(10).toArray();

        for (int i = 0; i < array1.length; i++) {
            if (Double.compare(array1[i], array3[i]) != 0) {
                throw new AssertionError("Mismatch between first and third stream at index " + i + ": " + array1[i] + " != " + array3[i]);
            }
        }

        // Test that different seeds produce different sequences (high probability)
        Random randomDifferent = new Random(54321L);
        DoubleStream streamDifferent = randomDifferent.doubles();
        double[] arrayDifferent = streamDifferent.limit(10).toArray();

        boolean allEqual = true;
        for (int i = 0; i < array1.length; i++) {
            if (Double.compare(array1[i], arrayDifferent[i]) == 0) {
                allEqual = false;
                break;
            }
        }
        if (allEqual) {
            throw new AssertionError("Sequences with different seeds are unexpectedly identical");
        }

        // Test that the stream produces values within the expected range [0.0, 1.0)
        for (double val : array1) {
            if (val < 0.0 || val >= 1.0) {
                throw new AssertionError("Value out of range: " + val);
            }
        }
    }
}
