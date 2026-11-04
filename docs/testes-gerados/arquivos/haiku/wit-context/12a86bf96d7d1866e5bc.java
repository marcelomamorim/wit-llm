/*
 * @test
 * @summary WITUP-derived checks for ThreadLocalRandom double spliterator tryAdvance
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.Spliterator;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.DoubleConsumer;

public class ThreadLocalRandomWitupTest {

    public static void main(String[] args) {
        testNullConsumerThrowsNullPointerException();
        testTryAdvanceConsumesExactlyStreamSizeElements();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullConsumerThrowsNullPointerException() {
        Spliterator.OfDouble s = ThreadLocalRandom.current().doubles(3).spliterator();
        boolean thrown = false;
        try {
            s.tryAdvance((DoubleConsumer) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "tryAdvance(null) must throw NullPointerException");
    }

    static void testTryAdvanceConsumesExactlyStreamSizeElements() {
        Spliterator.OfDouble s = ThreadLocalRandom.current().doubles(3).spliterator();
        final int[] count = new int[1];
        DoubleConsumer c = d -> {
            check(d >= 0.0 && d < 1.0, "generated double out of [0,1): " + d);
            count[0]++;
        };
        int advances = 0;
        while (s.tryAdvance(c)) {
            advances++;
            if (advances > 3) {
                throw new RuntimeException("tryAdvance returned true more than size times");
            }
        }
        check(advances == 3, "expected 3 successful tryAdvance calls, got " + advances);
        check(count[0] == 3, "expected consumer invoked 3 times, got " + count[0]);
        check(!s.tryAdvance(c), "tryAdvance must return false after exhaustion");
        check(count[0] == 3, "consumer must not be invoked after exhaustion");
    }
}
