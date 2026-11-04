/*
 * @test
 * @summary Tests SplittableRandom spliterator forEachRemaining(IntConsumer) for null handling, sequence equivalence, empty source and exhaustion
 * @run main SplittableRandomWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;
import java.util.SplittableRandom;
import java.util.function.IntConsumer;

public class SplittableRandomWitupTest {

    public static void main(String[] args) {
        testNullConsumerThrowsNPE();
        testMatchesSequenceFromSameSeed();
        testEmptySourceMakesNoCalls();
        testExhaustedAfterForEachRemaining();
    }

    static Spliterator.OfInt spliterator(long seed, long size) {
        return new SplittableRandom(seed).ints(size, 0, 100).spliterator();
    }

    static void testNullConsumerThrowsNPE() {
        Spliterator.OfInt sp = spliterator(1L, 5L);
        boolean thrown = false;
        try {
            sp.forEachRemaining((IntConsumer) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("expected NullPointerException for null consumer");
        }
    }

    static void testMatchesSequenceFromSameSeed() {
        int[] expected = new SplittableRandom(42L).ints(5, 0, 100).toArray();
        List<Integer> actual = new ArrayList<>();
        spliterator(42L, 5L).forEachRemaining((IntConsumer) v -> actual.add(v));
        if (actual.size() != expected.length) {
            throw new AssertionError("size mismatch: " + actual.size() + " vs " + expected.length);
        }
        for (int i = 0; i < expected.length; i++) {
            if (actual.get(i) != expected[i]) {
                throw new AssertionError("mismatch at " + i + ": " + actual.get(i) + " vs " + expected[i]);
            }
        }
    }

    static void testEmptySourceMakesNoCalls() {
        int[] count = {0};
        spliterator(3L, 0L).forEachRemaining((IntConsumer) v -> count[0]++);
        if (count[0] != 0) {
            throw new AssertionError("expected no calls, got " + count[0]);
        }
    }

    static void testExhaustedAfterForEachRemaining() {
        Spliterator.OfInt sp = spliterator(7L, 3L);
        int[] count = {0};
        sp.forEachRemaining((IntConsumer) v -> count[0]++);
        if (count[0] != 3) {
            throw new AssertionError("expected 3 elements, got " + count[0]);
        }
        if (sp.tryAdvance((IntConsumer) v -> { })) {
            throw new AssertionError("tryAdvance should return false after exhaustion");
        }
        int[] again = {0};
        sp.forEachRemaining((IntConsumer) v -> again[0]++);
        if (again[0] != 0) {
            throw new AssertionError("second forEachRemaining should make no calls, got " + again[0]);
        }
    }
}
