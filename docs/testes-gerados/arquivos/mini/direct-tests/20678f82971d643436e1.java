/*
 * @test
 * @summary Test Stream.concat behavior with various input streams
 * @run main StreamWitupTest
 */

import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import java.util.Spliterator;
import java.util.Objects;
import java.util.Arrays;

public class StreamWitupTest {
    public static void main(String[] args) {
        testConcatNonEmptyStreams();
        testConcatEmptyStreams();
        testConcatParallelStreams();
        testConcatNullFirstStream();
        testConcatNullSecondStream();
    }

    private static void testConcatNonEmptyStreams() {
        Stream<String> s1 = Stream.of("a", "b");
        Stream<String> s2 = Stream.of("c", "d");
        Stream<String> concat = Stream.concat(s1, s2);
        Object[] result = concat.toArray();
        if (result.length != 4 || !"a".equals(result[0]) || !"b".equals(result[1]) || !"c".equals(result[2]) || !"d".equals(result[3])) {
            throw new AssertionError("Concat of two non-empty streams failed: " + Arrays.toString(result));
        }
    }

    private static void testConcatEmptyStreams() {
        Stream<String> empty1 = Stream.empty();
        Stream<String> empty2 = Stream.empty();
        Stream<String> concat = Stream.concat(empty1, empty2);
        Object[] result = concat.toArray();
        if (result.length != 0) {
            throw new AssertionError("Concat of two empty streams should be empty");
        }
    }

    private static void testConcatParallelStreams() {
        Stream<String> s1 = Stream.of("x").parallel();
        Stream<String> s2 = Stream.of("y").parallel();
        Stream<String> concat = Stream.concat(s1, s2);
        if (!concat.isParallel()) {
            throw new AssertionError("Concat of parallel streams should be parallel");
        }
        Object[] result = concat.toArray();
        if (result.length != 2 || !"x".equals(result[0]) || !"y".equals(result[1])) {
            throw new AssertionError("Concat of parallel streams failed: " + Arrays.toString(result));
        }
    }

    private static void testConcatNullFirstStream() {
        try {
            Stream.concat(null, Stream.of("a"));
            throw new AssertionError("Expected NullPointerException for null first stream");
        } catch (NullPointerException expected) {
        }
    }

    private static void testConcatNullSecondStream() {
        try {
            Stream.concat(Stream.of("a"), null);
            throw new AssertionError("Expected NullPointerException for null second stream");
        } catch (NullPointerException expected) {
        }
    }
}
