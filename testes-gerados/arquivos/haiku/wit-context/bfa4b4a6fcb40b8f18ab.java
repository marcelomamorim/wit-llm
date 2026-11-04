/*
 * @test
 * @summary WITUP-derived checks for ThreadLocalRandom.RandomDoublesSpliterator.forEachRemaining(DoubleConsumer) through public API
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.Spliterator;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.DoubleConsumer;

public class ThreadLocalRandomWitupTest {

    public static void main(String[] args) {
        testNullConsumerThrowsNullPointerException();
        testForEachRemainingProducesExpectedCountAndBounds();
        testSpliteratorIsExhaustedAfterForEachRemaining();
        testZeroSizeStreamInvokesConsumerNeverAndDoesNotThrow();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testNullConsumerThrowsNullPointerException() {
        Spliterator.OfDouble sp = ThreadLocalRandom.current().doubles(5).spliterator();
        try {
            sp.forEachRemaining((DoubleConsumer) null);
            throw new AssertionError("expected NullPointerException for null consumer");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testForEachRemainingProducesExpectedCountAndBounds() {
        final int size = 7;
        Spliterator.OfDouble sp = ThreadLocalRandom.current().doubles(size).spliterator();
        final int[] count = new int[1];
        DoubleConsumer consumer = v -> {
            check(!Double.isNaN(v), "value must not be NaN");
            check(v >= 0.0 && v < Double.MAX_VALUE, "value out of bounds: " + v);
            count[0]++;
        };
        sp.forEachRemaining(consumer);
        check(count[0] == size, "expected " + size + " elements but got " + count[0]);
    }

    static void testSpliteratorIsExhaustedAfterForEachRemaining() {
        Spliterator.OfDouble sp = ThreadLocalRandom.current().doubles(3).spliterator();
        DoubleConsumer noop = v -> { };
        sp.forEachRemaining(noop);
        check(!sp.tryAdvance(noop), "spliterator should be exhausted after forEachRemaining");
    }

    static void testZeroSizeStreamInvokesConsumerNeverAndDoesNotThrow() {
        Spliterator.OfDouble sp = ThreadLocalRandom.current().doubles(0).spliterator();
        final int[] count = new int[1];
        DoubleConsumer consumer = v -> count[0]++;
        sp.forEachRemaining(consumer);
        check(count[0] == 0, "consumer should not be invoked for empty stream");
    }
}
