/*
 * @test
 * @summary Checks public-API behavior reaching Streams.RangeIntSpliterator.forEachRemaining(IntConsumer)
 * @run main StreamsWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

public class StreamsWitupTest {

    public static void main(String[] args) {
        testNullConsumerThrowsNpe();
        testOpenRangeForEachRemainingVisitsAllElements();
        testClosedRangeForEachRemainingVisitsLastElement();
        testForEachRemainingExhaustsSpliterator();
    }

    static void testNullConsumerThrowsNpe() {
        Spliterator.OfInt sp = IntStream.range(0, 3).spliterator();
        try {
            sp.forEachRemaining((IntConsumer) null);
            throw new AssertionError("expected NullPointerException");
        } catch (NullPointerException expected) {
        }
    }

    static void testOpenRangeForEachRemainingVisitsAllElements() {
        Spliterator.OfInt sp = IntStream.range(0, 3).spliterator();
        List<Integer> out = new ArrayList<>();
        sp.forEachRemaining((IntConsumer) i -> out.add(i));
        List<Integer> expected = List.of(0, 1, 2);
        if (!expected.equals(out)) {
            throw new AssertionError("expected " + expected + " but was " + out);
        }
    }

    static void testClosedRangeForEachRemainingVisitsLastElement() {
        Spliterator.OfInt sp = IntStream.rangeClosed(0, 2).spliterator();
        List<Integer> out = new ArrayList<>();
        sp.forEachRemaining((IntConsumer) i -> out.add(i));
        List<Integer> expected = List.of(0, 1, 2);
        if (!expected.equals(out)) {
            throw new AssertionError("expected " + expected + " but was " + out);
        }
    }

    static void testForEachRemainingExhaustsSpliterator() {
        Spliterator.OfInt sp = IntStream.range(0, 3).spliterator();
        sp.forEachRemaining((IntConsumer) i -> { });
        if (sp.tryAdvance((IntConsumer) i -> { })) {
            throw new AssertionError("spliterator should be exhausted after forEachRemaining");
        }
    }
}
