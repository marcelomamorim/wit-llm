/*
 * @test
 * @summary WITUP-derived checks for StreamSpliterators tryAdvance via public Stream.generate spliterator
 * @run main StreamSpliteratorsWitupTest
 */

import java.util.Spliterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class StreamSpliteratorsWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testTryAdvanceDeliversSuppliedValue();
    }

    static void testNullActionThrowsNPE() {
        Stream<String> s = Stream.generate(() -> "x");
        Spliterator<String> sp = s.spliterator();
        try {
            sp.tryAdvance(null);
            throw new AssertionError("expected NullPointerException");
        } catch (NullPointerException expected) {
        }
    }

    static void testTryAdvanceDeliversSuppliedValue() {
        AtomicInteger counter = new AtomicInteger();
        Stream<Integer> s = Stream.generate(counter::incrementAndGet);
        Spliterator<Integer> sp = s.spliterator();
        int[] got = new int[1];

        boolean first = sp.tryAdvance(v -> got[0] = v);
        if (!first) {
            throw new AssertionError("tryAdvance should return true for infinite supplier");
        }
        if (got[0] != 1) {
            throw new AssertionError("expected 1 but got " + got[0]);
        }

        boolean second = sp.tryAdvance(v -> got[0] = v);
        if (!second) {
            throw new AssertionError("tryAdvance should return true on second call");
        }
        if (got[0] != 2) {
            throw new AssertionError("expected 2 but got " + got[0]);
        }
    }
}
