/*
 * @test
 * @summary WITUP-derived checks for ThreadLocalRandom long spliterator forEachRemaining
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.Spliterator;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.LongConsumer;
import java.util.stream.LongStream;

public class ThreadLocalRandomWitupTest {

    public static void main(String[] args) {
        testNullConsumerThrowsNPE();
        testForEachRemainingCountAndBounds();
        testExhaustedAfterForEachRemaining();
    }

    static void fail(String message) {
        throw new RuntimeException(message);
    }

    static Spliterator.OfLong newSpliterator(long size, long origin, long bound) {
        LongStream stream = ThreadLocalRandom.current().longs(size, origin, bound);
        return stream.spliterator();
    }

    static void testNullConsumerThrowsNPE() {
        Spliterator.OfLong spl = newSpliterator(10, 5, 10);
        boolean thrown = false;
        try {
            spl.forEachRemaining((LongConsumer) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            fail("expected NullPointerException for null consumer");
        }
    }

    static void testForEachRemainingCountAndBounds() {
        final long origin = 5;
        final long bound = 10;
        final long size = 100;
        Spliterator.OfLong spl = newSpliterator(size, origin, bound);
        final int[] count = new int[1];
        spl.forEachRemaining((LongConsumer) v -> {
            if (v < origin || v >= bound) {
                fail("value out of bounds: " + v);
            }
            count[0]++;
        });
        if (count[0] != size) {
            fail("expected " + size + " elements but got " + count[0]);
        }
    }

    static void testExhaustedAfterForEachRemaining() {
        Spliterator.OfLong spl = newSpliterator(20, 0, 3);
        spl.forEachRemaining((LongConsumer) v -> { });
        if (spl.tryAdvance((LongConsumer) v -> { })) {
            fail("tryAdvance should return false after forEachRemaining");
        }
        final int[] count = new int[1];
        spl.forEachRemaining((LongConsumer) v -> count[0]++);
        if (count[0] != 0) {
            fail("second forEachRemaining should not visit elements, got " + count[0]);
        }
    }
}
