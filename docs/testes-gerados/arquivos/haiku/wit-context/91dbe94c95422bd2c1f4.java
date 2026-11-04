/*
 * @test
 * @summary Verify ThreadLocalRandom int stream spliterator forEachRemaining null check, element count and bounds
 * @run main ThreadLocalRandomWitupTest
 */
import java.util.Spliterator;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.IntConsumer;

public class ThreadLocalRandomWitupTest {
    public static void main(String[] args) {
        testNullConsumerThrowsNPE();
        testConsumerInvokedExactlyStreamSizeTimes();
        testValuesWithinBounds();
        testZeroSizeNoInvocation();
    }

    static void testNullConsumerThrowsNPE() {
        Spliterator.OfInt s = ThreadLocalRandom.current().ints(5, 0, 10).spliterator();
        try {
            s.forEachRemaining((IntConsumer) null);
        } catch (NullPointerException e) {
            return;
        }
        throw new AssertionError("expected NullPointerException for null consumer");
    }

    static void testConsumerInvokedExactlyStreamSizeTimes() {
        long size = 1000L;
        Spliterator.OfInt s = ThreadLocalRandom.current().ints(size, 0, 100).spliterator();
        final long[] count = {0L};
        IntConsumer c = v -> count[0]++;
        s.forEachRemaining(c);
        if (count[0] != size) {
            throw new AssertionError("expected " + size + " invocations but was " + count[0]);
        }
    }

    static void testValuesWithinBounds() {
        int origin = 3;
        int bound = 7;
        Spliterator.OfInt s = ThreadLocalRandom.current().ints(500L, origin, bound).spliterator();
        final int[] bad = {0};
        IntConsumer c = v -> {
            if (v < origin || v >= bound) {
                bad[0]++;
            }
        };
        s.forEachRemaining(c);
        if (bad[0] != 0) {
            throw new AssertionError("values out of bounds: " + bad[0]);
        }
    }

    static void testZeroSizeNoInvocation() {
        Spliterator.OfInt s = ThreadLocalRandom.current().ints(0L, 0, 10).spliterator();
        final long[] count = {0L};
        IntConsumer c = v -> count[0]++;
        s.forEachRemaining(c);
        if (count[0] != 0L) {
            throw new AssertionError("expected no invocations for size 0 but was " + count[0]);
        }
    }
}
