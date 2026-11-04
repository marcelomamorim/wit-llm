/*
 * @test
 * @summary Tests for Streams.forEachRemaining(IntConsumer)
 * @run main StreamsWitupTest
 */

import java.util.stream.Streams;
import java.util.function.IntConsumer;

public class StreamsWitupTest {

    public static void main(String[] args) {
        testForEachRemainingWithValidConsumer();
        testForEachRemainingWithNullConsumer();
    }

    private static void testForEachRemainingWithValidConsumer() {
        Streams.RangeIntSpliterator spliterator = new Streams.RangeIntSpliterator(0, 3, false);
        StringBuilder sb = new StringBuilder();
        IntConsumer consumer = sb::append;
        spliterator.forEachRemaining(consumer);
        String result = sb.toString();
        if (!"012".equals(result)) {
            throw new AssertionError("Expected '012', but got: " + result);
        }
    }

    private static void testForEachRemainingWithNullConsumer() {
        Streams.RangeIntSpliterator spliterator = new Streams.RangeIntSpliterator(0, 1, false);
        try {
            spliterator.forEachRemaining(null);
            throw new AssertionError("Expected NullPointerException for null consumer");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }
}
