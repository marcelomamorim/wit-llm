/*
 * @test
 * @summary Verifies ThreadLocalRandom int spliterator tryAdvance: null consumer, bounded values, element count and exhaustion
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.Spliterator;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.IntConsumer;

public class ThreadLocalRandomWitupTest {

    public static void main(String[] args) {
        testNullConsumerThrowsNpe();
        testBoundedValuesAndCount();
        testEmptyStreamReturnsFalseWithoutCallingConsumer();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullConsumerThrowsNpe() {
        Spliterator.OfInt sp = ThreadLocalRandom.current().ints(1, 0, 10).spliterator();
        boolean thrown = false;
        try {
            sp.tryAdvance((IntConsumer) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null consumer");
    }

    static void testBoundedValuesAndCount() {
        Spliterator.OfInt sp = ThreadLocalRandom.current().ints(3, 5, 10).spliterator();
        int[] count = {0};
        while (sp.tryAdvance((IntConsumer) v -> {
            check(v >= 5 && v < 10, "value out of bounds: " + v);
            count[0]++;
        })) {
        }
        check(count[0] == 3, "expected 3 elements, got " + count[0]);
        boolean again = sp.tryAdvance((IntConsumer) v -> {
            throw new RuntimeException("consumer must not be called after exhaustion");
        });
        check(!again, "tryAdvance should return false after exhaustion");
    }

    static void testEmptyStreamReturnsFalseWithoutCallingConsumer() {
        Spliterator.OfInt sp = ThreadLocalRandom.current().ints(0, 0, 1).spliterator();
        boolean[] called = {false};
        boolean result = sp.tryAdvance((IntConsumer) v -> called[0] = true);
        check(!result, "tryAdvance on empty spliterator should return false");
        check(!called[0], "consumer must not be called for empty spliterator");
    }
}
