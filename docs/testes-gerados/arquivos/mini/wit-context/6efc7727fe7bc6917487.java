/*
 * @test
 * @summary Test ReferencePipeline.mapToInt behavior including null argument check
 * @run main ReferencePipelineWitupTest
 */

import java.util.stream.ReferencePipeline;
import java.util.stream.IntStream;
import java.util.function.ToIntFunction;
import java.util.Objects;

public class ReferencePipelineWitupTest {

    // Minimal concrete subclass to instantiate ReferencePipeline for testing
    static class RP extends ReferencePipeline<Object, Object> {
        RP() {
            super((java.util.Spliterator<?>) null, 0, false);
        }
    }

    public static void main(String[] args) {
        testMapToIntWithValidMapper();
        testMapToIntWithNullMapper();
        System.out.println("All tests passed.");
    }

    static void testMapToIntWithValidMapper() {
        RP pipeline = new RP();
        ToIntFunction<Object> mapper = new ToIntFunction<Object>() {
            public int applyAsInt(Object o) {
                return o == null ? 0 : o.hashCode();
            }
        };
        IntStream result = pipeline.mapToInt(mapper);
        if (result == null) {
            throw new AssertionError("mapToInt returned null for valid mapper");
        }
    }

    static void testMapToIntWithNullMapper() {
        RP pipeline = new RP();
        try {
            pipeline.mapToInt(null);
            throw new AssertionError("Expected NullPointerException for null mapper");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }
}
