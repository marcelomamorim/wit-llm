/*
 * @test
 * @summary Test ReferencePipeline.flatMapToInt behavior with various mappers
 * @run main ReferencePipelineWitupTest
 */

import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.ReferencePipeline;
import java.util.function.Function;
import java.util.Objects;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        testNullMapperThrows();
        testFlatMapToIntWithEmptyStream();
        testFlatMapToIntWithSingleElement();
        testFlatMapToIntWithMultipleElements();
        testFlatMapToIntWithNullStreamFromMapper();
    }

    static void testNullMapperThrows() {
        Stream<String> stream = Stream.of("a");
        ReferencePipeline<String, String> rp = (ReferencePipeline<String, String>) stream;
        try {
            rp.flatMapToInt(null);
            throw new AssertionError("Expected NullPointerException for null mapper");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testFlatMapToIntWithEmptyStream() {
        Stream<String> stream = Stream.empty();
        ReferencePipeline<String, String> rp = (ReferencePipeline<String, String>) stream;
        IntStream result = rp.flatMapToInt(s -> IntStream.of(1, 2));
        int count = (int) result.count();
        if (count != 0) {
            throw new AssertionError("Expected empty result stream, got count=" + count);
        }
    }

    static void testFlatMapToIntWithSingleElement() {
        Stream<String> stream = Stream.of("x");
        ReferencePipeline<String, String> rp = (ReferencePipeline<String, String>) stream;
        IntStream result = rp.flatMapToInt(s -> IntStream.of(s.length(), s.length() * 2));
        int[] expected = {1, 2};
        int[] actual = result.toArray();
        if (actual.length != expected.length) {
            throw new AssertionError("Expected length " + expected.length + " but got " + actual.length);
        }
        for (int i = 0; i < expected.length; i++) {
            if (actual[i] != expected[i]) {
                throw new AssertionError("Expected " + expected[i] + " but got " + actual[i]);
            }
        }
    }

    static void testFlatMapToIntWithMultipleElements() {
        Stream<String> stream = Stream.of("a", "bb", "ccc");
        ReferencePipeline<String, String> rp = (ReferencePipeline<String, String>) stream;
        IntStream result = rp.flatMapToInt(s -> IntStream.of(s.length()));
        int[] expected = {1, 2, 3};
        int[] actual = result.toArray();
        if (actual.length != expected.length) {
            throw new AssertionError("Expected length " + expected.length + " but got " + actual.length);
        }
        for (int i = 0; i < expected.length; i++) {
            if (actual[i] != expected[i]) {
                throw new AssertionError("Expected " + expected[i] + " but got " + actual[i]);
            }
        }
    }

    static void testFlatMapToIntWithNullStreamFromMapper() {
        Stream<String> stream = Stream.of("a");
        ReferencePipeline<String, String> rp = (ReferencePipeline<String, String>) stream;
        IntStream result = rp.flatMapToInt(s -> null);
        // The implementation tolerates null streams by skipping them, so result should be empty
        long count = result.count();
        if (count != 0) {
            throw new AssertionError("Expected empty stream when mapper returns null, got count=" + count);
        }
    }

}