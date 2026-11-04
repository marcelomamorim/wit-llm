/*
 * @test
 * @summary Checks IntStream.generate spliterator tryAdvance: value delivery, return value, supplier call count, and null action handling
 * @run main StreamSpliteratorsWitupTest
 */

import java.util.Spliterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

public class StreamSpliteratorsWitupTest {

    public static void main(String[] args) {
        AtomicInteger calls = new AtomicInteger();
        Spliterator.OfInt sp = IntStream.generate(() -> 40 + calls.incrementAndGet()).spliterator();

        int[] got = new int[1];
        boolean r1 = sp.tryAdvance((IntConsumer) v -> got[0] = v);
        if (!r1) {
            throw new RuntimeException("first tryAdvance should return true");
        }
        if (got[0] != 41) {
            throw new RuntimeException("expected 41 but got " + got[0]);
        }
        if (calls.get() != 1) {
            throw new RuntimeException("supplier should be called once, was " + calls.get());
        }

        boolean r2 = sp.tryAdvance((IntConsumer) v -> got[0] = v);
        if (!r2) {
            throw new RuntimeException("second tryAdvance should return true");
        }
        if (got[0] != 42) {
            throw new RuntimeException("expected 42 but got " + got[0]);
        }
        if (calls.get() != 2) {
            throw new RuntimeException("supplier should be called twice, was " + calls.get());
        }

        boolean npe = false;
        try {
            sp.tryAdvance((IntConsumer) null);
        } catch (NullPointerException e) {
            npe = true;
        }
        if (!npe) {
            throw new RuntimeException("null action should throw NullPointerException");
        }
        if (calls.get() != 2) {
            throw new RuntimeException("supplier must not be called when action is null, count=" + calls.get());
        }
    }
}
