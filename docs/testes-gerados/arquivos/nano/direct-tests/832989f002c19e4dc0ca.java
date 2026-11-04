/*
 * @test
 * @summary Test for Spliterators.forEachRemaining(LongConsumer)
 * @run main SpliteratorsWitupTest
 */

import java.util.Spliterators;
import java.util.function.LongConsumer;

public class SpliteratorsWitupTest {

    public static void main(String[] args) {
        testForEachRemainingCallsAction();
        testForEachRemainingNullActionThrows();
    }

    static void testForEachRemainingCallsAction() {
        final boolean[] called = {false};
        LongConsumer consumer = new LongConsumer() {
            @Override
            public void accept(long value) {
                called[0] = true;
            }
        };
        // Create a dummy iterator with forEachRemaining method
        java.util.Iterator<Long> iterator = new java.util.Iterator<Long>() {
            private boolean done = false;
            @Override
            public boolean hasNext() {
                return !done;
            }
            @Override
            public Long next() {
                done = true;
                return 1L;
            }
        };
        // Wrap iterator into a Spliterator
        java.util.Spliterator.OfLong spliterator = java.util.Spliterators.spliteratorUnknownSize(iterator, 0);
        // Call forEachRemaining
        spliterator.forEachRemaining(consumer);
        // Verify that consumer was called
        if (!called[0]) {
            throw new AssertionError("Consumer was not called by forEachRemaining");
        }
    }

    static void testForEachRemainingNullActionThrows() {
        java.util.Spliterator.OfLong spliterator = java.util.Spliterators.emptyLongSpliterator();
        boolean threw = false;
        try {
            spliterator.forEachRemaining(null);
        } catch (NullPointerException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected NullPointerException when passing null to forEachRemaining");
        }
    }
}
