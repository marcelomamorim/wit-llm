/*
 * @test
 * @summary Black-box checks of ThreadLocalRandom long spliterator forEachRemaining via public API
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.Spliterator;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.LongConsumer;
import java.util.stream.LongStream;

public class ThreadLocalRandomWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static Spliterator.OfLong spliterator(long size, long origin, long bound) {
        LongStream s = ThreadLocalRandom.current().longs(size, origin, bound);
        return s.spliterator();
    }

    public static void main(String[] args) {
        testNullConsumerThrowsNpe();
        testProducesAllValuesInRange();
        testZeroSizeNoCalls();
        testSecondTraversalIsEmpty();
    }

    static void testNullConsumerThrowsNpe() {
        Spliterator.OfLong sp = spliterator(3, 0L, 10L);
        boolean thrown = false;
        try {
            sp.forEachRemaining((LongConsumer) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null consumer");
    }

    static void testProducesAllValuesInRange() {
        final long origin = 5L;
        final long bound = 20L;
        Spliterator.OfLong sp = spliterator(50, origin, bound);
        final int[] count = {0};
        final boolean[] ok = {true};
        sp.forEachRemaining((LongConsumer) v -> {
            count[0]++;
            if (v < origin || v >= bound) {
                ok[0] = false;
            }
        });
        check(count[0] == 50, "expected 50 elements, got " + count[0]);
        check(ok[0], "generated value outside [origin, bound)");
    }

    static void testZeroSizeNoCalls() {
        Spliterator.OfLong sp = spliterator(0, 0L, 100L);
        final int[] count = {0};
        sp.forEachRemaining((LongConsumer) v -> count[0]++);
        check(count[0] == 0, "consumer invoked for empty stream");
    }

    static void testSecondTraversalIsEmpty() {
        Spliterator.OfLong sp = spliterator(7, 1L, 4L);
        final int[] first = {0};
        sp.forEachRemaining((LongConsumer) v -> first[0]++);
        check(first[0] == 7, "expected 7 elements on first traversal, got " + first[0]);
        final int[] second = {0};
        sp.forEachRemaining((LongConsumer) v -> second[0]++);
        check(second[0] == 0, "expected no elements on second traversal, got " + second[0]);
    }
}
