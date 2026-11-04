/*
 * @test
 * @summary Verifies Random.longs(...) spliterator tryAdvance(LongConsumer) behavior: sequence equivalence, exhaustion, and null consumer handling
 * @run main RandomWitupTest
 */
import java.util.Random;
import java.util.Spliterator;
import java.util.function.LongConsumer;

public class RandomWitupTest {

    public static void main(String[] args) {
        testSequenceMatchesStream();
        testExhaustedReturnsFalseWithoutConsuming();
        testNullConsumerThrowsNPE();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testSequenceMatchesStream() {
        long[] expected = new Random(42L).longs(3, 0, 10).toArray();
        Spliterator.OfLong sp = new Random(42L).longs(3, 0, 10).spliterator();
        long[] got = new long[3];
        long[] box = new long[1];
        for (int k = 0; k < 3; k++) {
            boolean advanced = sp.tryAdvance((LongConsumer) v -> box[0] = v);
            check(advanced, "tryAdvance should return true at index " + k);
            got[k] = box[0];
        }
        for (int k = 0; k < 3; k++) {
            check(expected[k] == got[k],
                  "mismatch at " + k + ": expected " + expected[k] + " got " + got[k]);
            check(got[k] >= 0 && got[k] < 10, "value out of bounds: " + got[k]);
        }
    }

    static void testExhaustedReturnsFalseWithoutConsuming() {
        Spliterator.OfLong sp = new Random(7L).longs(2, 1, 5).spliterator();
        int[] calls = new int[1];
        LongConsumer counter = v -> calls[0]++;
        check(sp.tryAdvance(counter), "first advance should succeed");
        check(sp.tryAdvance(counter), "second advance should succeed");
        check(!sp.tryAdvance(counter), "third advance should return false");
        check(calls[0] == 2, "consumer should be called exactly 2 times, was " + calls[0]);
    }

    static void testNullConsumerThrowsNPE() {
        Spliterator.OfLong sp = new Random(1L).longs(1, 0, 3).spliterator();
        try {
            sp.tryAdvance((LongConsumer) null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
