/*
 * @test
 * @summary Test StreamSupport.stream(Spliterator<T>, boolean) for null input and basic usage
 * @run main StreamSupportWitupTest
 */

import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.Objects;

public class StreamSupportWitupTest {
    public static void main(String[] args) {
        testNullSpliterator();
        testStreamFromSpliteratorSequential();
        testStreamFromSpliteratorParallel();
    }

    private static void testNullSpliterator() {
        try {
            StreamSupport.stream(null, false);
            throw new RuntimeException("Expected NullPointerException for null spliterator");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testStreamFromSpliteratorSequential() {
        Spliterator<Integer> spliterator = Spliterators.spliterator(new Integer[] {1, 2, 3}, 0);
        Stream<Integer> stream = StreamSupport.stream(spliterator, false);
        Objects.requireNonNull(stream);
        // Check that stream is sequential
        if (stream.isParallel()) {
            throw new RuntimeException("Stream should be sequential but is parallel");
        }
        // Check stream contents
        int sum = stream.mapToInt(Integer::intValue).sum();
        if (sum != 6) {
            throw new RuntimeException("Sum of stream elements expected 6 but was " + sum);
        }
    }

    private static void testStreamFromSpliteratorParallel() {
        Spliterator<Integer> spliterator = Spliterators.spliterator(new Integer[] {4, 5, 6}, 0);
        Stream<Integer> stream = StreamSupport.stream(spliterator, true);
        Objects.requireNonNull(stream);
        // Check that stream is parallel
        if (!stream.isParallel()) {
            throw new RuntimeException("Stream should be parallel but is sequential");
        }
        // Check stream contents
        int sum = stream.mapToInt(Integer::intValue).sum();
        if (sum != 15) {
            throw new RuntimeException("Sum of stream elements expected 15 but was " + sum);
        }
    }
}
