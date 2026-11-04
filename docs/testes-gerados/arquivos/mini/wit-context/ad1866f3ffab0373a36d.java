/*
 * @test
 * @summary Test ReferencePipeline.flatMapToInt behavior including null mapper rejection
 * @run main ReferencePipelineWitupTest
 */

import java.util.stream.IntStream;
import java.util.stream.ReferencePipeline;
import java.util.function.Function;
import java.util.Spliterator;
import java.util.stream.StreamShape;
import java.util.stream.StreamOpFlag;
import java.util.Objects;

public class ReferencePipelineWitupTest {

    // Minimal subclass to instantiate ReferencePipeline for testing
    static class RP extends ReferencePipeline<String, String> {
        RP() {
            super((Spliterator<?>) null, 0, false);
        }
    }

    public static void main(String[] args) {
        testNullMapperThrowsNPE();
        testFlatMapToIntProducesExpectedValues();
    }

    static void testNullMapperThrowsNPE() {
        ReferencePipeline<String, String> rp = new RP();
        try {
            rp.flatMapToInt(null);
            throw new AssertionError("Expected NullPointerException for null mapper");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testFlatMapToIntProducesExpectedValues() {
        ReferencePipeline<String, String> rp = new RP();
        Function<String, IntStream> mapper = new Function<String, IntStream>() {
            public IntStream apply(String s) {
                if (s == null) return IntStream.empty();
                if (s.isEmpty()) return IntStream.of();
                // Map each character to its code point
                int[] codes = new int[s.length()];
                for (int i = 0; i < s.length(); i++) {
                    codes[i] = s.charAt(i);
                }
                return IntStream.of(codes);
            }
        };

        IntStream result = rp.flatMapToInt(mapper);

        // The returned IntStream is a pipeline stage; test that it is non-null
        if (result == null) {
            throw new AssertionError("flatMapToInt returned null IntStream");
        }

        // Test that the IntStream processes elements as expected
        // Since RP is a dummy pipeline with no source, the stream is empty
        // So we test the pipeline by invoking terminal operation and expecting no exception
        int count = result.count();
        if (count != 0) {
            throw new AssertionError("Expected empty IntStream from dummy pipeline, got count=" + count);
        }
    }
}
