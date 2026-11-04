/*
 * @test
 * @summary Witup-derived checks for SplittableRandom int spliterator tryAdvance null-consumer handling
 * @run main SplittableRandomWitupTest
 */

import java.util.SplittableRandom;
import java.util.Spliterator;
import java.util.function.IntConsumer;

public class SplittableRandomWitupTest {

    public static void main(String[] args) {
        testNullConsumerThrowsNPE();
        testNullConsumerThrowsNPEWhenExhausted();
        testAdvancesExactlyStreamSize();
    }

    static void testNullConsumerThrowsNPE() {
        SplittableRandom rng = new SplittableRandom(42L);
        Spliterator.OfInt s = rng.ints(3L, 0, 10).spliterator();
        try {
            s.tryAdvance((IntConsumer) null);
            fail("expected NullPointerException for null consumer");
        } catch (NullPointerException expected) {
        }
    }

    static void testNullConsumerThrowsNPEWhenExhausted() {
        SplittableRandom rng = new SplittableRandom(7L);
        Spliterator.OfInt s = rng.ints(0L, 0, 10).spliterator();
        try {
            s.tryAdvance((IntConsumer) null);
            fail("expected NullPointerException for null consumer on exhausted spliterator");
        } catch (NullPointerException expected) {
        }
    }

    static void testAdvancesExactlyStreamSize() {
        SplittableRandom rng = new SplittableRandom(123L);
        Spliterator.OfInt s = rng.ints(3L, 0, 10).spliterator();
        final int[] count = {0};
        final boolean[] outOfRange = {false};
        IntConsumer c = v -> {
            if (v < 0 || v >= 10) {
                outOfRange[0] = true;
            }
            count[0]++;
        };
        for (int i = 0; i < 3; i++) {
            if (!s.tryAdvance(c)) {
                fail("tryAdvance returned false before stream was exhausted at step " + i);
            }
        }
        if (s.tryAdvance(c)) {
            fail("tryAdvance returned true after stream size was consumed");
        }
        if (count[0] != 3) {
            fail("expected 3 consumed elements but got " + count[0]);
        }
        if (outOfRange[0]) {
            fail("generated value outside [0, 10)");
        }
    }

    static void fail(String msg) {
        throw new RuntimeException(msg);
    }
}
