/*
 * @test
 * @summary Witup-generated tests for Random longs spliterator tryAdvance
 * @run main RandomWitupTest
 */
import java.util.Random;
import java.util.Spliterator;
import java.util.function.LongConsumer;

public class RandomWitupTest {
    public static void main(String[] args) {
        testNullConsumerThrowsNPE();
        testAdvanceProducesExpectedSequence();
        System.out.println("PASS");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullConsumerThrowsNPE() {
        Spliterator.OfLong sp = new Random(42L).longs(3).spliterator();
        try {
            sp.tryAdvance((LongConsumer) null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
        }
    }

    static void testAdvanceProducesExpectedSequence() {
        long[] expected = new Random(42L).longs(3).toArray();
        Spliterator.OfLong sp = new Random(42L).longs(3).spliterator();
        long[] got = new long[3];
        int[] idx = {0};
        LongConsumer consumer = v -> got[idx[0]++] = v;
        for (int i = 0; i < 3; i++) {
            check(sp.tryAdvance(consumer), "tryAdvance should return true at index " + i);
        }
        check(!sp.tryAdvance(consumer), "tryAdvance should return false after exhaustion");
        check(idx[0] == 3, "consumer should be invoked exactly 3 times");
        for (int i = 0; i < 3; i++) {
            check(got[i] == expected[i], "value mismatch at index " + i);
        }
    }
}
