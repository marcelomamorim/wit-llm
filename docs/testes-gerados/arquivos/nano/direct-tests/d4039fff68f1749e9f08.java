/* @test    @summary Test for SplittableRandom.forEachRemaining(DoubleConsumer) method    @run main SplittableRandomWitupTest */

import java.util.SplittableRandom;
import java.util.function.DoubleConsumer;

public class SplittableRandomWitupTest {

    public static void main(String[] args) {
        testForEachRemainingWithValidConsumer();
        testForEachRemainingWithNullConsumer();
    }

    static void testForEachRemainingWithValidConsumer() {
        SplittableRandom sr = new SplittableRandom(12345L);
        double[] results = new double[5];
        DoubleConsumer consumer = new DoubleConsumer() {
            int count = 0;
            public void accept(double value) {
                if (count < results.length) {
                    results[count] = value;
                    count++;
                }
            }
        };
        sr.forEachRemaining(consumer);
        boolean allResultsSet = true;
        for (double d : results) {
            if (Double.isNaN(d)) {
                allResultsSet = false;
                break;
            }
        }
        if (!allResultsSet) {
            throw new AssertionError("Results array contains NaN, forEachRemaining may not have executed properly.");
        }
    }

    static void testForEachRemainingWithNullConsumer() {
        SplittableRandom sr = new SplittableRandom(54321L);
        boolean exceptionThrown = false;
        try {
            sr.forEachRemaining(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when passing null consumer.");
        }
    }
}
