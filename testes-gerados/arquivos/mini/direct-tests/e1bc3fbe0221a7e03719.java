/*
 * @test
 * @summary Basic tests for StreamSupport.stream(Spliterator, boolean)
 * @run main StreamSupportWitupTest
 */

import java.util.stream.StreamSupport;
import java.util.stream.Stream;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.Objects;

public class StreamSupportWitupTest {
    public static void main(String[] args) {
        testNullSpliterator();
        testSequentialStream();
        testParallelStream();
        testStreamContents();
    }

    private static void testNullSpliterator() {
        try {
            StreamSupport.stream(null, false);
            throw new RuntimeException("Expected NullPointerException for null spliterator");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testSequentialStream() {
        Spliterator<Integer> spliterator = Spliterators.spliterator(new Integer[] {1, 2, 3}, 0);
        Stream<Integer> stream = StreamSupport.stream(spliterator, false);
        if (stream.isParallel()) {
            throw new RuntimeException("Stream should be sequential (not parallel)");
        }
    }

    private static void testParallelStream() {
        Spliterator<Integer> spliterator = Spliterators.spliterator(new Integer[] {4, 5, 6}, 0);
        Stream<Integer> stream = StreamSupport.stream(spliterator, true);
        if (!stream.isParallel()) {
            throw new RuntimeException("Stream should be parallel");
        }
    }

    private static void testStreamContents() {
        Integer[] data = {7, 8, 9};
        Spliterator<Integer> spliterator = Spliterators.spliterator(data, 0);
        Stream<Integer> stream = StreamSupport.stream(spliterator, false);
        Object[] collected = stream.toArray();
        if (collected.length != data.length) {
            throw new RuntimeException("Stream length mismatch");
        }
        for (int i = 0; i < data.length; i++) {
            if (!Objects.equals(data[i], collected[i])) {
                throw new RuntimeException("Stream element mismatch at index " + i);
            }
        }
    }
}
