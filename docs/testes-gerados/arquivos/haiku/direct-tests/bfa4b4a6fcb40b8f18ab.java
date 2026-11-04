/*
 * @test
 * @summary ThreadLocalRandom spliterator forEachRemaining(DoubleConsumer) behavior
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.Spliterator;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.DoubleConsumer;

public class ThreadLocalRandomWitupTest {
    public static void main(String[] args) {
        ThreadLocalRandom rng = ThreadLocalRandom.current();

        Spliterator.OfDouble sp = rng.doubles(5L, 0.0, 1.0).spliterator();
        final int[] count = {0};
        sp.forEachRemaining((DoubleConsumer) d -> {
            if (d < 0.0 || d >= 1.0) {
                throw new AssertionError("value out of range: " + d);
            }
            count[0]++;
        });
        if (count[0] != 5) {
            throw new AssertionError("expected 5 elements, got " + count[0]);
        }

        final int[] extra = {0};
        sp.forEachRemaining((DoubleConsumer) d -> extra[0]++);
        if (extra[0] != 0) {
            throw new AssertionError("expected no elements after exhaustion, got " + extra[0]);
        }

        Spliterator.OfDouble sp2 = rng.doubles(3L, 2.0, 3.0).spliterator();
        try {
            sp2.forEachRemaining((DoubleConsumer) null);
            throw new AssertionError("expected NullPointerException");
        } catch (NullPointerException expected) {
        }
    }
}
