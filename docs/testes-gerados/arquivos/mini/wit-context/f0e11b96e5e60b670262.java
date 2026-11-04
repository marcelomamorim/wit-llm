/*
 * @test
 * @summary Test Spliterators.forEachRemaining(C consumer) for NPE on null consumer
 * @run main SpliteratorsWitupTest
 */

import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;

public class SpliteratorsWitupTest {

    public static void main(String[] args) {
        testForEachRemainingNullConsumer();
    }

    private static void testForEachRemainingNullConsumer() {
        Spliterator<Object> empty = Spliterators.emptySpliterator();
        try {
            empty.forEachRemaining(null);
            throw new AssertionError("Expected NullPointerException for null consumer");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }
}
