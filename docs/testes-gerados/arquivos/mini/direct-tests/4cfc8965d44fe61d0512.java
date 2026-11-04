/*
 * @test
 * @summary Test ReferencePipeline.flatMap behavior with various mappers
 * @run main ReferencePipelineWitupTest
 */

import java.util.stream.ReferencePipeline;
import java.util.stream.Stream;
import java.util.function.Function;
import java.util.Objects;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        testFlatMapWithNonNullStreams();
        testFlatMapWithNullStream();
        testFlatMapWithEmptyStream();
        testFlatMapWithCancellationRequested();
        testFlatMapRejectsNullMapper();
    }

    static void testFlatMapWithNonNullStreams() {
        Stream<String> base = Stream.of("a", "bb");
        Stream<Integer> mapped = base.flatMap(new Function<String, Stream<Integer>>() {
            public Stream<Integer> apply(String s) {
                return Stream.of(s.length());
            }
        });
        Object[] results = mapped.toArray();
        if (results.length != 2 || !results[0].equals(1) || !results[1].equals(2)) {
            throw new AssertionError("flatMap did not map lengths correctly");
        }
    }

    static void testFlatMapWithNullStream() {
        Stream<String> base = Stream.of("a", "b");
        Stream<Integer> mapped = base.flatMap(new Function<String, Stream<Integer>>() {
            public Stream<Integer> apply(String s) {
                if ("a".equals(s)) return null;
                return Stream.of(s.length());
            }
        });
        Object[] results = mapped.toArray();
        if (results.length != 1 || !results[0].equals(1)) {
            throw new AssertionError("flatMap did not handle null stream correctly");
        }
    }

    static void testFlatMapWithEmptyStream() {
        Stream<String> base = Stream.of("a", "b");
        Stream<Integer> mapped = base.flatMap(new Function<String, Stream<Integer>>() {
            public Stream<Integer> apply(String s) {
                return Stream.empty();
            }
        });
        Object[] results = mapped.toArray();
        if (results.length != 0) {
            throw new AssertionError("flatMap did not handle empty streams correctly");
        }
    }

    static void testFlatMapWithCancellationRequested() {
        // This test simulates cancellationRequested by using a short-circuiting operation
        Stream<String> base = Stream.of("a", "b", "c");
        Stream<Integer> mapped = base.flatMap(new Function<String, Stream<Integer>>() {
            public Stream<Integer> apply(String s) {
                return Stream.of(s.length());
            }
        });
        // Use findFirst to trigger cancellationRequested internally
        Integer first = mapped.findFirst().orElse(-1);
        if (first != 1) {
            throw new AssertionError("flatMap with cancellationRequested did not return expected first element");
        }
    }

    static void testFlatMapRejectsNullMapper() {
        try {
            Stream<String> base = Stream.of("a");
            base.flatMap(null);
            throw new AssertionError("flatMap did not throw NullPointerException for null mapper");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
