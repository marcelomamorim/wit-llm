/*
 * @test
 * @summary Verifies StreamSupport.stream(Spliterator, boolean) null check, sequential/parallel flag and element order
 * @run main StreamSupportWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class StreamSupportWitupTest {

    public static void main(String[] args) {
        testNullSpliteratorThrowsNPE();
        testSequentialFlag();
        testParallelFlag();
        testElementOrderPreserved();
        testParallelCount();
    }

    static void testNullSpliteratorThrowsNPE() {
        try {
            StreamSupport.stream(null, false);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException for null spliterator");
    }

    static void testSequentialFlag() {
        Stream<String> s = StreamSupport.stream(
                Arrays.asList("a", "b").spliterator(), false);
        if (s.isParallel()) {
            throw new RuntimeException("Stream should be sequential");
        }
    }

    static void testParallelFlag() {
        Stream<String> s = StreamSupport.stream(
                Arrays.asList("a", "b").spliterator(), true);
        if (!s.isParallel()) {
            throw new RuntimeException("Stream should be parallel");
        }
    }

    static void testElementOrderPreserved() {
        List<String> out = new ArrayList<>();
        StreamSupport.stream(Arrays.asList("a", "b", "c").spliterator(), false)
                .forEach(out::add);
        List<String> expected = Arrays.asList("a", "b", "c");
        if (!expected.equals(out)) {
            throw new RuntimeException("Unexpected order: " + out);
        }
    }

    static void testParallelCount() {
        long count = StreamSupport.stream(
                Arrays.asList(1, 2, 3, 4).spliterator(), true).count();
        if (count != 4L) {
            throw new RuntimeException("Unexpected count: " + count);
        }
    }
}
