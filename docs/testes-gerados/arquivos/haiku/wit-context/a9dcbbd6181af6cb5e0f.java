/*
 * @test
 * @summary Tests SplittableRandom longs spliterator forEachRemaining via public LongStream API
 * @run main SplittableRandomWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;
import java.util.SplittableRandom;
import java.util.function.LongConsumer;
import java.util.stream.LongStream;

public class SplittableRandomWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static List<Long> drain(long seed, long size, long origin, long bound) {
        SplittableRandom rng = new SplittableRandom(seed);
        Spliterator.OfLong sp = rng.longs(size, origin, bound).spliterator();
        List<Long> values = new ArrayList<>();
        LongConsumer c = values::add;
        sp.forEachRemaining(c);
        return values;
    }

    static void testNullConsumerThrowsNpe() {
        SplittableRandom rng = new SplittableRandom(42L);
        Spliterator.OfLong sp = rng.longs(3).spliterator();
        boolean thrown = false;
        try {
            sp.forEachRemaining((LongConsumer) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null consumer");
    }

    static void testConsumesExactlySizeElements() {
        List<Long> values = drain(7L, 5L, 0L, 0L + 1000L);
        check(values.size() == 5, "expected 5 elements but got " + values.size());
    }

    static void testBoundedValuesWithinRange() {
        List<Long> values = drain(123L, 200L, 10L, 20L);
        check(values.size() == 200, "expected 200 elements");
        for (Long v : values) {
            check(v >= 10L && v < 20L, "value out of range: " + v);
        }
    }

    static void testDeterministicForSameSeed() {
        List<Long> a = drain(99L, 50L, -5L, 5L);
        List<Long> b = drain(99L, 50L, -5L, 5L);
        check(a.equals(b), "same seed must yield same sequence");
    }

    static void testNothingRemainsAfterForEachRemaining() {
        SplittableRandom rng = new SplittableRandom(1L);
        Spliterator.OfLong sp = rng.longs(4L, 0L, 100L).spliterator();
        final int[] count = new int[1];
        sp.forEachRemaining((LongConsumer) v -> count[0]++);
        check(count[0] == 4, "expected 4 consumed but got " + count[0]);
        boolean advanced = sp.tryAdvance((LongConsumer) v -> { });
        check(!advanced, "no elements should remain after forEachRemaining");
    }

    static void testZeroSizeConsumesNothing() {
        List<Long> values = drain(5L, 0L, 0L, 10L);
        check(values.isEmpty(), "zero-size stream must produce no elements");
    }

    public static void main(String[] args) {
        testNullConsumerThrowsNpe();
        testConsumesExactlySizeElements();
        testBoundedValuesWithinRange();
        testDeterministicForSameSeed();
        testNothingRemainsAfterForEachRemaining();
        testZeroSizeConsumesNothing();
    }
}
