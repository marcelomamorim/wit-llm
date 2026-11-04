/*
 * @test
 * @summary Test ReferencePipeline.flatMap behavior including null mapper rejection
 * @run main ReferencePipelineWitupTest
 */

import java.util.stream.ReferencePipeline;
import java.util.stream.Stream;
import java.util.function.Function;
import java.util.Objects;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        testFlatMapNullMapper();
        testFlatMapBasicFunctionality();
    }

    static void testFlatMapNullMapper() {
        ReferencePipeline<String, String> pipeline = new ReferencePipeline<String, String>(
            () -> Stream.of("a", "b").spliterator(), 0, false) {
            @Override
            public java.util.stream.Stream<String> evaluate() {
                return Stream.of("a", "b");
            }
        };

        try {
            pipeline.flatMap(null);
            throw new AssertionError("Expected NullPointerException for null mapper");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testFlatMapBasicFunctionality() {
        ReferencePipeline<String, String> pipeline = new ReferencePipeline<String, String>(
            () -> Stream.of("a", "b").spliterator(), 0, false) {
            @Override
            public java.util.stream.Stream<String> evaluate() {
                return Stream.of("a", "b");
            }
        };

        Function<String, Stream<String>> mapper = s -> Stream.of(s, s.toUpperCase());
        Stream<String> resultStream = pipeline.flatMap(mapper);

        // Collect results to array to verify
        Object[] results = resultStream.toArray();

        if (results.length != 4) {
            throw new AssertionError("Expected 4 elements after flatMap, got " + results.length);
        }

        boolean foundA = false;
        boolean foundB = false;
        boolean foundAUpper = false;
        boolean foundBUpper = false;

        for (Object o : results) {
            if ("a".equals(o)) foundA = true;
            else if ("b".equals(o)) foundB = true;
            else if ("A".equals(o)) foundAUpper = true;
            else if ("B".equals(o)) foundBUpper = true;
        }

        if (!(foundA && foundB && foundAUpper && foundBUpper)) {
            throw new AssertionError("flatMap result missing expected elements");
        }
    }

}