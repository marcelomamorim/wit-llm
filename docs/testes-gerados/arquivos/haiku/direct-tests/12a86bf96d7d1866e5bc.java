/*
 * @test
 * @summary Verify tryAdvance behavior of the DoubleStream spliterator created from ThreadLocalRandom.doubles(long)
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.Spliterator;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.DoubleConsumer;

public class ThreadLocalRandomWitupTest {

    public static void main(String[] args) {
        testNullConsumerThrowsNpe();
        testCountAndRange();
        testEmptyStreamReturnsFalseWithoutAccept();
    }

    static void testNullConsumerThrowsNpe() {
        Spliterator.OfDouble s = ThreadLocalRandom.current().doubles(2).spliterator();
        DoubleConsumer nullConsumer = null;
        try {
            s.tryAdvance(nullConsumer);
            throw new AssertionError("expected NullPointerException for null consumer");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testCountAndRange() {
        int n = 5;
        Spliterator.OfDouble s = ThreadLocalRandom.current().doubles(n).spliterator();
        int[] count = new int[1];
        DoubleConsumer checker = d -> {
            if (d < 0.0 || d >= 1.0) {
                throw new AssertionError("value out of range [0,1): " + d);
            }
            count[0]++;
        };
        for (int i = 0; i < n; i++) {
            if (!s.tryAdvance(checker)) {
                throw new AssertionError("tryAdvance returned false too early at " + i);
            }
        }
        if (count[0] != n) {
            throw new AssertionError("expected " + n + " accepted values but got " + count[0]);
        }
        DoubleConsumer mustNotBeCalled = d -> {
            throw new AssertionError("consumer invoked after stream exhausted");
        };
        if (s.tryAdvance(mustNotBeCalled)) {
            throw new AssertionError("tryAdvance should return false after exhaustion");
        }
    }

    static void testEmptyStreamReturnsFalseWithoutAccept() {
        Spliterator.OfDouble s = ThreadLocalRandom.current().doubles(0).spliterator();
        DoubleConsumer mustNotBeCalled = d -> {
            throw new AssertionError("consumer invoked on empty stream");
        };
        if (s.tryAdvance(mustNotBeCalled)) {
            throw new AssertionError("tryAdvance should return false for empty stream");
        }
    }
}
