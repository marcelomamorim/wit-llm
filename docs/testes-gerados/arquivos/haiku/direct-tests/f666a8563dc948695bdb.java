/*
 * @test
 * @summary Tests IntStream spliterator tryAdvance behavior of SplittableRandom via public API
 * @run main SplittableRandomWitupTest
 */

import java.util.Spliterator;
import java.util.function.IntConsumer;

public class SplittableRandomWitupTest {

    public static void main(String[] args) {
        testSequenceMatchesNextInt();
        testExhaustedReturnsFalseWithoutConsuming();
        testNullConsumerThrowsNPE();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testSequenceMatchesNextInt() {
        SplittableRandom src = new SplittableRandom(42L);
        Spliterator.OfInt s = src.ints(5, 0, 100).spliterator();
        SplittableRandom ref = new SplittableRandom(42L);
        for (int k = 0; k < 5; k++) {
            int[] box = {-1};
            boolean r = s.tryAdvance((IntConsumer) v -> box[0] = v);
            check(r, "tryAdvance should return true at index " + k);
            int expected = ref.nextInt(0, 100);
            check(box[0] == expected,
                  "value mismatch at " + k + ": " + box[0] + " != " + expected);
        }
    }

    static void testExhaustedReturnsFalseWithoutConsuming() {
        Spliterator.OfInt s = new SplittableRandom(7L).ints(2, 0, 10).spliterator();
        int[] calls = {0};
        check(s.tryAdvance((IntConsumer) v -> calls[0]++), "first advance should be true");
        check(s.tryAdvance((IntConsumer) v -> calls[0]++), "second advance should be true");
        check(!s.tryAdvance((IntConsumer) v -> calls[0]++), "exhausted advance should be false");
        check(calls[0] == 2, "consumer must not run after exhaustion, calls=" + calls[0]);
    }

    static void testNullConsumerThrowsNPE() {
        Spliterator.OfInt s = new SplittableRandom(1L).ints(1, 0, 10).spliterator();
        boolean thrown = false;
        try {
            s.tryAdvance(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null consumer should throw NullPointerException");
    }
}
