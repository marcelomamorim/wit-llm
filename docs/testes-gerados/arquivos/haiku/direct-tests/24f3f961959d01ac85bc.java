/*
 * @test
 * @summary Verify forEachRemaining(IntConsumer) on wrapping spliterators obtained from IntStream pipelines
 * @run main StreamSpliteratorsWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

public class StreamSpliteratorsWitupTest {

    public static void main(String[] args) {
        testFreshForEachRemaining();
        testAfterPartialAdvance();
        testSecondCallIsNoOp();
        testEmptyFilteredStream();
        testNullConsumerRejected();
    }

    static Spliterator.OfInt wrapped() {
        return IntStream.range(0, 5).map(x -> x * 2).spliterator();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testFreshForEachRemaining() {
        List<Integer> out = new ArrayList<>();
        IntConsumer c = out::add;
        wrapped().forEachRemaining(c);
        check(out.equals(List.of(0, 2, 4, 6, 8)), "fresh forEachRemaining: " + out);
    }

    static void testAfterPartialAdvance() {
        Spliterator.OfInt s = wrapped();
        int[] first = new int[1];
        boolean advanced = s.tryAdvance((IntConsumer) v -> first[0] = v);
        check(advanced && first[0] == 0, "tryAdvance first element: " + first[0]);
        List<Integer> rest = new ArrayList<>();
        IntConsumer c = rest::add;
        s.forEachRemaining(c);
        check(rest.equals(List.of(2, 4, 6, 8)), "remaining after tryAdvance: " + rest);
    }

    static void testSecondCallIsNoOp() {
        Spliterator.OfInt s = wrapped();
        List<Integer> firstRun = new ArrayList<>();
        IntConsumer c1 = firstRun::add;
        s.forEachRemaining(c1);
        check(firstRun.size() == 5, "first run size: " + firstRun.size());
        List<Integer> secondRun = new ArrayList<>();
        IntConsumer c2 = secondRun::add;
        s.forEachRemaining(c2);
        check(secondRun.isEmpty(), "second run should be empty: " + secondRun);
    }

    static void testEmptyFilteredStream() {
        Spliterator.OfInt s = IntStream.range(0, 5).filter(x -> x > 10).map(x -> x).spliterator();
        List<Integer> out = new ArrayList<>();
        IntConsumer c = out::add;
        s.forEachRemaining(c);
        check(out.isEmpty(), "empty filtered stream: " + out);
    }

    static void testNullConsumerRejected() {
        Spliterator.OfInt s = wrapped();
        boolean thrown = false;
        try {
            s.forEachRemaining((IntConsumer) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null consumer should throw NullPointerException");
    }
}
