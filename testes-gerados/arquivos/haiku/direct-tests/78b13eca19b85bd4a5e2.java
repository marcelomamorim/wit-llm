/*
 * @test
 * @summary Verifies Streams.RangeIntSpliterator.forEachRemaining through public IntStream.range/rangeClosed spliterators
 * @run main StreamsWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Spliterator;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

public class StreamsWitupTest {

    public static void main(String[] args) {
        testRangeHalfOpen();
        testRangeClosed();
        testEmptyRange();
        testExhaustedAfterForEachRemaining();
        testNullConsumerRejected();
        testRangeClosedAtMaxValue();
    }

    static void testRangeHalfOpen() {
        List<Integer> got = new ArrayList<>();
        IntConsumer c = v -> got.add(v);
        IntStream.range(0, 5).spliterator().forEachRemaining(c);
        check(got.equals(Arrays.asList(0, 1, 2, 3, 4)), "range(0,5) got " + got);
    }

    static void testRangeClosed() {
        List<Integer> got = new ArrayList<>();
        IntConsumer c = v -> got.add(v);
        IntStream.rangeClosed(1, 3).spliterator().forEachRemaining(c);
        check(got.equals(Arrays.asList(1, 2, 3)), "rangeClosed(1,3) got " + got);
    }

    static void testEmptyRange() {
        List<Integer> got = new ArrayList<>();
        IntConsumer c = v -> got.add(v);
        IntStream.range(3, 3).spliterator().forEachRemaining(c);
        check(got.isEmpty(), "range(3,3) got " + got);
    }

    static void testExhaustedAfterForEachRemaining() {
        List<Integer> got = new ArrayList<>();
        IntConsumer c = v -> got.add(v);
        Spliterator.OfInt sp = IntStream.range(0, 3).spliterator();
        sp.forEachRemaining(c);
        check(got.equals(Arrays.asList(0, 1, 2)), "first pass got " + got);
        boolean advanced = sp.tryAdvance(c);
        check(!advanced, "tryAdvance after forEachRemaining should be false");
        sp.forEachRemaining(c);
        check(got.size() == 3, "second forEachRemaining should add nothing, got " + got);
    }

    static void testNullConsumerRejected() {
        Spliterator.OfInt sp = IntStream.range(0, 3).spliterator();
        try {
            sp.forEachRemaining((IntConsumer) null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testRangeClosedAtMaxValue() {
        List<Integer> got = new ArrayList<>();
        IntConsumer c = v -> got.add(v);
        int max = Integer.MAX_VALUE;
        IntStream.rangeClosed(max - 1, max).spliterator().forEachRemaining(c);
        check(got.equals(Arrays.asList(max - 1, max)), "rangeClosed(MAX-1,MAX) got " + got);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
