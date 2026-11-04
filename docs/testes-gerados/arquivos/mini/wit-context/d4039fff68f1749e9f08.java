/*
 * @test
 * @summary Test SplittableRandom.forEachRemaining(DoubleConsumer) for NPE and normal operation
 * @run main SplittableRandomWitupTest
 */

import java.util.SplittableRandom;
import java.util.function.DoubleConsumer;

public class SplittableRandomWitupTest {
    public static void main(String[] args) {
        testForEachRemainingNullConsumer();
        testForEachRemainingNormal();
    }

    private static void testForEachRemainingNullConsumer() {
        SplittableRandom.RandomDoublesSpliterator spliterator = new SplittableRandom(123L).new RandomDoublesSpliterator(0, 5, 0.0, 1.0);
        try {
            spliterator.forEachRemaining(null);
            throw new AssertionError("Expected NullPointerException for null consumer");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testForEachRemainingNormal() {
        final StringBuilder sb = new StringBuilder();
        DoubleConsumer consumer = new DoubleConsumer() {
            public void accept(double d) {
                sb.append(d).append(",");
            }
        };

        SplittableRandom.RandomDoublesSpliterator spliterator = new SplittableRandom(123L).new RandomDoublesSpliterator(0, 3, 0.0, 1.0);
        spliterator.forEachRemaining(consumer);

        String result = sb.toString();
        if (result.isEmpty()) {
            throw new AssertionError("Expected some doubles to be consumed, but got empty output");
        }
        // Check that the output contains 3 doubles separated by commas
        String[] parts = result.split(",");
        if (parts.length != 3) {
            throw new AssertionError("Expected 3 doubles consumed, but got " + parts.length);
        }
        for (String part : parts) {
            if (part.isEmpty()) {
                throw new AssertionError("Empty double value in output");
            }
            try {
                Double.parseDouble(part);
            } catch (NumberFormatException e) {
                throw new AssertionError("Invalid double value: " + part);
            }
        }
    }
}