/*
 * @test
 * @summary Verifies null-consumer NullPointerException and ordered traversal of the wrapping spliterator reached through the public IntStream.spliterator() API
 * @run main StreamSpliteratorsWitupTest
 */

import java.util.Spliterator;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

public class StreamSpliteratorsWitupTest {

    public static void main(String[] args) {
        testNullConsumerOnFreshSpliterator();
        testNullConsumerAfterCompletedTraversal();
        testOrderedTraversalDeliversAllElements();
    }

    static void testNullConsumerOnFreshSpliterator() {
        Spliterator.OfInt sp = IntStream.of(1, 2, 3).map(x -> x).spliterator();
        boolean thrown = false;
        try {
            sp.forEachRemaining((IntConsumer) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException for null consumer on fresh spliterator");
        }
    }

    static void testNullConsumerAfterCompletedTraversal() {
        Spliterator.OfInt sp = IntStream.of(1, 2, 3).map(x -> x).spliterator();
        sp.forEachRemaining(x -> { });
        boolean thrown = false;
        try {
            sp.forEachRemaining((IntConsumer) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException for null consumer after completed traversal");
        }
    }

    static void testOrderedTraversalDeliversAllElements() {
        Spliterator.OfInt sp = IntStream.of(1, 2, 3).map(x -> x * 2).spliterator();
        final int[] count = {0};
        final int[] sum = {0};
        final int[] last = {0};
        sp.forEachRemaining((IntConsumer) v -> {
            count[0]++;
            sum[0] += v;
            last[0] = v;
        });
        if (count[0] != 3) {
            throw new RuntimeException("Expected 3 elements, got " + count[0]);
        }
        if (sum[0] != 12) {
            throw new RuntimeException("Expected sum 12, got " + sum[0]);
        }
        if (last[0] != 6) {
            throw new RuntimeException("Expected last element 6, got " + last[0]);
        }
    }
}
