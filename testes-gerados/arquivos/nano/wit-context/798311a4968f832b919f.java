/*
 * @test
 * @summary Test for Spliterators.tryAdvance(LongConsumer)
 * @run main SpliteratorsWitupTest
 */

import java.util.Spliterators;
import java.util.function.LongConsumer;

public class SpliteratorsWitupTest {

    public static void main(String[] args) {
        testTryAdvanceWithNullAction();
        testTryAdvanceWithValidAction();
        testTryAdvanceWithEmptyArray();
    }

    private static void testTryAdvanceWithNullAction() {
        Spliterators.OfLong spliterator = new Spliterators.OfLong();
        LongConsumer consumer = null;
        boolean threwException = false;
        try {
            spliterator.tryAdvance(consumer);
        } catch (NullPointerException e) {
            threwException = true;
        }
        if (!threwException) {
            throw new AssertionError("Expected NullPointerException when action is null");
        }
    }

    private static void testTryAdvanceWithValidAction() {
        long[] array = {1L, 2L, 3L};
        Spliterators.OfLong spliterator = new Spliterators.OfLong(array, 0, array.length);
        final long[] received = {0L};
        LongConsumer consumer = new LongConsumer() {
            @Override
            public void accept(long value) {
                received[0] = value;
            }
        };
        boolean result = spliterator.tryAdvance(consumer);
        if (!result) {
            throw new AssertionError("Expected tryAdvance to return true with valid array");
        }
        if (received[0] != array[0]) {
            throw new AssertionError("Consumer did not receive the correct value");
        }
    }

    private static void testTryAdvanceWithEmptyArray() {
        long[] emptyArray = {};
        Spliterators.OfLong spliterator = new Spliterators.OfLong(emptyArray, 0, 0);
        final boolean[] called = {false};
        LongConsumer consumer = new LongConsumer() {
            @Override
            public void accept(long value) {
                called[0] = true;
            }
        };
        boolean result = spliterator.tryAdvance(consumer);
        if (result) {
            throw new AssertionError("Expected tryAdvance to return false for empty array");
        }
        if (called[0]) {
            throw new AssertionError("Consumer should not be called for empty array");
        }
    }
}
