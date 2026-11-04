/*
 * @test
 * @summary Test ReferencePipeline.toArray() method
 * @run main ReferencePipelineWitupTest
 */

import java.util.stream.Stream;
import java.util.stream.ReferencePipeline;
import java.util.stream.StreamSupport;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Supplier;

public class ReferencePipelineWitupTest {
    public static void main(String[] args) {
        testToArrayEmptyStream();
        testToArrayNonEmptyStream();
        testToArrayWithNullElements();
    }

    static void testToArrayEmptyStream() {
        Stream<String> emptyStream = Stream.empty();
        Object[] arr = emptyStream.toArray();
        if (arr == null) {
            throw new AssertionError("toArray() returned null for empty stream");
        }
        if (arr.length != 0) {
            throw new AssertionError("toArray() length expected 0 but was " + arr.length);
        }
    }

    static void testToArrayNonEmptyStream() {
        Stream<String> stream = Stream.of("a", "b", "c");
        Object[] arr = stream.toArray();
        if (arr == null) {
            throw new AssertionError("toArray() returned null for non-empty stream");
        }
        if (arr.length != 3) {
            throw new AssertionError("toArray() length expected 3 but was " + arr.length);
        }
        if (!"a".equals(arr[0]) || !"b".equals(arr[1]) || !"c".equals(arr[2])) {
            throw new AssertionError("toArray() elements do not match expected values");
        }
    }

    static void testToArrayWithNullElements() {
        // Create a stream with null elements
        Supplier<Spliterator<String>> spliteratorSupplier = new Supplier<Spliterator<String>>() {
            public Spliterator<String> get() {
                return Spliterators.spliterator(new String[] {"x", null, "z"}, 0);
            }
        };
        Stream<String> stream = StreamSupport.stream(spliteratorSupplier.get(), false);
        Object[] arr = stream.toArray();
        if (arr == null) {
            throw new AssertionError("toArray() returned null for stream with null elements");
        }
        if (arr.length != 3) {
            throw new AssertionError("toArray() length expected 3 but was " + arr.length);
        }
        if (!"x".equals(arr[0]) || arr[1] != null || !"z".equals(arr[2])) {
            throw new AssertionError("toArray() elements do not match expected values including null");
        }
    }
}