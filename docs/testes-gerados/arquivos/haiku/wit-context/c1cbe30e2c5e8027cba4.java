/*
 * @test
 * @summary WITUP-derived tests for SplittableRandom ints spliterator forEachRemaining
 * @run main SplittableRandomWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.SplittableRandom;
import java.util.function.IntConsumer;

public class SplittableRandomWitupTest {

    public static void main(String[] args) {
        testNullConsumerThrowsNPE();
        testEmptyStreamDoesNotInvokeConsumer();
        testConsumesAllElementsWithinBounds();
        testDeterministicSequenceForSameSeed();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullConsumerThrowsNPE() {
        boolean thrown = false;
        try {
            new SplittableRandom(1L).ints(5L, 0, 10).spliterator()
                    .forEachRemaining((IntConsumer) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null consumer");
    }

    static void testEmptyStreamDoesNotInvokeConsumer() {
        final int[] calls = {0};
        new SplittableRandom(2L).ints(0L, 0, 10).spliterator()
                .forEachRemaining((IntConsumer) v -> calls[0]++);
        check(calls[0] == 0, "consumer must not be invoked for empty stream, calls=" + calls[0]);
    }

    static void testConsumesAllElementsWithinBounds() {
        final int size = 100;
        final int origin = -5;
        final int bound = 5;
        final int[] count = {0};
        final boolean[] inRange = {true};
        new SplittableRandom(3L).ints(size, origin, bound).spliterator()
                .forEachRemaining((IntConsumer) v -> {
                    count[0]++;
                    if (v < origin || v >= bound) {
                        inRange[0] = false;
                    }
                });
        check(count[0] == size, "expected " + size + " elements, got " + count[0]);
        check(inRange[0], "generated value outside [origin, bound)");
    }

    static void testDeterministicSequenceForSameSeed() {
        List<Integer> first = collect(42L, 20, 0, 1000);
        List<Integer> second = collect(42L, 20, 0, 1000);
        check(first.size() == 20, "unexpected size " + first.size());
        check(first.equals(second), "same seed must produce same sequence");
    }

    static List<Integer> collect(long seed, long size, int origin, int bound) {
        final List<Integer> values = new ArrayList<>();
        new SplittableRandom(seed).ints(size, origin, bound).spliterator()
                .forEachRemaining((IntConsumer) v -> values.add(v));
        return values;
    }
}
