/*
 * @test
 * @summary Witup generated tests for StreamSupport.stream(Spliterator, boolean)
 * @run main StreamSupportWitupTest
 */
import java.util.Arrays;
import java.util.List;
import java.util.Spliterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class StreamSupportWitupTest {

    public static void main(String[] args) {
        testNullSpliteratorThrowsNullPointerException();
        testSequentialStreamFromSpliterator();
        testParallelStreamFromSpliterator();
        testStreamElementCountPreserved();
    }

    static void testNullSpliteratorThrowsNullPointerException() {
        Spliterator<String> spliterator = null;
        try {
            StreamSupport.stream(spliterator, false);
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException for null spliterator (sequential)");
    }

    static void testSequentialStreamFromSpliterator() {
        List<Integer> list = Arrays.asList(1, 2, 3);
        Stream<Integer> stream = StreamSupport.stream(list.spliterator(), false);
        if (stream.isParallel()) {
            throw new RuntimeException("Expected sequential stream when parallel=false");
        }
    }

    static void testParallelStreamFromSpliterator() {
        List<Integer> list = Arrays.asList(1, 2, 3);
        Stream<Integer> stream = StreamSupport.stream(list.spliterator(), true);
        if (!stream.isParallel()) {
            throw new RuntimeException("Expected parallel stream when parallel=true");
        }
    }

    static void testStreamElementCountPreserved() {
        List<Integer> list = Arrays.asList(1, 2, 3);
        long sequentialCount = StreamSupport.stream(list.spliterator(), false).count();
        long parallelCount = StreamSupport.stream(list.spliterator(), true).count();
        if (sequentialCount != 3L) {
            throw new RuntimeException("Unexpected sequential count: " + sequentialCount);
        }
        if (parallelCount != 3L) {
            throw new RuntimeException("Unexpected parallel count: " + parallelCount);
        }
    }
}
