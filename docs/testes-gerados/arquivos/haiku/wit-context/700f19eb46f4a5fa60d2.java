/*
 * @test
 * @summary WITUP-derived checks for ThreadLocalRandom long stream spliterator tryAdvance
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.Spliterator;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.LongConsumer;
import java.util.stream.LongStream;

public class ThreadLocalRandomWitupTest {
    public static void main(String[] args) {
        testNullConsumerThrowsNullPointerException();
        testTryAdvanceConsumesExactlyFenceElements();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testNullConsumerThrowsNullPointerException() {
        Spliterator.OfLong spliterator = ThreadLocalRandom.current().longs(2).spliterator();
        try {
            spliterator.tryAdvance((LongConsumer) null);
            throw new AssertionError("expected NullPointerException for null consumer");
        } catch (NullPointerException expected) {
        }
    }

    static void testTryAdvanceConsumesExactlyFenceElements() {
        LongStream stream = ThreadLocalRandom.current().longs(3);
        Spliterator.OfLong spliterator = stream.spliterator();
        int[] count = new int[1];
        LongConsumer consumer = value -> count[0]++;
        for (int i = 0; i < 3; i++) {
            check(spliterator.tryAdvance(consumer), "tryAdvance should return true before fence");
        }
        check(!spliterator.tryAdvance(consumer), "tryAdvance should return false at fence");
        check(count[0] == 3, "consumer should be invoked exactly 3 times, was " + count[0]);
    }
}
