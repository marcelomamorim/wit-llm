/*
 * @test
 * @summary Test java.util.Random.RandomLongsSpliterator.forEachRemaining(LongConsumer) for NPE on null consumer and normal operation
 * @run main RandomWitupTest
 */

import java.util.Random;
import java.util.function.LongConsumer;

public class RandomWitupTest {
    public static void main(String[] args) {
        testForEachRemainingNullConsumer();
        testForEachRemainingNormal();
    }

    private static void testForEachRemainingNullConsumer() {
        Random random = new Random(12345L);
        Random.RandomLongsSpliterator spliterator = new Random.RandomLongsSpliterator(random, 0L, 10L, 0, 5);
        try {
            spliterator.forEachRemaining(null);
            throw new AssertionError("Expected NullPointerException for null consumer");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testForEachRemainingNormal() {
        Random random = new Random(12345L);
        final StringBuilder sb = new StringBuilder();
        Random.RandomLongsSpliterator spliterator = new Random.RandomLongsSpliterator(random, 0L, 10L, 0, 3);
        spliterator.forEachRemaining(new LongConsumer() {
            public void accept(long value) {
                sb.append(value).append(",");
            }
        });
        String result = sb.toString();
        if (result.isEmpty()) {
            throw new AssertionError("Expected some values to be consumed");
        }
        // Check that the number of values consumed matches the spliterator size (fence - index)
        String[] parts = result.split(",");
        if (parts.length != 3) {
            throw new AssertionError("Expected 3 values consumed, got " + parts.length);
        }
    }
}
