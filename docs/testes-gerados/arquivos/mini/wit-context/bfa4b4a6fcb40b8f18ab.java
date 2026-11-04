/*
 * @test
 * @summary Test ThreadLocalRandom.forEachRemaining(DoubleConsumer) for null consumer and normal operation
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.DoubleConsumer;

public class ThreadLocalRandomWitupTest {
    public static void main(String[] args) {
        testForEachRemainingNullConsumer();
        testForEachRemainingNormal();
    }

    static void testForEachRemainingNullConsumer() {
        ThreadLocalRandom.RandomDoublesSpliterator spliterator = new ThreadLocalRandom.RandomDoublesSpliterator(0L, 5L, 0.0, 1.0);
        try {
            spliterator.forEachRemaining(null);
            throw new Error("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testForEachRemainingNormal() {
        final StringBuilder sb = new StringBuilder();
        DoubleConsumer consumer = new DoubleConsumer() {
            public void accept(double d) {
                sb.append(d).append(",");
            }
        };

        ThreadLocalRandom.RandomDoublesSpliterator spliterator = new ThreadLocalRandom.RandomDoublesSpliterator(0L, 3L, 0.0, 1.0);
        spliterator.forEachRemaining(consumer);

        String result = sb.toString();
        if (result.isEmpty()) {
            throw new Error("Expected some doubles to be consumed, but got empty output");
        }
        // Check that the output contains at least 3 doubles separated by commas
        int count = 0;
        for (int i = 0; i < result.length(); i++) {
            if (result.charAt(i) == ',') count++;
        }
        if (count != 3) {
            throw new Error("Expected 3 doubles consumed, but got " + count);
        }
    }
}
