/*
 * @test
 * @summary Verify Spliterators empty spliterators' tryAdvance returns false without invoking the consumer and rejects a null consumer with NullPointerException
 * @run main SpliteratorsWitupTest
 */

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;

public class SpliteratorsWitupTest {

    public static void main(String[] args) {
        testEmptyRefTryAdvance();
        testEmptyIntTryAdvance();
        testEmptyLongTryAdvance();
        testEmptyDoubleTryAdvance();
    }

    static void testEmptyRefTryAdvance() {
        Spliterator<Object> s = Spliterators.emptySpliterator();
        final boolean[] called = {false};
        Consumer<Object> c = x -> called[0] = true;
        if (s.tryAdvance(c)) {
            throw new RuntimeException("tryAdvance returned true on empty spliterator");
        }
        if (called[0]) {
            throw new RuntimeException("consumer was invoked on empty spliterator");
        }
        if (s.tryAdvance(c)) {
            throw new RuntimeException("second tryAdvance returned true");
        }
        try {
            s.tryAdvance((Consumer<Object>) null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null consumer (ref)");
    }

    static void testEmptyIntTryAdvance() {
        Spliterator.OfInt s = Spliterators.emptyIntSpliterator();
        final boolean[] called = {false};
        IntConsumer c = x -> called[0] = true;
        if (s.tryAdvance(c)) {
            throw new RuntimeException("tryAdvance returned true on empty int spliterator");
        }
        if (called[0]) {
            throw new RuntimeException("int consumer was invoked on empty spliterator");
        }
        try {
            s.tryAdvance((IntConsumer) null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null consumer (int)");
    }

    static void testEmptyLongTryAdvance() {
        Spliterator.OfLong s = Spliterators.emptyLongSpliterator();
        final boolean[] called = {false};
        LongConsumer c = x -> called[0] = true;
        if (s.tryAdvance(c)) {
            throw new RuntimeException("tryAdvance returned true on empty long spliterator");
        }
        if (called[0]) {
            throw new RuntimeException("long consumer was invoked on empty spliterator");
        }
        try {
            s.tryAdvance((LongConsumer) null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null consumer (long)");
    }

    static void testEmptyDoubleTryAdvance() {
        Spliterator.OfDouble s = Spliterators.emptyDoubleSpliterator();
        final boolean[] called = {false};
        DoubleConsumer c = x -> called[0] = true;
        if (s.tryAdvance(c)) {
            throw new RuntimeException("tryAdvance returned true on empty double spliterator");
        }
        if (called[0]) {
            throw new RuntimeException("double consumer was invoked on empty spliterator");
        }
        try {
            s.tryAdvance((DoubleConsumer) null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null consumer (double)");
    }
}
