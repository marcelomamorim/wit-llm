/*
 * @test
 * @summary WITUP-derived checks for SplittableRandom.longs spliterator tryAdvance
 * @run main SplittableRandomWitupTest
 */

import java.util.Spliterator;
import java.util.SplittableRandom;
import java.util.function.LongConsumer;
import java.util.stream.LongStream;

public class SplittableRandomWitupTest {

    public static void main(String[] args) {
        testNullConsumerThrowsNullPointerException();
        testEmptySpliteratorReturnsFalse();
        testConsumesExactlyCountValues();
    }

    static Spliterator.OfLong newSpliterator(long count) {
        LongStream stream = new SplittableRandom(42L).longs(count);
        return stream.spliterator();
    }

    static void testNullConsumerThrowsNullPointerException() {
        Spliterator.OfLong s = newSpliterator(3L);
        boolean thrown = false;
        try {
            s.tryAdvance((LongConsumer) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException();
        }
    }

    static void testEmptySpliteratorReturnsFalse() {
        Spliterator.OfLong s = newSpliterator(0L);
        LongConsumer c = v -> { };
        if (s.tryAdvance(c)) {
            throw new RuntimeException();
        }
    }

    static void testConsumesExactlyCountValues() {
        Spliterator.OfLong s = newSpliterator(3L);
        final int[] calls = new int[1];
        LongConsumer c = v -> calls[0]++;
        for (int i = 0; i < 3; i++) {
            if (!s.tryAdvance(c)) {
                throw new RuntimeException();
            }
        }
        if (s.tryAdvance(c)) {
            throw new RuntimeException();
        }
        if (calls[0] != 3) {
            throw new RuntimeException();
        }
    }
}
