/*
 * @test
 * @summary Test Streams.RangeIntSpliterator.forEachRemaining with various ranges
 * @run main StreamsWitupTest
 */

import java.util.stream.Streams;
import java.util.function.IntConsumer;

public class StreamsWitupTest {

    static class TestIntConsumer implements IntConsumer {
        int count = 0;
        int lastValue = Integer.MIN_VALUE;
        public void accept(int value) {
            count++;
            lastValue = value;
        }
    }

    public static void main(String[] args) {
        testForEachRemainingOpenRange();
        testForEachRemainingClosedRange();
        testForEachRemainingEmptyRange();
    }

    private static void testForEachRemainingOpenRange() {
        // Range from 0 (inclusive) to 3 (exclusive), open range
        Streams.RangeIntSpliterator spliterator = new Streams.RangeIntSpliterator(0, 3, false);
        TestIntConsumer consumer = new TestIntConsumer();
        spliterator.forEachRemaining(consumer);
        if (consumer.count != 3) {
            throw new AssertionError("Expected 3 elements consumed, got " + consumer.count);
        }
        if (consumer.lastValue != 2) {
            throw new AssertionError("Expected last consumed value 2, got " + consumer.lastValue);
        }
        if (spliterator.estimateSize() != 0) {
            throw new AssertionError("Expected estimateSize 0 after forEachRemaining, got " + spliterator.estimateSize());
        }
    }

    private static void testForEachRemainingClosedRange() {
        // Range from 0 to 3, closed range (last element included)
        Streams.RangeIntSpliterator spliterator = new Streams.RangeIntSpliterator(0, 3, true);
        TestIntConsumer consumer = new TestIntConsumer();
        spliterator.forEachRemaining(consumer);
        if (consumer.count != 4) {
            throw new AssertionError("Expected 4 elements consumed for closed range, got " + consumer.count);
        }
        if (consumer.lastValue != 3) {
            throw new AssertionError("Expected last consumed value 3 for closed range, got " + consumer.lastValue);
        }
        if (spliterator.estimateSize() != 0) {
            throw new AssertionError("Expected estimateSize 0 after forEachRemaining closed range, got " + spliterator.estimateSize());
        }
    }

    private static void testForEachRemainingEmptyRange() {
        // Range where from == upTo and open range (no elements)
        Streams.RangeIntSpliterator spliterator = new Streams.RangeIntSpliterator(5, 5, false);
        TestIntConsumer consumer = new TestIntConsumer();
        spliterator.forEachRemaining(consumer);
        if (consumer.count != 0) {
            throw new AssertionError("Expected 0 elements consumed for empty range, got " + consumer.count);
        }
        if (spliterator.estimateSize() != 0) {
            throw new AssertionError("Expected estimateSize 0 for empty range, got " + spliterator.estimateSize());
        }
    }
}
